package com.moustacheapps.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.moustacheapps.gameworld.GameWorld;

public class Ninja {
	
	int width, height;
	float x, y;
	public boolean isAlive;
	public Rectangle boundingRectangle, boundingRectangleMelee;
	public Direction direction;
	public enum Direction {
		STANDARD, LEFT, RIGHT, UP, DOWN, DOWNMELEE, RIGHTMELEE, UPMELEE, LEFTMELEE;
	}
	public float health, energy;
	GameWorld gameWorld;
	
	
	public Ninja(float x, float y, int width, int height, GameWorld gameWorld) {
		
		this.width = width;
		this.height = height;
		direction = Direction.STANDARD;
		isAlive = true;
		boundingRectangle = new Rectangle();
		boundingRectangle.set(x, y, width, height);
		
		boundingRectangleMelee = new Rectangle();
		boundingRectangleMelee.set(x-10,y-10, width + 20, height + 20);
		this.x = x;
		this.y = y;
		health = 120;
		energy = 120;
		this.gameWorld = gameWorld;
		
	}
	
	public void update(float delta) {
		if (health <= 0) {
			gameWorld.gameOver();
		}
		
		if ((energy + 0.5) <= 120) {
			energy += 0.5;
		}
		
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
	
	public Rectangle getBoundingRectangle(){
		return boundingRectangle;
	}
	
	public Rectangle getBoundingRectangleMelee(){
		return boundingRectangleMelee;
	}
	
	public void restart() {
		isAlive = true;
		health = 120;
		energy = 120;
		direction = Direction.STANDARD;
	}

}
