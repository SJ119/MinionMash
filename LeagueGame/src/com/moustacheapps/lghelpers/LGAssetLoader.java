package com.moustacheapps.lghelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LGAssetLoader {

	public static Texture characterTexture, logoTexture, texture;
	public static TextureRegion logo, playButtonUp, playButtonDown,
			backButtonUp, backButtonDown, sword, grass, upDownRoad, leftRoad,
			rightRoad, base, cs;
	// Character facing down
	public static TextureRegion[] shenFront = new TextureRegion[9];
	public static TextureRegion[] shenBack = new TextureRegion[9];
	public static TextureRegion[] shenLeft = new TextureRegion[7];
	public static TextureRegion[] shenRight = new TextureRegion[7];
	public static TextureRegion[] shenFrontMelee = new TextureRegion[9];
	public static TextureRegion[] shenBackMelee = new TextureRegion[9];
	public static TextureRegion[] shenLeftMelee = new TextureRegion[9];
	public static TextureRegion[] shenRightMelee = new TextureRegion[9];
	public static TextureRegion[] minionUp = new TextureRegion[2];
	public static TextureRegion[] minionDown = new TextureRegion[2];
	public static TextureRegion[] minionRight = new TextureRegion[2];
	public static TextureRegion[] minionLeft = new TextureRegion[2];

	public static BitmapFont font, shadow, whiteFont, font2, shadow2, whiteFont2;
	// Animate player texture regions
	public static Animation frontAttack, backAttack, leftAttack, rightAttack,
			frontAttackMelee, backAttackMelee, leftAttackMelee,
			rightAttackMelee, minionMoveTop, minionMoveDown, minionMoveRight,
			minionMoveLeft;

	public static void load() {
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 256, 114);

		characterTexture = new Texture(Gdx.files.internal("data/shen.png"));
		characterTexture
				.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		playButtonUp = new TextureRegion(texture, 156, 43, 29, 16);
		playButtonDown = new TextureRegion(texture, 185, 43, 29, 16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		backButtonUp = new TextureRegion(texture, 156, 59, 29, 16);
		backButtonDown = new TextureRegion(texture, 185, 59, 29, 16);
		backButtonUp.flip(false, true);
		backButtonDown.flip(false, true);

		sword = new TextureRegion(characterTexture, 171, 0, 3, 12);
		sword.flip(false, true);

		for (int i = 0; i < shenFront.length; i++) {
			shenFront[i] = new TextureRegion(characterTexture, (19 * i), 0, 19,
					22);
			shenFront[i].flip(false, true);
		}

		for (int i = 0; i < shenLeft.length; i++) {
			shenLeft[i] = new TextureRegion(characterTexture, (19 * i), 22, 19,
					22);
			shenLeft[i].flip(false, true);
		}

		for (int i = 0; i < shenRight.length; i++) {
			shenRight[i] = new TextureRegion(characterTexture, (19 * i), 22,
					19, 22);
			shenRight[i].flip(true, true);
		}

		for (int i = 0; i < shenBack.length; i++) {
			shenBack[i] = new TextureRegion(characterTexture, (22 * i), 44, 22,
					22);
			shenBack[i].flip(false, true);
		}

		// Shen Melee

		for (int i = 0; i < shenFrontMelee.length; i++) {
			shenFrontMelee[i] = new TextureRegion(characterTexture, 19 * i, 66,
					19, 25);
			shenFrontMelee[i].flip(false, true);
		}

		for (int i = 0; i < shenBackMelee.length; i++) {
			shenBackMelee[i] = new TextureRegion(characterTexture, (22 * i),
					91, 22, 25);
			shenBackMelee[i].flip(false, true);
		}

		for (int i = 0; i < shenLeftMelee.length; i++) {
			shenLeftMelee[i] = new TextureRegion(characterTexture,
					206 + (22 * i), 91, 22, 25);
			shenLeftMelee[i].flip(false, true);
		}

		for (int i = 0; i < shenRightMelee.length; i++) {
			shenRightMelee[i] = new TextureRegion(characterTexture,
					206 + (22 * i), 91, 22, 25);
			shenRightMelee[i].flip(true, true);
		}

		for (int i = 0; i < minionLeft.length; i++) {
			minionLeft[i] = new TextureRegion(characterTexture, 238 + (14 * i),
					0, 14, 15);
			minionLeft[i].flip(true, true);
		}

		// Minions

		for (int i = 0; i < minionUp.length; i++) {
			minionUp[i] = new TextureRegion(characterTexture, 181 + 14 * i, 0,
					14, 15);
			minionUp[i].flip(false, true);
		}

		for (int i = 0; i < minionUp.length; i++) {
			minionUp[i] = new TextureRegion(characterTexture, 182 + (14 * i),
					0, 14, 15);
			minionUp[i].flip(false, true);
		}

		for (int i = 0; i < minionDown.length; i++) {
			minionDown[i] = new TextureRegion(characterTexture, 210 + (14 * i),
					0, 14, 15);
			minionDown[i].flip(false, true);
		}

		for (int i = 0; i < minionRight.length; i++) {
			minionRight[i] = new TextureRegion(characterTexture,
					238 + (14 * i), 0, 14, 15);
			minionRight[i].flip(false, true);
		}

		for (int i = 0; i < minionLeft.length; i++) {
			minionLeft[i] = new TextureRegion(characterTexture, 238 + (14 * i),
					0, 14, 15);
			minionLeft[i].flip(true, true);
		}

		grass = new TextureRegion(characterTexture, 408, 0, 20, 20);
		grass.flip(false, true);
		upDownRoad = new TextureRegion(characterTexture, 428, 0, 36, 11);
		upDownRoad.flip(false, true);
		leftRoad = new TextureRegion(characterTexture, 440, 11, 24, 33);
		leftRoad.flip(false, true);
		rightRoad = new TextureRegion(characterTexture, 440, 11, 24, 33);
		rightRoad.flip(true, true);
		base = new TextureRegion(characterTexture, 464, 0, 48, 47);
		base.flip(false, true);

		cs = new TextureRegion(characterTexture, 266, 0, 11, 13);
		cs.flip(false, true);

		// Animations
		minionMoveTop = new Animation(0.5f, minionDown);
		minionMoveTop.setPlayMode(Animation.LOOP);

		minionMoveDown = new Animation(0.5f, minionUp);
		minionMoveDown.setPlayMode(Animation.LOOP);

		minionMoveLeft = new Animation(0.5f, minionLeft);
		minionMoveLeft.setPlayMode(Animation.LOOP);

		minionMoveRight = new Animation(0.5f, minionRight);
		minionMoveRight.setPlayMode(Animation.LOOP);

		frontAttackMelee = new Animation(0.02f, shenFrontMelee);
		backAttackMelee = new Animation(0.05f, shenBackMelee);
		leftAttackMelee = new Animation(0.03f, shenLeftMelee);
		rightAttackMelee = new Animation(0.03f, shenRightMelee);

		frontAttackMelee.setPlayMode(Animation.REVERSED);
		backAttackMelee.setPlayMode(Animation.REVERSED);
		leftAttackMelee.setPlayMode(Animation.REVERSED);
		rightAttackMelee.setPlayMode(Animation.REVERSED);

		frontAttack = new Animation(0.05f, shenFront);
		frontAttack.setPlayMode(Animation.REVERSED);

		backAttack = new Animation(0.05f, shenBack);
		backAttack.setPlayMode(Animation.REVERSED);

		leftAttack = new Animation(0.05f, shenLeft);
		leftAttack.setPlayMode(Animation.REVERSED);

		rightAttack = new Animation(0.05f, shenRight);
		rightAttack.setPlayMode(Animation.REVERSED);

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.17f, -.2f);

		whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont.setScale(.17f, -.2f);

		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.17f, -.2f);

		font2 = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font2.setScale(.25f, -.3f);

		whiteFont2 = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont2.setScale(.25f, -.3f);

		shadow2 = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow2.setScale(.255f, -.305f);

		
	}

}
