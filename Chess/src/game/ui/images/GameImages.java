package game.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Класс для доступа к изображениям общим для всех игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameImages {
	public static Image woodDark;
	public static Image woodLight;
	public static Image woodMedium;
	public static Image papiro;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		woodDark   = new Image(display, GameImages.class.getResourceAsStream("wood_dark.png"));
		woodLight  = new Image(display, GameImages.class.getResourceAsStream("wood_light.png"));
		woodMedium = new Image(display, GameImages.class.getResourceAsStream("wood_medium.png"));
		
		papiro     = new Image(display, GameImages.class.getResourceAsStream("papiro.png"));
	}  
} 
