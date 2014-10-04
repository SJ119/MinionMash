package com.moustacheapps.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moustacheapps.gameworld.GameWorld;

public class Sword {
	
	private Vector2 position;
	public Vector2 velocity;
	private int width, height;
	private float x, y;
	private Rectangle boundingRectangle;
	private boolean isOffScreen = false;

	public Sword(float x, float y, int width, int height, Vector2 velocity) {
		this.width = width;
		this.height = height;
		position = new Vector2(x,y);
		this.velocity = velocity;
		boundingRectangle = new Rectangle();
	}
	
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
		
		
		if (velocity.y > 0) {
			boundingRectangle.set(position.x, position.y, width, height);
			if (position.y > (GameWorld.midPointY * 2) + 12){
			isOffScreen = true;
			}
		} else if (velocity.y < 0) {
			boundingRectangle.set(position.x, position.y, width, height);
			if (position.y < -12){
			isOffScreen = true;
			}
		} else if (velocity.x > 0) {
			boundingRectangle.set(position.x -4, position.y +3, height, width);
			if (position.x > (GameWorld.midPointX * 2) + 12) {
			isOffScreen = true;
			}
		} else if (velocity.x < 0) {
			boundingRectangle.set(position.x -4, position.y +3, height, width);
			if (position.x < - 12){
			isOffScreen = true;
			}
		}
	}
	
	public int getXX() {
		return (int) boundingRectangle.width;
	}
	
	public int getYY() {
		return (int) boundingRectangle.height;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isOffScreen(){
		return isOffScreen;
	}
	
}
