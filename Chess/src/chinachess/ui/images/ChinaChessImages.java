package chinachess.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Класс для доступа к уникальным изображениям шахматных фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChinaChessImages {
	public static Image iconChinaChess;

	static {
		load( Display.getCurrent() );
	}

	private static void load(final Display display) {
		iconChinaChess = new Image(display, ChinaChessImages.class.getResourceAsStream("ChinaChess.png"));
	}  
} 
