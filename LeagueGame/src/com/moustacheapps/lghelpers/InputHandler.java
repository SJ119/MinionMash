package com.moustacheapps.lghelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.moustacheapps.gameobjects.Ninja;
import com.moustacheapps.gameobjects.Ninja.Direction;
import com.moustacheapps.gameworld.GameWorld;
import com.moustacheapps.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	private Ninja ninja;
	
	private GameWorld myWorld;

	private List<SimpleButton> inputSections, retryButtons;

	private SimpleButton playButton, retryButton, leftTap, rightTap, fullTap;

	private float scaleFactorX;
	private float scaleFactorY;
	private boolean shuffleTap = false;
	public static int shuffleState = 0;
	public int gameWidth, gameHeight;
	
	private Vector2 velocityU = new Vector2(0,-200);
	private Vector2 velocityD = new Vector2(0,200);
	private Vector2 velocityL = new Vector2(-200, 0);
	private Vector2 velocityR = new Vector2(200, 0);
	

	public InputHandler(GameWorld myWorld, float scaleFactorX,
			float scaleFactorY, int gameWidth, int gameHeight) {
		this.myWorld = myWorld;
		ninja = myWorld.getNinja();
		int midPointY = GameWorld.getMidPointY();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		
		inputSections = new ArrayList<SimpleButton>();

		playButton = new SimpleButton(
				gameWidth / 2 - (LGAssetLoader.playButtonUp.getRegionWidth() / 2),
				midPointY + 35, 29, 16, LGAssetLoader.playButtonUp,
				LGAssetLoader.playButtonDown);
		leftTap = new SimpleButton(0, 0, 136, GameWorld.getMidPointY() * 2,
				null, null);
		rightTap = new SimpleButton(136, 0, 136, GameWorld.getMidPointY() * 2,
				null, null);
		fullTap = new SimpleButton(0, 0, gameWidth, gameHeight,
				null, null);
		retryButton = new SimpleButton(
				gameWidth / 2 - (LGAssetLoader.backButtonUp.getRegionWidth() / 2),
				midPointY + 35, 29, 16, LGAssetLoader.backButtonUp,
				LGAssetLoader.backButtonDown);

		inputSections.add(leftTap);
		inputSections.add(rightTap);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		float ratio = ((float) gameHeight/(float) gameWidth);
		boolean aboveDownRight = (screenX * ratio > screenY );
		boolean aboveUpRight = (screenX < (gameWidth - screenY / ratio));

		if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX, screenY);
		} else if (myWorld.isRunning()) {
			if (fullTap.isTouchDown(screenX, screenY)) {
				if (aboveDownRight) {
					if (aboveUpRight) {
						ninja.direction = Direction.UP;
						myWorld.initSword(velocityU);
					} else {
						ninja.direction = Direction.RIGHT;
						myWorld.initSword(velocityR);
					}
				} else {
					if (aboveUpRight) {
						ninja.direction = Direction.LEFT;
						myWorld.initSword(velocityL);
					} else {
						ninja.direction = Direction.DOWN;
						myWorld.initSword(velocityD);
					}
				}
			}
		} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			retryButton.isTouchDown(screenX, screenY);
			fullTap.isTouchDown(screenX, screenY);
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.start();
			}
		} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			if (retryButton.isTouchUp(screenX, screenY)) {
				myWorld.restart();
				return true;
			} else if (leftTap.isTouchUp(screenX, screenY)
					|| rightTap.isTouchUp(screenX, screenY)) {
				myWorld.restart();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.DPAD_DOWN) {

			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.DOWN;
				myWorld.initSword(velocityD);
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart();
			}

		} else if (keycode == Keys.DPAD_UP) {

			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.UP;
				myWorld.initSword(velocityU);
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart();
			}

		} else if (keycode == Keys.DPAD_LEFT) {

			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.LEFT;
				myWorld.initSword(velocityL);
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart();
			}
			
		} else if (keycode == Keys.DPAD_RIGHT) {
			
			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.RIGHT;
				myWorld.initSword(velocityR);
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart();
			}
		}
		
		return false;

	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public static int getShuffleState() {
		return shuffleState;
	}

	public SimpleButton getPlayButton() {
		return playButton;
	}

	public SimpleButton getRetryButton() {
		return retryButton;
	}

	public List<SimpleButton> getRetryButtons() {
		return retryButtons;
	}
}
