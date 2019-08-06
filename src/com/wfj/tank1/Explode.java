package com.wfj.tank1;

//import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	private int x,y;
	
	public boolean living = true;
	TankFrame tf = null;
	
	private int step =0;

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

	
	public Explode(int x, int y,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
		
		new Audio("audio/explode.wav").play();
	}
	
	public void paint(Graphics g) {
		g.drawImage(ResourceMgr.explodes[step++],x,y,null);
		
		if (step >= ResourceMgr.explodes.length) {
			tf.explodes.remove(this);
			
		}

	}
	
}
