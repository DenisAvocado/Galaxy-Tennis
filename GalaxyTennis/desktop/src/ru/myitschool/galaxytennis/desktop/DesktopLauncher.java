package ru.myitschool.galaxytennis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.myitschool.galaxytennis.GTennis;
//import ru.myitschool.galaxytennis.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GTennis.SCR_WIDTH;
		config.height = GTennis.SCR_HEIGHT;
		config.title = "Galaxy Tennis";
		new LwjglApplication(new GTennis(), config);

	}
}
