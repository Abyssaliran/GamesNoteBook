package game.core;

import java.io.File;

public class GamesConfig {
	public static String projectRoot;
	public static String pngRoot;

	static {
		File f = new File(".");

		projectRoot = f.getAbsolutePath();
		System.out.println("Project root: " + projectRoot);

		pngRoot = projectRoot.replace(".", "png");
		System.out.println("PGN root: " + pngRoot);
		System.out.println();
	}

}