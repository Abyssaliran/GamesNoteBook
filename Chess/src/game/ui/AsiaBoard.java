package game.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

/**
 * Доска для азиатских игр. 
 * В центре клетки пересекаются линии. Клетки не закрашиваются.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class AsiaBoard extends GameBoard {
	private static final Color colorYellow = new Color(null, 255, 255,0);

	public AsiaBoard(Composite parent, Board board) {
		super(parent, board);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		gc.setBackground( colorYellow );
		gc.fillRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	
		int dv = squareWidth/2;
		int dh = squareHeight/2;
		
		int x = v * squareWidth  + dv;
		int y = h * squareHeight + dh;

		if (v !=          0) gc.drawLine(x, y, x - dv, y);
		if (v != board.nV-1) gc.drawLine(x, y, x + dv, y);

		if (h !=          0) gc.drawLine(x, y, x, y - dh);
		if (h != board.nH-1) gc.drawLine(x, y, x, y + dh);
	}
}
