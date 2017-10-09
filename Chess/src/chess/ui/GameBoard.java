package chess.ui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 *
 */
public abstract class GameBoard extends Canvas implements PaintListener  {
	protected int nV = 8;
	protected int nH = 8;

	public GameBoard(Composite parent, int style) {
		super(parent, style);

		addPaintListener(this);
	}

	@Override
	public void paintControl(PaintEvent e) {
		Rectangle clientArea = getClientArea();
	
		int squareWidth = getClientArea().width / nV;
		int squareHeight = getClientArea().height / nH;
	
		GC gc = e.gc;
		for (int v = 0; v < nV; v++) {
			for (int h = 0; h < nH; h++)  
				drawSquare(squareWidth, squareHeight, gc, v, h);
		}
	
		e.gc.drawRectangle(0, 0, clientArea.width - 1, clientArea.height - 1);
	}

	abstract void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h);
}