package com.wfj.tank1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Tank {
	private int x, y;
	private Dir dir = Dir.DOWN;
	private static final int SPEED = 5;

	public static int WIDTH = ResourceMgr.goodTankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

	private Random random = new Random();
	
	Rectangle rect = new Rectangle();

	private boolean moving = true;
	private TankFrame tf = null;
	public boolean living = true;
	private Group group = Group.BAD;

	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void paint(Graphics g) {
		// 坦克阵亡，移除
		if (!living)
			tf.tanks.remove(this);

		switch (dir) {
		case LEFT:
			g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
			break;
		case UP:
			g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
			break;
		}
		move();
	}

	private void move() {

		if (!moving) {
			return;
		}

		switch (dir) {
		case LEFT:
			x -= SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		default:
			break;
		}
		//敌方坦克开火的随机性
		if (this.group == Group.BAD && random.nextInt(100) > 95)
			this.fire();
		//地方坦克方向随机性
		if (this.group == Group.BAD && random.nextInt(100) > 95)
			randomDir();
		//边界检测
		boundsCheck();
		// update rect
		rect.x = this.x;
		rect.y = this.y;
	}
	
	//边界检测方法，-2是一个技巧
	private void boundsCheck() {
		if (this.x < 2)
			x = 2;
		if (this.y < 28)
			y = 28;
		if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2)
			x = TankFrame.GAME_WIDTH - Tank.WIDTH -2;
		if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT-2)
			y = TankFrame.GAME_HEIGHT - Tank.HEIGHT-2;
	}

	/**
	 * 随机切换方向
	 */
	private void randomDir() {
		// 这样的写法确实很难想到！
		this.dir = Dir.values()[random.nextInt(4)];
	}

	// 开火方法
	public void fire() {
		int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
		tf.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tf));
	}

	// 阵亡方法
	public void die() {
		this.living = false;
	}
}
