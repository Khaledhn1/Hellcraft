package com.mime.Hellcraft.graphics;

import java.util.Random;

import com.mime.Hellcraft.Game;
import com.mime.Hellcraft.input.Controller;

public class Render3D extends Render {

	public double[] zBuffer;
	private double rendeDistance = 5000;

	private double forwardGlobal;

	public Render3D(int width, int height) {
		super(width, height);

		zBuffer = new double[width * height];
	}

	public void floor(Game game) {

		double floorposition = 8;
		double ceallingposition = 8;
		double forward = game.controls.z;
		forwardGlobal = forward;
		double right = game.controls.x;
		double up = game.controls.y;
		double walking = Math.sin(game.time / 6.0) * 0.3;
		if (Controller.crouchWalk) {
			walking = Math.sin(game.time / 6.0) * 0.2;
		}

		if (Controller.runWalk) {
			walking = Math.sin(game.time / 6.0) * 1;
		}

		double rotation = game.controls.rotation;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double cealling = (y - height / 2.0) / height;

			double z = (floorposition + up) / cealling;
			if (Controller.walk) {
				z = (floorposition + up + walking) / cealling;
			}

			if (cealling < 0) {
				z = (ceallingposition - up) / -cealling;
				if (Controller.walk) {
					z = (floorposition - up - walking) / -cealling;
				}
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7)
						+ (yPix & 7) * 8];

				if (z > 500) {
					pixels[x + y * width] = 0;
				}
			}

		}

	}

	public void walls() {
		Random random = new Random(100);
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble();
			double yy = random.nextDouble();
			double zz = 1.5;

			int xPixel = (int) (xx / zz * height / 2 + width / 2);
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 & yPixel >= 0 & xPixel < width & yPixel < height) {

				pixels[xPixel + yPixel * width] = 0xfffff;
			}
		}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble() - 1;
			double yy = random.nextDouble();
			double zz = 1.5;

			int xPixel = (int) (xx / zz * height / 2 + width / 2);
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 & yPixel >= 0 & xPixel < width & yPixel < height) {

				pixels[xPixel + yPixel * width] = 0xfffff;
			}
		}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble() - 1;
			double yy = random.nextDouble() - 1;
			double zz = 1.5;

			int xPixel = (int) (xx / zz * height / 2 + width / 2);
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 & yPixel >= 0 & xPixel < width & yPixel < height) {

				pixels[xPixel + yPixel * width] = 0xfffff;
			}
		}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble();
			double yy = random.nextDouble() - 1;
			double zz = 1.5;

			int xPixel = (int) (xx / zz * height / 2 + width / 2);
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 & yPixel >= 0 & xPixel < width & yPixel < height) {

				pixels[xPixel + yPixel * width] = 0xfffff;
			}
		}

	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (rendeDistance / (zBuffer[i]));

			if (brightness < 0) {
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}
