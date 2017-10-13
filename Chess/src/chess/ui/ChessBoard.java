package chess.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import game.ui.GameBoard;

/**
 * @author Romanov_V_Y Шахматная доска.
 */
public class ChessBoard extends GameBoard {
	public ChessBoard(Composite composite, int style) {
		super(composite, style);
	}

	public void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h) {
		boolean isWhiteSquare = ((v + h) % 2 == 0);
		Color squareColor = isWhiteSquare 
				                 ? new Color(null, 255, 255, 255) 
		                         : new Color(null,   0, 192,   0);
		
		 gc.setBackground(squareColor);
		 gc.fillRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);

		 gc.setForeground(new Color(null, 0, 0, 0));
		 gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}
}
