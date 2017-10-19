package tamerlan.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Изображения для 
 * <a href="https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">Шахмат Тамерлана</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChessImages {
	/**
	 * Пиктограмма для шахмат Тамерлана.
	 */
	public static Image iconTamerlanChess;

	public static Image imageKingWhite;
	public static Image imageQueenWhite;
	public static Image imageBishopWhite;
	public static Image imageKnightWhite;
	public static Image imageRookWhite;
	public static Image imagePawnWhite;

	public static Image imageKingBlack;
	public static Image imageQueenBlack;
	public static Image imageBishopBlack;
	public static Image imageKnightBlack;
	public static Image imageRookBlack;
	public static Image imagePawnBlack;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		iconTamerlanChess = new Image(display, TamerlanChessImages.class.getResourceAsStream("TamerlanChess.gif"));;

		imageKingBlack   = new Image(display, TamerlanChessImages.class.getResourceAsStream("bKingZurich.gif"));
		imageQueenBlack  = new Image(display, TamerlanChessImages.class.getResourceAsStream("bQueenZurich.gif"));
		imageBishopBlack = new Image(display, TamerlanChessImages.class.getResourceAsStream("bBishopZurich.gif"));
		imageKnightBlack = new Image(display, TamerlanChessImages.class.getResourceAsStream("bKnightZurich.gif"));
		imageRookBlack   = new Image(display, TamerlanChessImages.class.getResourceAsStream("bRookZurich.gif"));
		imagePawnBlack   = new Image(display, TamerlanChessImages.class.getResourceAsStream("bPawnZurich.gif"));
		
		imageKingWhite   = new Image(display, TamerlanChessImages.class.getResourceAsStream("wKingZurich.gif"));
		imageQueenWhite  = new Image(display, TamerlanChessImages.class.getResourceAsStream("wQueenZurich.gif"));
		imageBishopWhite = new Image(display, TamerlanChessImages.class.getResourceAsStream("wBishopZurich.gif"));
		imageKnightWhite = new Image(display, TamerlanChessImages.class.getResourceAsStream("wKnightZurich.gif"));
		imageRookWhite   = new Image(display, TamerlanChessImages.class.getResourceAsStream("wRookZurich.gif"));
		imagePawnWhite   = new Image(display, TamerlanChessImages.class.getResourceAsStream("wPawnZurich.gif"));
	}  
}
