package reversi.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import reversi.Reversi;
import reversi.pieces.Hole;
import reversi.ui.images.ReversiImages;

/**
 * Доска для игры в 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiBoardPanel extends GreenBoard {
	/**
	 * Создать доску для игры в реверси.
	 * 
	 * @param composite - составной элемент содержащий доску
	 * @param nHoles - количество случайно расположенных отверстий в доске.
	 */
	public ReversiBoardPanel(Composite composite, int nHoles) {
		super(composite, Reversi.getInitBoard(nHoles));
	}

	@Override
	public Image getPieceImage(Piece piece) {
		if (piece instanceof Hole)
			return ReversiImages.imageHoleBlack;
		
		return null;
	}
}
