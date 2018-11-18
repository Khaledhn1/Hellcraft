package com.mime.Hellcraft.graphics;

import java.util.Random;
import com.mime.Hellcraft.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render;

	public Screen(int Width, int Height) {
		super(Width, Height);
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}

	}

	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}

		render.floor(game);
		render.renderDistanceLimiter();
		render.walls();
		
		draw(render, 0, 0);
	}
}