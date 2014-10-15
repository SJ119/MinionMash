package com.moustacheapps.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.moustacheapps.gameworld.GameWorld;
import com.moustacheapps.gameworld.LGGameRenderer;

public class MinionGenerator {

	// classes
	private GameWorld gameWorld;

	// variables
	private Random r;
	private List<Integer> sequence = new ArrayList<Integer>();
	private int direction, position, position2, x, minionsSlain;
	private float threshold = (float) 1.5, threshold2 = (float) 1,
			timeSinceGenerate = 0, counter = 0;
	private boolean isOn = false;
	public static ArrayList<Minion> minions = new ArrayList<Minion>();
	private Vector2 velocity;
	private Minion minion, minion2;
	private Ninja ninja;
	private ArrayList<Sword> swords = new ArrayList<Sword>();
	private Sword sword;
	private float runTime = 0;
	private LGGameRenderer renderer;

	public MinionGenerator(GameWorld gameworld, Ninja ninja) {
		// Initializers
		position = -1;
		position2 = -1;
		this.gameWorld = gameworld;
		minionsSlain = 0;
		r = new Random();
		this.ninja = ninja;

		// generate a random sequence
		addMinions();
		
	}

	public void addMinions() {

		direction = r.nextInt(4);
		switch (direction) {
		// minion is 13 x 15 pixels
		// down
		case 3:
			velocity = new Vector2(0, 30);
			minion = new Minion(GameWorld.midPointX - 7, -15, 14, 15, velocity);
			break;
		// up
		case 2:
			velocity = new Vector2(0, -30);
			minion = new Minion(GameWorld.midPointX - 7,
					GameWorld.midPointY * 2, 14, 15, velocity);
			break;
		// left
		case 1:
			velocity = new Vector2(30, 0);
			minion = new Minion(-14, GameWorld.midPointY - 8, 14, 15, velocity);
			break;
		// right
		default:
			velocity = new Vector2(-30, 0);
			minion = new Minion(GameWorld.midPointX * 2 + 14,
					GameWorld.midPointY - 8, 14, 15, velocity);
			break;
		}

		minions.add(minion);
	}

	// update board
	public void update(float delta, ArrayList<Sword> swords) {
		runTime += delta;
		//System.out.println(runTime);
		if (runTime > (1 / Math.log(2 + Math.log(2+ GameWorld.cs)))) {
			runTime = 0;
			addMinions();
			// AssetLoader.coin.play((float) 0.5);
			// GameWorld.round += 1;
		}

		for (int i = 0; i < minions.size(); i++) {

			minion2 = minions.get(i);
			minion2.update(delta);
			
			if (Intersector.overlaps(ninja.getBoundingRectangle(),
					minion2.getBoundingRectangle())) {
				minions.remove(i);
				ninja.health -= 30;
				
				renderer.prepareTransition(255, 0, 0, 0.6f);
			}

			for (int j = 0; j < swords.size(); j++) {

				sword = swords.get(j);

				if (Intersector.overlaps(sword.getBoundingRectangle(),
						minion2.getBoundingRectangle())) {
					// System.out.println("true");
					GameWorld.cs += 1;
					if (ninja.health + 1 <= 120) {
						ninja.health += 1;
					}
					// System.out.println(GameWorld.cs);
					minions.remove(i);
					swords.remove(j);
					
				}

			}
		}

	}

	public ArrayList<Minion> getMinions() {
		return minions;
	}

	public void restart() {
		minions.clear();
	}
	
	public void setRenderer(LGGameRenderer renderer) {
		this.renderer = renderer;
	}
	
}
