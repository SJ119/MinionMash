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
import com.badlogic.gdx.math.Intersector;
import com.moustacheapps.gameobjects.Minion;
import com.moustacheapps.gameobjects.Ninja;
import com.moustacheapps.gameobjects.Ninja.Direction;
import com.moustacheapps.gameobjects.Sword;
import com.moustacheapps.lghelpers.InputHandler;
import com.moustacheapps.lghelpers.LGAssetLoader;
import com.moustacheapps.screens.GameScreen;
import com.moustacheapps.tweenaccessors.Value;
import com.moustacheapps.tweenaccessors.ValueAccessor;
import com.moustacheapps.ui.SimpleButton;

public class LGGameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;
	private float sum;

	private int gameWidth, gameHeight, midPointY, midPointX;

	public static TextureRegion sword, shenStandard, grass, upDownRoad,
			leftRoad, rightRoad, base, cs;
	int rotation;

	private Animation shenFrontAtk, shenBackAtk, shenLeftAtk, shenRightAtk,
			frontAttackMelee, backAttackMelee, leftAttackMelee,
			rightAttackMelee, minionMoveTop, minionMoveDown, minionMoveRight,
			minionMoveLeft;

	private SimpleButton playButton, retryButton;

	private TweenManager manager;
	private Value alpha = new Value();
	private Color transitionColor;

	private Ninja ninja;
	private ArrayList<Sword> swords = new ArrayList<Sword>();
	private ArrayList<Minion> minions = new ArrayList<Minion>();

	int x = GameScreen.width / 20 + 1;
	int y = GameScreen.height / 20 + 1;
	int upDown = GameScreen.height / 22;
	int rightLeft = GameScreen.width / 48 + 1;

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
		minions = myWorld.getMinions();
	}

	void initCharacterAssets() {
		shenFrontAtk = LGAssetLoader.frontAttack;
		shenBackAtk = LGAssetLoader.backAttack;
		shenLeftAtk = LGAssetLoader.leftAttack;
		shenRightAtk = LGAssetLoader.rightAttack;
		frontAttackMelee = LGAssetLoader.frontAttackMelee;
		backAttackMelee = LGAssetLoader.backAttackMelee;
		leftAttackMelee = LGAssetLoader.leftAttackMelee;
		rightAttackMelee = LGAssetLoader.rightAttackMelee;
		minionMoveTop = LGAssetLoader.minionMoveTop;
		minionMoveDown = LGAssetLoader.minionMoveDown;
		minionMoveLeft = LGAssetLoader.minionMoveLeft;
		minionMoveRight = LGAssetLoader.minionMoveRight;
		sword = LGAssetLoader.sword;
		shenStandard = LGAssetLoader.shenFront[8];
		grass = LGAssetLoader.grass;
		upDownRoad = LGAssetLoader.upDownRoad;
		leftRoad = LGAssetLoader.leftRoad;
		rightRoad = LGAssetLoader.rightRoad;
		base = LGAssetLoader.base;
		cs = LGAssetLoader.cs;
	}

	public void render(float delta, float runTime, float runTime2) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batcher.begin();
		batcher.enableBlending();

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				batcher.draw(grass, 0 + 20 * i, 0 + 20 * j, 20, 20);
			}
		}

		for (int i = 0; i < upDown; i++) {
			batcher.draw(upDownRoad, GameWorld.midPointX - 18,
					GameWorld.midPointY + 15 + 11 * i, 36, 11);
			batcher.draw(upDownRoad, GameWorld.midPointX - 18,
					GameWorld.midPointY - 16 - 11 - 11 * i, 36, 11);
		}

		for (int i = 0; i < rightLeft; i++) {
			batcher.draw(leftRoad, GameWorld.midPointX + 24 + 24 * i,
					GameWorld.midPointY - 8, 24, 33);
			batcher.draw(rightRoad, GameWorld.midPointX - 48 - 24 * i,
					GameWorld.midPointY - 8, 24, 33);
		}

		batcher.draw(base, GameWorld.midPointX - 24, GameWorld.midPointY - 16,
				48, 47);

		if (myWorld.isMenu()) {
			playButton.draw(batcher);
		}

		if (myWorld.isGameOver() || myWorld.isHighScore()) {
			retryButton.draw(batcher);
		}

		if (ninja.direction == Direction.DOWN) {
			batcher.draw(shenFrontAtk.getKeyFrame(runTime2, false),
					ninja.getX(), ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.UP) {
			batcher.draw(shenBackAtk.getKeyFrame(runTime2, false),
					ninja.getX() - 1, ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth() + 2,
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.LEFT) {
			batcher.draw(shenLeftAtk.getKeyFrame(runTime2, false),
					ninja.getX(), ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.RIGHT) {
			batcher.draw(shenRightAtk.getKeyFrame(runTime2, false),
					ninja.getX(), ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight(), 1, 1, 0);
		} else if (ninja.direction == Direction.UPMELEE) {
			batcher.draw(backAttackMelee.getKeyFrame(runTime2, false),
					ninja.getX() - 1, ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth() + 2,
					ninja.getHeight() + 3, 1, 1, 0);

		} else if (ninja.direction == Direction.LEFTMELEE) {
			batcher.draw(leftAttackMelee.getKeyFrame(runTime2, false),
					ninja.getX() - 4, ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth() + 2,
					ninja.getHeight() + 3, 1, 1, 0);

		} else if (ninja.direction == Direction.RIGHTMELEE) {
			batcher.draw(rightAttackMelee.getKeyFrame(runTime2, false),
					ninja.getX() + 2, ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth() + 4,
					ninja.getHeight() + 3, 1, 1, 0);

		} else if (ninja.direction == Direction.DOWNMELEE) {
			batcher.draw(frontAttackMelee.getKeyFrame(runTime2, false),
					ninja.getX(), ninja.getY(), ninja.getWidth() / 2.0f,
					ninja.getHeight() / 2.0f, ninja.getWidth(),
					ninja.getHeight() + 3, 1, 1, 0);

		} else {
			batcher.draw(shenStandard, ninja.getX(), ninja.getY(),
					ninja.getWidth() / 2.0f, ninja.getHeight() / 2.0f,
					ninja.getWidth() , ninja.getHeight() + 1, 1, 1, 0);
		}
		
		if (myWorld.isGameOver()){
			int length = ("Score: " + GameWorld.cs).length();
			LGAssetLoader.shadow2.draw(batcher, "Score : " + GameWorld.cs, GameWorld.midPointX
					- length * 5, 18);
			LGAssetLoader.font2.draw(batcher, "Score : " + GameWorld.cs, GameWorld.midPointX
					- length * 5, 18);
		}

		
		if (myWorld.isMenu()){
			int length = ("Minion Mash").length();
			LGAssetLoader.shadow2.draw(batcher, "Minion Mash", GameWorld.midPointX
					- length * 5, 18);
			LGAssetLoader.font2.draw(batcher, "Minion Mash", GameWorld.midPointX
					- length * 5, 17);
		}
		
		if (myWorld.isRunning()) {

			for (int i = 0; i < minions.size(); i++) {

				Minion m = minions.get(i);
				if (m.isUp()) {
					batcher.draw(minionMoveTop.getKeyFrame(runTime), m.getX(),
							m.getY(), m.getWidth(), m.getHeight());
				} else if (m.isDown()) {
					batcher.draw(minionMoveDown.getKeyFrame(runTime), m.getX(),
							m.getY(), m.getWidth(), m.getHeight());
				} else if (m.isLeft()) {
					batcher.draw(minionMoveLeft.getKeyFrame(runTime), m.getX(),
							m.getY(), m.getWidth(), m.getHeight());
				} else if (m.isRight()) {
					batcher.draw(minionMoveRight.getKeyFrame(runTime),
							m.getX(), m.getY(), m.getWidth(), m.getHeight());
				}

			}

			int length = ("" + GameWorld.cs).length();
			batcher.draw(cs, 8, 17, 11, 13);
			LGAssetLoader.shadow.draw(batcher, ": " + GameWorld.cs, 22, 18);
			LGAssetLoader.font.draw(batcher, ": " + GameWorld.cs, 22, 18);

			for (int i = 0; i < swords.size(); i++) {

				Sword s = (Sword) swords.get(i);
				if (s.velocity.x > 0) {
					rotation = 270;
				} else if (s.velocity.x < 0) {
					rotation = 90;
				} else if (s.velocity.y > 0) {
					rotation = 0;
				} else if (s.velocity.y < 0) {
					rotation = 180;
				}
				batcher.draw(sword, s.getX(), s.getY(), s.getWidth() / 2.0f,
						s.getHeight() / 2.0f, s.getWidth(), s.getHeight(), 1,
						1, rotation);

			}
		}

		batcher.end();
		

		shapeRenderer.begin(ShapeType.Filled);
		
		if (myWorld.isRunning()) {
		shapeRenderer.setColor(128 / 255.0f, 128 / 255.0f, 136 / 255.0f, 1);
		shapeRenderer.rect(7, 4, 123, 11);
		shapeRenderer.setColor(184 / 255.0f, 200 / 255.0f, 224 / 255.0f, 1);
		shapeRenderer.rect(7, 4, 122, 10);
		shapeRenderer.setColor(128 / 255.0f, 128 / 255.0f, 136 / 255.0f, 1);
		shapeRenderer.rect(8, 5, 120, 5);
		shapeRenderer.rect(8, 11, 120, 2);
		shapeRenderer.setColor(1 / 255.0f, 170 / 255.0f, 1 / 255.0f, 1);
		shapeRenderer.rect(8, 5, ninja.health, 5);
		shapeRenderer.setColor(254 / 255.0f, 221 / 255.0f, 0 / 255.0f, 1);
		shapeRenderer.rect(8, 11, ninja.energy, 2);

		shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
		for (int i = 0; i < 3; i++) {
			shapeRenderer.rect(38 + 30 * i, 5, 1, 5);
		}
		}
		shapeRenderer.end();
		
		

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
