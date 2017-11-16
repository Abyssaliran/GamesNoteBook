package reversi.ui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import game.core.Board;
import game.core.PieceColor;
import game.core.Square;

/**
 * Панель для демонстрации количества фигур у белых и черных.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ScorePanel extends Canvas implements Observer {
	private static final int MIN_W = 300;
	
	private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);
	private static final Color COLOR_BACK  = new Color(null, 192, 192, 192);
	private static final Color COLOR_TEXT  = new Color(null,  0,   0,    0);

	private Label whiteScore;
	private Label blackScore;

	private int wScore = 0;
	private int bScore = 0;
	
	private Board board;

	public ScorePanel(Composite parent, Board board) {
		super(parent, SWT.BORDER);
		setBackground(COLOR_BACK);
		
		this.board = board;

		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.spacing = 10;
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		setLayout(layout);

		whiteScore = new Label(this, SWT.BORDER_SOLID);
		whiteScore.setBackground(COLOR_WHITE);
		whiteScore.setForeground(COLOR_TEXT);
		whiteScore.computeSize(MIN_W, SWT.DEFAULT);

		blackScore = new Label(this, SWT.BORDER_SOLID);
		blackScore.setBackground(COLOR_WHITE);
		blackScore.setForeground(COLOR_TEXT);
		blackScore.computeSize(MIN_W, SWT.DEFAULT);
		
		wScore = getPieceCount(board, PieceColor.WHITE);
		bScore = getPieceCount(board, PieceColor.BLACK);
		
		whiteScore.setText("Белые:\t"  + wScore);
		blackScore.setText("Черные:\t" + bScore);

		board.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		wScore = getPieceCount(board, PieceColor.WHITE);
		bScore = getPieceCount(board, PieceColor.BLACK);
		
		whiteScore.setText("Белые:\t"  + wScore);
		blackScore.setText("Черные:\t" + bScore);
	}

	/**
	 * Подсчет на доске количества фигур заданного цвета.
	 * 
	 * @param board - доска для подсчета фигур.
	 * @param color - цвет подсчитываемых фигур.
	 * @return  количество фигур 
	 */
	private int getPieceCount(Board board, PieceColor color) {
		int count = 0;

		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++) {
				Square square = board.getSquare(v, h);
				if (!square.isEmpty())
					if (square.getPiece().getColor() == color)
						count++;
			}
		return count;
	}
}
