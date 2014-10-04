package com.moustacheapps.leaguegame;

import com.badlogic.gdx.Game;
import com.moustacheapps.lghelpers.AssetLoader;
import com.moustacheapps.lghelpers.LGAssetLoader;
import com.moustacheapps.screens.SplashScreen;

public class LGGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		LGAssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}