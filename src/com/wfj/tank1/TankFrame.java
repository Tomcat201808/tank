package com.wfj.tank1;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankFrame extends Frame {

	Tank myTank = new Tank(200, 400, Dir.DOWN,Group.GOOD,this);
	
	//子弹容器
	List<Bullet> bullets = new ArrayList<Bullet>();
	//敌方坦克容器
	List<Tank> tanks = new ArrayList<Tank>();
	//爆炸容器
	List<Explode> explodes = new ArrayList<Explode>();
	
	Bullet bullet =new Bullet(300,300 ,Dir.DOWN ,Group.BAD,this);
	static final int GAME_WIDTH = 1080,GAME_HEIGHT =960;
	
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setTitle("坦克大战");
		setVisible(true);
		this.addKeyListener(new MyKeyListener());

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) { 
				System.exit(0);
			}

		});
	}

	Image offScreenImage = null; // 虚拟图片

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("子弹的数量："+ bullets.size(), 10,60);
		g.drawString("敌人的数量："+ tanks.size(), 10,80);
		g.drawString("爆炸的数量："+ explodes.size(), 10,100);
		g.setColor(c);
		
		myTank.paint(g);
		
		//画出子弹
		for(int i =0; i< bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
		//画出坦克
		for(int i =0; i< tanks.size(); i++) {
			tanks.get(i).paint(g);
		}
		
		//画出多处爆炸
		for(int i =0; i< explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
		
		//碰撞检测
		for(int i=0; i<bullets.size();i++) {
			for(int j=0; j<tanks.size();j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
			
		}
		
		
		 for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
			 Bullet b = it.next();
			 if(!b.living) it.remove();
		 }
		 
		 for(Iterator<Tank> it = tanks.iterator(); it.hasNext();) {
			 Tank t = it.next();
			 if(!t.living) it.remove();
		 }
		 
	}

	class MyKeyListener extends KeyAdapter {

		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;

			default:
				break;
			}

			setMainTankDir();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
				
			case KeyEvent.VK_CONTROL:
				myTank.fire();
				break;
			default:
				break;
			}

			setMainTankDir();

		}

		private void setMainTankDir() {
			if (!bL && !bU && !bR && !bD) {
				myTank.setMoving(false);
			} else {
				myTank.setMoving(true);

				if (bL)
					myTank.setDir(Dir.LEFT);
				if (bU)
					myTank.setDir(Dir.UP);
				if (bR)
					myTank.setDir(Dir.RIGHT);
				if (bD)
					myTank.setDir(Dir.DOWN);

			}
		}

	}
}
