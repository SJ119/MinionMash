package com.moustacheapps.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Minion {

	int width, height;
	float x, y;
	public Vector2 position;
	public Vector2 velocity;
	public boolean isAlive;
	public Rectangle boundingRectangle;
	public boolean isOnScreen = false;
	
	public Direction direction;
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	public Minion(float x, float y, int width, int height, Vector2 velocity) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		isAlive = true;
		isOnScreen = false;
		this.velocity = velocity;
		boundingRectangle = new Rectangle();
		position = new Vector2(x,y);
		if (velocity.x > 0) {
			direction = Direction.RIGHT;
		} else if (velocity.x < 0){
			direction = Direction.LEFT;
		} else if (velocity.y > 0){
			direction = Direction.DOWN;
		} else if (velocity.y < 0){
			direction = Direction.UP;
		}
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));

		boundingRectangle.set(position.x + 1, position.y, width - 1, height);
			
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
	
	public Rectangle getBoundingRectangle(){
		return boundingRectangle;
	}

	public boolean isOnScreen() {
		return isOnScreen;
	}
	
	public boolean isUp() {
		return direction == Direction.UP;
	}
	
	public boolean isDown() {
		return direction == Direction.DOWN;
	}
	
	public boolean isLeft() {
		return direction == Direction.LEFT;
	}
	
	public boolean isRight() {
		return direction == Direction.RIGHT;
	}

}
