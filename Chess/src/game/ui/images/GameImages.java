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
	
	/**
	 * Изображения на гранях кубика.
	 */
	private static Image[] cubes;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		woodDark   = new Image(display, GameImages.class.getResourceAsStream("wood_dark.png"));
		woodLight  = new Image(display, GameImages.class.getResourceAsStream("wood_light.png"));
		woodMedium = new Image(display, GameImages.class.getResourceAsStream("wood_medium.png"));
		
		papiro     = new Image(display, GameImages.class.getResourceAsStream("papiro.png"));

		cubes = new Image[6];
		cubes[0]   = new Image(display, GameImages.class.getResourceAsStream("cube1.png"));
		cubes[1]   = new Image(display, GameImages.class.getResourceAsStream("cube2.png"));
		cubes[2]   = new Image(display, GameImages.class.getResourceAsStream("cube3.png"));
		cubes[3]   = new Image(display, GameImages.class.getResourceAsStream("cube4.png"));
		cubes[4]   = new Image(display, GameImages.class.getResourceAsStream("cube5.png"));
		cubes[5]   = new Image(display, GameImages.class.getResourceAsStream("cube6.png"));
	}
	
	/**
	 * Для заданного значения выдать изображение этого значения на кубике.
	 * 
	 * @param n - значение
	 * @return изображение значения.
	 */
	static
	public Image getCubeImage(int n) {
		return cubes[n-1];
	}
} 
