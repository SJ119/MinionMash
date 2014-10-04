package com.moustacheapps.leaguegame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moustacheapps.leaguegame.LGGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DoubleFlap";
		cfg.useGL20 = true;
		cfg.height = 1920 / 3;
		cfg.width = 1080 / 3;

		new LwjglApplication(new LGGame(), cfg);
	}
}
