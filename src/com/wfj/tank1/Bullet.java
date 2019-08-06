package com.wfj.tank1;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
	private static final int SPEED = 100;
	public static int WIDTH = ResourceMgr.bulletD.getWidth();
	public static int HEIGHT = ResourceMgr.bulletD.getHeight();

	Rectangle rect = new Rectangle();

	private int x, y;
	private Dir dir;

	public boolean living = true;
	TankFrame tf = null;
	private Group group = Group.BAD;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
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

	public void paint(Graphics g) {
		if (!living) {
			tf.bullets.remove(new Bullet(this.x, this.y, this.dir, this.group, this.tf));
		}

		switch (dir) {
		case LEFT:
			g.drawImage(ResourceMgr.bulletL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.bulletU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.bulletR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bulletD, x, y, null);
			break;
		}

		move();
	}

	private void move() {
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

		// update rect
		rect.x = this.x;
		rect.y = this.y;

		if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
			living = false;
		}

	}

	// 碰撞方法
	public void collideWith(Tank tank) {
		// 同类型坦克相撞，没事！
		if (this.group == tank.getGroup())
			return;
		// 用一个rect来记录子弹的位置
		if (rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
			int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
			tf.explodes.add(new Explode(eX, eY, tf));
		}

	}

	// 子弹阵亡
	private void die() {
		this.living = false;
	}

}
