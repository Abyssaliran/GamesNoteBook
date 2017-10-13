package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

/**
 * Доска для азиатских игр. 
 * В центре клетки пересекаются линии. Клетки не закрашиваются.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class AsiaBoard extends GameBoard {
	final Color colorYellow = new Color(null, 255, 255,0);

	public AsiaBoard(Composite parent) {
		super(parent, SWT.NONE);
	}

	@Override
	public void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h) {
		gc.setBackground( colorYellow );
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
