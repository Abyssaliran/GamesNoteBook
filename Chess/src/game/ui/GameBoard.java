package game.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
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
import game.ui.listeners.IMouseMoveListener;
import game.ui.listeners.PieceMovePromptListener;

/**
 * Базовый класс для отрисовки досок всех настольных игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class GameBoard extends Canvas 
	implements PaintListener, MouseListener, MouseMoveListener, Observer  
{
    private static final Color PROMPT_COLOR = new Color(null, 255, 0, 0);
    
	public Board board;

	public GameBoard(Composite parent, Board board) {
		super(parent, SWT.NONE | SWT.DOUBLE_BUFFERED);
		
		this.board = board;

		addPaintListener(this);
		
		addMouseListener(this);
		addMouseMoveListener(this);
		
		board.addObserver(this);
		
		// !Что бы доска получала фокус добавим слушателя клавиатуры.
		// После этого доска начнет получать события от колеса мыши.
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		// Добавим слушателя колеса мыши.
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent e) {
				if (e.count > 0)
					board.history.toPrevMove();
				else board.history.toNextMove();
				
				board.setBoardChanged();
			}
		});

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
	
		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++)
				drawSquare(gc, v, h, squareWidth, squareHeight);

		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++)
				drawPiece(gc, v, h, squareWidth, squareHeight);
		
		drawSquaresPrompt(gc, squareWidth, squareHeight);
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
		
		if (!board.onBoard(selectedV, selectedH))
			return null;
		
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

	// ------------------------------------------------------
	// ------ Обработка событий нажатия на кнопки мыши ------
	// ------------------------------------------------------

	/**
	 * Слушатель нажатий мыши над клетками доски.
	 */
	protected IGameListner listener = IGameListner.EMPTY;
	
	@Override
	public void mouseDown(MouseEvent e) {
		Square s = getSquare(e);
		
		if (s != null)
			listener.mouseDown(s, e.button);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		Square s = getSquare(e);
		
		if (s != null)
			listener.mouseUp(s, e.button);
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent e) {}
	
	// ------------------------------------------------
	// ------ Обработка событий перемещения мыши ------
	// ------------------------------------------------
	
	/**
	 * Клетки на которые допустим очередной ход фигурой.
	 * Используется для отрисовки на доске подсказки 
	 * всех допустимых ходов для этой фигуры.
	 */
	public List<Square> prompted = new ArrayList<>();
	
	/**
	 * Слушатель события перемещения мыши.
	 */
	protected IMouseMoveListener mouseMoveListener 
					= new PieceMovePromptListener(this, prompted);

	@Override
	public void mouseMove(MouseEvent e) {
		Square s = getSquare(e);
		
		if (s != null)
			mouseMoveListener.mouseMove(s);
	}
	
	/**
	 * Нарисовать подсказку для клеток на которые может 
	 * сделать очередной ход фигура.
	 * 
	 * @param gc - графический контекст для отрисовки подсказки.
	 * @param sw - ширина клетки.
	 * @param sh - высота клетки.
	 */
	void drawSquaresPrompt(GC gc, int sw, int sh) {
		if (prompted.isEmpty())
			return;
		
		gc.setLineWidth(3);
		gc.setForeground(PROMPT_COLOR);
		
		for (Square s : prompted)  
			gc.drawRectangle(s.v * sw, s.h * sh, sw, sh);
	}
}