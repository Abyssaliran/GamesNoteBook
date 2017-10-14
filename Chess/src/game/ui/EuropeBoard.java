package game.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

/**
 * Европейская доска с двухцветными клетками.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class EuropeBoard extends GameBoard {
	private static final Color colorWhite = new Color(null, 255, 255, 255);
	private static final Color colorGreen = new Color(null,   0, 192,   0);

	public EuropeBoard(Composite composite, Board board) {
		super(composite, board);
	}

	/* (non-Javadoc)
	 * @see game.ui.GameBoard#drawSquare(int, int, org.eclipse.swt.graphics.GC, int, int)
	 */
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		boolean isWhiteSquare = ((v + h) % 2 == 0);
		Color squareColor = isWhiteSquare ? colorWhite : colorGreen;

		gc.setBackground(squareColor);
		gc.fillRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);

		gc.setForeground(new Color(null, 0, 0, 0));
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}
}
