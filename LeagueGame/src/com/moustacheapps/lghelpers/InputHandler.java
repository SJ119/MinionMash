package com.moustacheapps.lghelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.moustacheapps.gameobjects.Minion;
import com.moustacheapps.gameobjects.MinionGenerator;
import com.moustacheapps.gameobjects.Ninja;
import com.moustacheapps.gameobjects.Ninja.Direction;
import com.moustacheapps.gameworld.GameWorld;
import com.moustacheapps.screens.GameScreen;
import com.moustacheapps.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	private Ninja ninja;

	private GameWorld myWorld;

	private List<SimpleButton> inputSections, retryButtons;

	private ArrayList<Minion> minions = new ArrayList<Minion>();

	private Minion minion;

	private SimpleButton playButton, retryButton, leftTap, rightTap, fullTap;

	private float scaleFactorX;
	private float scaleFactorY;
	public static int shuffleState = 0;
	public int gameWidth, gameHeight;

	private Vector2 velocityU = new Vector2(0, -200);
	private Vector2 velocityD = new Vector2(0, 200);
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
		this.minions = MinionGenerator.minions;
		inputSections = new ArrayList<SimpleButton>();

		playButton = new SimpleButton(gameWidth / 2
				- (LGAssetLoader.playButtonUp.getRegionWidth() / 2),
				midPointY + 60, 29, 16, LGAssetLoader.playButtonUp,
				LGAssetLoader.playButtonDown);
		leftTap = new SimpleButton(0, 0, 136, GameWorld.getMidPointY() * 2,
				null, null);
		rightTap = new SimpleButton(136, 0, 136, GameWorld.getMidPointY() * 2,
				null, null);
		fullTap = new SimpleButton(0, 0, gameWidth, gameHeight, null, null);
		retryButton = new SimpleButton(gameWidth / 2
				- (LGAssetLoader.backButtonUp.getRegionWidth() / 2),
				midPointY + 60, 29, 16, LGAssetLoader.backButtonUp,
				LGAssetLoader.backButtonDown);

		inputSections.add(leftTap);
		inputSections.add(rightTap);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		float ratio = ((float) gameHeight / (float) gameWidth);
		boolean aboveDownRight = (screenX * ratio > screenY);
		boolean aboveUpRight = (screenX < (gameWidth - screenY / ratio));

		if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX, screenY);
		} else if (myWorld.isRunning()) {
			if (fullTap.isTouchDown(screenX, screenY)) {
				if (aboveDownRight) {
					if (aboveUpRight) {
						ninja.direction = Direction.UP;
						if (!checkDistance()) {
							myWorld.initSword(velocityU);
							
						} else {
							ninja.direction = Direction.UPMELEE;
							
						}
					} else {
						ninja.direction = Direction.RIGHT;
						if (!checkDistance()) {
							myWorld.initSword(velocityR);
						} else {
							ninja.direction = Direction.RIGHTMELEE;
						}
					}
				} else {
					if (aboveUpRight) {
						ninja.direction = Direction.LEFT;
						if (!checkDistance()) {
							myWorld.initSword(velocityL);
						} else {
							ninja.direction = Direction.LEFTMELEE;
						}
					} else {
						ninja.direction = Direction.DOWN;
						if (!checkDistance()) {
							myWorld.initSword(velocityD);
						} else {
							ninja.direction = Direction.DOWNMELEE;
						}
					}
				}
			}
			GameScreen.runTime2 = 0;
		} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			retryButton.isTouchDown(screenX, screenY);
			leftTap.isTouchDown(screenX, screenY);
			rightTap.isTouchDown(screenX, screenY);
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
				myWorld.menu();
				return true;
			} else if (leftTap.isTouchUp(screenX, screenY)
					|| rightTap.isTouchUp(screenX, screenY)) {
				//myWorld.restart();
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
				if (!checkDistance()) {
					myWorld.initSword(velocityD);
				} else {
					ninja.direction = Direction.DOWNMELEE;
				}
				GameScreen.runTime2 = 0;
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.menu();
			}

		} else if (keycode == Keys.DPAD_UP) {

			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.UP;
				if (!checkDistance()) {
					myWorld.initSword(velocityU);
				} else {
					ninja.direction = Direction.UPMELEE;
				}
				GameScreen.runTime2 = 0;
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.menu();
			}

		} else if (keycode == Keys.DPAD_LEFT) {

			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.LEFT;
				if (!checkDistance()) {
					myWorld.initSword(velocityL);
				} else {
					ninja.direction = Direction.LEFTMELEE;
				}
				GameScreen.runTime2 = 0;
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.menu();
			}

		} else if (keycode == Keys.DPAD_RIGHT) {

			if (myWorld.isMenu()) {
				myWorld.start();
			} else if (myWorld.isRunning()) {
				ninja.direction = Direction.RIGHT;
				if (!checkDistance()) {
					myWorld.initSword(velocityR);
				} else {
					ninja.direction = Direction.RIGHTMELEE;
				}
				GameScreen.runTime2 = 0;
			} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.menu();
			}
		}

		return false;

	}

	public boolean checkDistance() {
		for (int i = 0; i < minions.size(); i++) {
			minion = minions.get(i);

			if (ninja.direction == Direction.LEFT
					&& minion.velocity.x > 0
					&& Intersector.overlaps(ninja.boundingRectangleMelee,
							minion.boundingRectangle)) {
				minions.remove(i);
				GameWorld.cs += 1;
				return true;
			} else if (ninja.direction == Direction.RIGHT
					&& minion.velocity.x < 0
					&& Intersector.overlaps(ninja.boundingRectangleMelee,
							minion.boundingRectangle)) {
				minions.remove(i);
				GameWorld.cs += 1;
				return true;
			} else if (ninja.direction == Direction.UP
					&& minion.velocity.y > 0
					&& Intersector.overlaps(ninja.boundingRectangleMelee,
							minion.boundingRectangle)) {
				minions.remove(i);
				GameWorld.cs += 1;
				return true;
			} else if (ninja.direction == Direction.DOWN
					&& minion.velocity.y < 0
					&& Intersector.overlaps(ninja.boundingRectangleMelee,
							minion.boundingRectangle)) {
				minions.remove(i);
				GameWorld.cs += 1;
				return true;
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
