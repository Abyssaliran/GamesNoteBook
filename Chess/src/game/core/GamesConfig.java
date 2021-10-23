package game.core;

import java.io.File;

public class GamesConfig {
	public static String projectRoot;
	public static String pgnRoot;

	static {
		File f = new File(".");

		projectRoot = f.getAbsolutePath();
		System.out.println("Project root: " + projectRoot);

		pgnRoot = projectRoot.replace(".", "pgn");

		System.out.println("PGN root: " + pgnRoot);
		System.out.println();
	}

}