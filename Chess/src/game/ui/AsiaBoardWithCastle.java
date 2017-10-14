package game.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

public abstract class AsiaBoardWithCastle extends AsiaBoard {

	public AsiaBoardWithCastle(Composite parent, Board board) {
		super(parent, board);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		super.drawSquare(gc, v, h, squareWidth, squareHeight);
		
//		if ((6 > h) && (h > 3)) return;
		
		if ((v == 4) && ((h == 1) || (h == 6)) ) {
			int dv = squareWidth/2;
			int dh = squareHeight/2;
			
			int x = v * squareWidth  + dv;
			int y = h * squareHeight + dh;
			
			gc.drawLine(x, y, x - dv, y - dh);
			gc.drawLine(x, y, x + dv, y + dh);
			gc.drawLine(x, y, x - dv, y + dh);
			gc.drawLine(x, y, x + dv, y - dh);
		}
		
	}
}
