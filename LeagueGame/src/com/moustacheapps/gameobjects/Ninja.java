package com.moustacheapps.gameobjects;

import com.badlogic.gdx.math.Rectangle;

public class Ninja {
	
	int width, height;
	float x, y;
	public boolean isAlive;
	public Rectangle boundingRectangle;
	public Direction direction;
	public enum Direction {
		STANDARD, LEFT, RIGHT, UP, DOWN
	}
	
	
	public Ninja(float x, float y, int width, int height) {
		
		this.width = width;
		this.height = height;
		direction = Direction.STANDARD;
		isAlive = true;
		boundingRectangle = new Rectangle();
		boundingRectangle.set(x, y, width, height);
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void die() {
		isAlive = false;
		direction = Direction.STANDARD;
	}
	
	public void restart() {
		isAlive = true;
		direction = Direction.STANDARD;
	}

}
