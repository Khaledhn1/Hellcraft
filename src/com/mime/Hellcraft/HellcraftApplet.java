package com.mime.Hellcraft;

import java.applet.Applet;
import java.awt.BorderLayout;

public class HellcraftApplet extends Applet {
	private static final long serialVersionUID = 1L;

	private Display display = new Display();

	public void init() {

		setLayout(new BorderLayout());
		add(display);
	}

	public void start() {
		display.start();
	}

	public void stop() {
		display.stop();
	}

}
