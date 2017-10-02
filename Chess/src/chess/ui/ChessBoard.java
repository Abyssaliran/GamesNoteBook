package chess.ui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Romanov_V_Y Шахматная доска.
 */
public class ChessBoard extends Canvas implements PaintListener {
	private int nV = 8;
	private int nH = 8;

	public ChessBoard(Composite composite, int style) {
		super(composite, style);

		addPaintListener(this);
	}

	@Override
	public void paintControl(PaintEvent e) {
		Rectangle clientArea = getClientArea();

		int squareWidth = getClientArea().width / nV;
		int squareHeight = getClientArea().height / nH;

		GC gc = e.gc;
		for (int v = 0; v < nV; v++) {
			for (int h = 0; h < nH; h++) {
				drawSquare(squareWidth, squareHeight, gc, v, h);
			}
		}

		e.gc.drawRectangle(0, 0, clientArea.width - 1, clientArea.height - 1);
	}

	private void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h) {
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
