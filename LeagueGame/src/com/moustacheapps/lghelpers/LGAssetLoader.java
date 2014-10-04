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
	backButtonUp, backButtonDown, sword;
	//Character facing down
	public static TextureRegion[] shenFront = new TextureRegion[9];
	public static TextureRegion[] shenBack = new TextureRegion[10];
	public static TextureRegion[] shenLeft = new TextureRegion[7];
	public static TextureRegion[] shenRight = new TextureRegion[7];
	//Animate player texture regions
	public static Animation frontAttack, backAttack, leftAttack, rightAttack;
	
	public static void load() {
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 256, 114);

		characterTexture = new Texture(Gdx.files.internal("data/shen.png"));
		characterTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
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
			shenFront[i] = new TextureRegion(characterTexture,(19 * i), 0, 19, 22);
			shenFront[i].flip(false, true);
		}
		
		for (int i = 0; i < shenLeft.length; i++) {
			shenLeft[i] = new TextureRegion(characterTexture,(19 * i), 22, 19, 22);
			shenLeft[i].flip(false, true);
		}
		
		for (int i = 0; i < shenRight.length; i++) {
			shenRight[i] = new TextureRegion(characterTexture,(19 * i), 22, 19, 22);
			shenRight[i].flip(true, true);
		}
		
		for (int i = 0; i < shenBack.length; i++) {
			shenBack[i] = new TextureRegion(characterTexture,(22 * i), 44, 22, 22);
			shenBack[i].flip(false, true);
		}
		frontAttack = new Animation(0.05f, shenFront);
		frontAttack.setPlayMode(Animation.LOOP);
		
		backAttack = new Animation(0.05f, shenBack);
		backAttack.setPlayMode(Animation.LOOP);
		
		leftAttack = new Animation(0.10f, shenLeft);
		leftAttack.setPlayMode(Animation.LOOP);
		
		rightAttack = new Animation(0.10f, shenRight);
		rightAttack.setPlayMode(Animation.LOOP);
		
	}
	
}
