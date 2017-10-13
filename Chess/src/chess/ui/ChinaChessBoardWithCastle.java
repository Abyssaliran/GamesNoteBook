package chess.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

public class ChinaChessBoardWithCastle extends ChinaChessBoard {

	public ChinaChessBoardWithCastle(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h) {
		super.drawSquare(squareWidth, squareHeight, gc, v, h);
		
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
