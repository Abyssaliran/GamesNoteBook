/**
 * 
 */
package go.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.AsiaBoard;

import go.Go;
import go.ui.images.GoImages;

/**
 * Панель для игры <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GoBoardPanel extends AsiaBoard {
	public GoBoardPanel(Composite parent, int boardSize) {
		super(parent, Go.getInitBoard(boardSize, boardSize));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see game.ui.GameBoard#getPieceImage(game.core.Piece)
	 */
	@Override
	public Image getPieceImage(Piece piece) {
		return piece.getColor() == PieceColor.WHITE 
				? GoImages.imageStoneWhite
				: GoImages.imageStoneBlack;
	}
}
