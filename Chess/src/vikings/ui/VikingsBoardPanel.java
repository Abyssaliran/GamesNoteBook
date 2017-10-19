package vikings.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.GameBoard;
import vikings.Vikings;
import vikings.pieces.Viking;
import vikings.pieces.Сyning;
import vikings.ui.images.VikingImages;

/**
 * Доска для игры 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB">Викинги (Хнефатафл, Тавлеи) </a>.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class VikingsBoardPanel extends GameBoard {
	private static final Color LINE_COLOR = new Color(null, 0,   0, 0);
	private static final Color FILL_COLOR = new Color(null, 0, 192, 0);

	public VikingsBoardPanel(Composite parent, int boardSize) {
		super(parent, Vikings.getInitBoard(boardSize));
	}

	@Override
	public Image getPieceImage(Piece piece) {
		if (piece instanceof Viking)
			return piece.getColor() == PieceColor.WHITE 
				? VikingImages.imageVikingWhite
				: VikingImages.imageVikingBlack;
		
		if (piece instanceof Сyning)
			return piece.getColor() == PieceColor.WHITE 
				? VikingImages.imageСyningWhite
				: VikingImages.imageСyningBlack;

		return null;
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		gc.setBackground(FILL_COLOR);
		gc.fillRectangle(area);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		gc.setForeground(LINE_COLOR);
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}
}
