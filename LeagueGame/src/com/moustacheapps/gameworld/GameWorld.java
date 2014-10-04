package com.moustacheapps.gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moustacheapps.gameobjects.Ninja;
import com.moustacheapps.gameobjects.Sword;
import com.moustacheapps.lghelpers.AssetLoader;

public class GameWorld {

	private Rectangle ground;
	public static Rectangle leftBound, rightBound;
	private static int score = 0, lives = 0, gamesNeeded = 10, timesPlayed;
	public static float runTime = 0;
	public static int midPointY, midPointX;
	private LGGameRenderer renderer;
	private GameState currentState;
	private Ninja ninja;
	private ArrayList<Sword> swords = new ArrayList<Sword>();

	public enum GameState {
		MENU, START, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midPointY, int midPointX) {
		currentState = GameState.MENU;
		GameWorld.midPointY = midPointY;
		GameWorld.midPointX = midPointX;
		ninja = new Ninja(midPointX - 10, midPointY -11, 20, 22);
		//scroller = new ScrollHandler(this, midPointY + 66);
		ground = new Rectangle(0, midPointY + 66, 272, 11);
		leftBound = new Rectangle(0, 0, 136, midPointY * 2);
		rightBound = new Rectangle(136, 0, 136, midPointY * 2);
	}

	public static Rectangle getRectangleLeft() {
		return leftBound;
	}

	public static Rectangle getRectangleRight() {
		return rightBound;
	}

	public GameState getGameState() {
		return currentState;
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case START:
			updateRunning(delta);
			break;
		default:
			break;
		}

	}

	public void initSword(Vector2 velocity){
		Sword sword = new Sword(midPointX - 1, midPointY - 6, 3, 12, velocity);
		swords.add(sword);
	}
	
	public ArrayList<Sword> getSwords() {
		return swords;
	}

	public void updateRunning(float delta) {

		if (delta > .15f) {
			delta = .15f;
		}
		
		for (int i = 0; i < swords.size(); i++) {
			Sword s = (Sword) swords.get(i);
			s.update(delta);
		}

	}

	public Ninja getNinja() {
		return ninja;
	}

	public static int getMidPointY() {
		return midPointY;
	}

	public static int getRunTime() {
		return (int) runTime;
	}

	public static int getScore() {
		return score;
	}

	public static int getLives() {
		return lives;
	}

	public static int getGamesNeeded() {
		if (AssetLoader.getTimesPlayed() <= 10) {
			return gamesNeeded - AssetLoader.getTimesPlayed();
		}
		return 0;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public void start() {
		currentState = GameState.START;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	public void getTimesPlayedPlusOne() {
		timesPlayed = AssetLoader.getTimesPlayed() + 1;
	}

	public void restart() {
		//getTimesPlayedPlusOne();
		//AssetLoader.setTimesPlayed(timesPlayed);
		score = 0;
		lives = 0;
		ninja.restart();
		//scroller.onRestart();
		start();
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.START;
	}

	public void setRenderer(LGGameRenderer renderer) {
		this.renderer = renderer;
	}

}
