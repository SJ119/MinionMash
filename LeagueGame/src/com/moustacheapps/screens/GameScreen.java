package com.moustacheapps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.moustacheapps.gameworld.GameWorld;
import com.moustacheapps.gameworld.LGGameRenderer;
import com.moustacheapps.lghelpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private LGGameRenderer lgRenderer;
	private float runTime;
	public static float runTime2;
	private float ratio;
	public static int width;
	public static int height;

	// This is the constructor, not the class declaration
	public GameScreen() {

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
	    int gameWidth = 136;
		float ratio = screenWidth / gameWidth;
		int gameHeight = (int) (screenHeight / ratio);
		int midPointY = (int) (gameHeight / 2);
		int midPointX = (int) (gameWidth / 2);
		width = gameWidth;
		height = gameHeight;
		//System.out.println(gameWidth);
		world = new GameWorld(midPointY, midPointX);
		Gdx.input.setInputProcessor(new InputHandler(world, ratio, ratio, gameWidth, gameHeight));
		//renderer = new GameRenderer(world, (int) gameHeight, midPointY,
		//		(int) screenWidth, (int) screenHeight);
		lgRenderer = new LGGameRenderer(world, gameWidth, gameHeight, midPointY, midPointX);
		world.setRenderer(lgRenderer);
		
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		runTime2 += delta;
		world.update(delta);
		//renderer.render(delta, runTime);
		lgRenderer.render(delta, runTime, runTime2);
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
