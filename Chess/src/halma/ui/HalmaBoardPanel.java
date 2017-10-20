/**
 * 
 */
package halma.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.AsiaBoard;
import halma.Halma;
import halma.ui.images.HalmaImages;

/**
 * Доска для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a> 
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class HalmaBoardPanel extends AsiaBoard {
	public HalmaBoardPanel(Composite parent, int boardSize) {
		super(parent, Halma.getInitBoard(boardSize));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see game.ui.GameBoard#getPieceImage(game.core.Piece)
	 */
	@Override
	public Image getPieceImage(Piece piece) {
		return piece.getColor() == PieceColor.WHITE 
				? HalmaImages.imageStoneWhite
				: HalmaImages.imageStoneBlack;
	}
}
