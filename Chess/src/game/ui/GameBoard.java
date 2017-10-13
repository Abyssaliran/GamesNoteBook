package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import game.core.Board;

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 *
 */
public abstract class GameBoard extends Canvas implements PaintListener  {
    protected Board board;

	public GameBoard(Composite parent, Board board) {
		super(parent, SWT.NONE);
		
		this.board = board;

		addPaintListener(this);
	}

	@Override
	public void paintControl(PaintEvent e) {
		Rectangle clientArea = getClientArea();
	
		int squareWidth  = getClientArea().width  / board.nV;
		int squareHeight = getClientArea().height / board.nH;
	
		GC gc = e.gc;
		for (int v = 0; v < board.nV; v++) {
			for (int h = 0; h < board.nH; h++)  
				drawSquare(gc, v, h, squareWidth, squareHeight);
		}
	
		e.gc.drawRectangle(0, 0, clientArea.width - 1, clientArea.height - 1);
	}

	/**
	 * @param gc графический контекст в котором рисуется клетка доски.
	 * @param v вертикаль клетки
	 * @param h горизонталь клетки
	 * @param squareWidth ширина клетки
	 * @param squareHeight высота клетки
	 */
	abstract 
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight);
}