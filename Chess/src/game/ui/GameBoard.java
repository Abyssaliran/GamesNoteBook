package game.ui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import game.core.Board;
import game.core.Piece;
import game.core.Square;
import game.ui.listeners.IGameListner;

/**
 * Базовый класс для отрисовки досок всех настольных игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class GameBoard extends Canvas 
	implements PaintListener, MouseListener, Observer  
{
    public Board board;

	public GameBoard(Composite parent, Board board) {
		super(parent, SWT.NONE | SWT.DOUBLE_BUFFERED);
		
		this.board = board;

		addPaintListener(this);
		
		addMouseListener(this);
		board.addObserver(this);
		
		board.setBoardChanged();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		update();
		redraw();
	}

	@Override
	public void paintControl(PaintEvent e) {
		GC gc = e.gc;

		Rectangle clientArea = getClientArea();
	
		drawBackground(gc, clientArea);

		int squareWidth  = getSquareWidth();
		int squareHeight = getSquareHeight();
	
		for (int v = 0; v < board.nV; v++) {
			for (int h = 0; h < board.nH; h++)  {
				drawSquare(gc, v, h, squareWidth, squareHeight);
				
				drawPiece (gc, v, h, squareWidth, squareHeight);
			}
		}
	}

	/**
	 * @return высота клетки.
	 */
	private int getSquareHeight() {
		return getClientArea().height / board.nH;
	}

	/**
	 * @return ширина клетки.
	 */
	private int getSquareWidth() {
		return getClientArea().width  / board.nV;
	}

	/**
	 * Сделать заданное изображение изображением курсора.
	 * 
	 * @param image
	 *            - новое изображение курсора.
	 */
	public void imageToCursor(Image image) {
		int sw = getSquareWidth();
		int sh = getSquareHeight();
		
		int pw = sw - sw/8; // Ширина фигуры в клетке.
		int ph = sh - sh/8; // Высота фигуры в клетке.
		
		ImageData imageDate = image.getImageData().scaledTo(pw,	ph);
	
		Display display = Display.getCurrent();
		
		Cursor cursorPiece = new Cursor(display, imageDate, sw/2, sh/2);
		setCursor(cursorPiece);
	}

	/**
	 * Выдать клетку над которой было нажатие мыши.
	 * 
	 * @param e - событие о нажатии мыши.
	 * @return - клетка под мышкой
	 */
	private Square getSquare(MouseEvent e) {
		int squareW = getSquareWidth();
		int squareH = getSquareHeight();

		int selectedV = e.x / squareW;
		int selectedH = e.y / squareH;
		
		return board.getSquare(selectedV, selectedH);
	}
	
	/**
	 * Отрисовать фигуру стоящую на клетке доски.
	 * 
	 * @param gc - графический контекст для рисования клетки
	 * @param v - вертикаль клетки
	 * @param h - горизонталь клетки
	 * @param squareWidth - ширина клетки
	 * @param squareHeight - высота клетки
	 */
	private void drawPiece(GC gc, int v, int h, int squareWidth, int squareHeight) {
		Piece piece = board.getSquare(v, h).getPiece();
		if (piece == null) return;

		int dx = squareWidth  /8;
		int dy = squareHeight /8;
		
		int x = v * squareWidth  + dx;
		int y = h * squareHeight + dy;
		
		Image image = getPieceImage(piece);
		Rectangle bounds = image.getBounds();
		gc.drawImage(image, 
				0, 0, bounds.width, bounds.height, 
				x, y, squareWidth - 2*dx, squareHeight - 2*dy);
	}

	/**
	 * Отрисовка фона для доски.
	 * 
	 * @param gc - графический контекст для рисования фона доски.
	 * @param area - область для рисования фона доски.
	 */
	abstract
	protected void drawBackground(GC gc, Rectangle area);

	/**
	 * Выдать изображение для заданной фигуры клетке доски.
	 * 
	 * @param piece - фигура для которой нужно выдать изображение. 
	 * @return - изображение фигуры.
	 */
	abstract 
	public Image getPieceImage(Piece piece);

	/**
	 * Отрисовка клетки доски.
	 * 
	 * @param gc - графический контекст в котором рисуется клетка доски.
	 * @param v - вертикаль клетки.
	 * @param h - горизонталь клетки.
	 * @param squareWidth - ширина клетки.
	 * @param squareHeight - высота клетки.
	 */
	abstract 
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight);

	/**
	 * Слушатель нажатий мыши над клетками доски.
	 */
	protected IGameListner listener = IGameListner.EMPTY;
	
	@Override
	public void mouseDown(MouseEvent e) {
		Square s = getSquare(e);
		
		listener.mouseDown(s, e.button);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		Square s = getSquare(e);
		
		listener.mouseUp(s, e.button);
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {}
}