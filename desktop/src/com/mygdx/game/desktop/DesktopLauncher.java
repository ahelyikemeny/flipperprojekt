package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import Flipper.FlipperGame;
import hu.csanyzeg.master.Demos.DemoMyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=300;
		config.height=600;
		new LwjglApplication(new FlipperGame(false), config);
	}
}
