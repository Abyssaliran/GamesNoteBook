package chess.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import game.ui.GameBoard;

public class ChinaChessBoard extends GameBoard {

	public ChinaChessBoard(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h) {
		// TODO отрисовать клетки доски китайских шахмат.
		gc.setBackground(new Color(null, 255, 255,0) );
		gc.fillRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	
		int dv = squareWidth/2;
		int dh = squareHeight/2;
		
		int x = v * squareWidth  + dv;
		int y = h * squareHeight + dh;

		if (v !=       0) gc.drawLine(x, y, x - dv, y);
		if (v !=    nV-1) gc.drawLine(x, y, x + dv, y);


		if (h !=       0) gc.drawLine(x, y, x, y - dh);
		if (h !=    nH-1) gc.drawLine(x, y, x, y + dh);
}
}
