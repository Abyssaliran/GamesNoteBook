package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

/**
 * Европейская доска с двухцветными клетками.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class EuropeBoard extends GameBoard {
	public EuropeBoard(Composite composite) {
		super(composite, SWT.NONE);
	}

	/* (non-Javadoc)
	 * @see game.ui.GameBoard#drawSquare(int, int, org.eclipse.swt.graphics.GC, int, int)
	 */
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
