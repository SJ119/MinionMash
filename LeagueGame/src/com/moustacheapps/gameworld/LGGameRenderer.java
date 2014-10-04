package com.moustacheapps.gameworld;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.moustacheapps.gameobjects.Ninja;
import com.moustacheapps.gameobjects.Ninja.Direction;
import com.moustacheapps.gameobjects.Sword;
import com.moustacheapps.lghelpers.InputHandler;
import com.moustacheapps.lghelpers.LGAssetLoader;
import com.moustacheapps.tweenaccessors.Value;
import com.moustacheapps.tweenaccessors.ValueAccessor;
import com.moustacheapps.ui.SimpleButton;

public class LGGameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int gameWidth, gameHeight, midPointY, midPointX;

	public static TextureRegion[] shenFront = new TextureRegion[9];
	public static TextureRegion[] shenBack = new TextureRegion[10];
	public static TextureRegion[] shenLeft = new TextureRegion[7];
	public static TextureRegion[] shenRight = new TextureRegion[7];
	public static TextureRegion sword;
	int rotation;

	private Animation shenFrontAtk, shenBackAtk, shenLeftAtk, shenRightAtk;

	private SimpleButton playButton, retryButton;

	private TweenManager manager;
	private Value alpha = new Value();
	private Color transitionColor;

	private Ninja ninja;
	private ArrayList<Sword> swords = new ArrayList<Sword>();

	public LGGameRenderer(GameWorld world, int gameWidth, int gameHeight,
			int midPointY, int midPointX) {
		this.midPointX = midPointX;
		this.midPointY = midPointY;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		myWorld = world;
		this.playButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getPlayButton();
		this.retryButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getRetryButton();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initCharacterAssets();
		initGameObjects();

		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
	}

	void initGameObjects() {
		ninja = myWorld.getNinja();
		swords = myWorld.getSwords();
	}

	void initCharacterAssets() {
		shenFrontAtk = LGAssetLoader.frontAttack;
		shenBackAtk = LGAssetLoader.backAttack;
		shenLeftAtk = LGAssetLoader.leftAttack;
		shenRightAtk = LGAssetLoader.rightAttack;
		sword = LGAssetLoader.sword;
	}

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(0 / 255.0f, 165 / 255.0f, 231 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midPointY);

		// Draw Grass
		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 66, 136, 11);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		// shapeRenderer.rect(0, midPointY + 77, 136, 52);
		shapeRenderer.rect(midPointX - 11, 50, 22, gameHeight - 100);
		shapeRenderer.rect(10, midPointY - 11, gameWidth - 20, 22);

		shapeRenderer.end();

		batcher.begin();
		batcher.enableBlending();

		if (myWorld.isMenu()) {
			playButton.draw(batcher);
		}

		if (myWorld.isGameOver() || myWorld.isHighScore()) {
			retryButton.draw(batcher);
		}
		if (ninja.direction == Direction.DOWN) {
			batcher.draw(shenFrontAtk.getKeyFrame(runTime), ninja.getX(),
					ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.UP) {
			batcher.draw(shenBackAtk.getKeyFrame(runTime), ninja.getX(),
					ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.LEFT) {
			batcher.draw(shenLeftAtk.getKeyFrame(runTime), ninja.getX(),
					ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.RIGHT) {
			batcher.draw(shenRightAtk.getKeyFrame(runTime), ninja.getX(),
					ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else {
			batcher.draw(LGAssetLoader.shenFront[8], ninja.getX(),
					ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		}
//		batcher.end();
//		
//		shapeRenderer.begin(ShapeType.Filled);
//		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
//		for (int i = 0; i < swords.size(); i++) {
//			Sword s = (Sword) swords.get(i);
//			shapeRenderer.rect(s.getX(), s.getY(), s.getXX(), s.getYY());
//		}
//		shapeRenderer.end();
//		batcher.begin();
//		batcher.enableBlending();
		for (int i = 0; i < swords.size(); i++) {

			Sword s = (Sword) swords.get(i);
			if (s.velocity.x > 0) {
				rotation = 90;
			} else if (s.velocity.x < 0) {
				rotation = 270;
			} else if (s.velocity.y > 0) {
				rotation = 0;
			} else if (s.velocity.y < 0) {
				rotation = 180;
			}
			batcher.draw(sword, s.getX(), s.getY(), s.getWidth() / 2.0f,
					s.getHeight() / 2.0f, s.getWidth(), s.getHeight(), 1, 1,
					rotation);

		}

		batcher.end();

		

		drawTransition(delta);
	}

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g,
					transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, 136, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);

		}
	}

}
