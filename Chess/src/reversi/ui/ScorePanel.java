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
	private static final Color WHITE = new Color(null, 255, 255, 255);
	private static final Color GRAY  = new Color(null, 192, 192, 192);

	private Label whiteScore;
	private Label blackScore;

	private int wScore = 0;
	private int bScore = 0;
	
	private Board board;

	public ScorePanel(Composite parent, Board board) {
		super(parent, SWT.NONE);
		
		this.board = board;

		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		layout.spacing = 10;
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		setLayout(layout);

		whiteScore = new Label(this, SWT.BORDER_SOLID);
		whiteScore.setBackground(WHITE);
		whiteScore.setText(" White: " + wScore);

		blackScore = new Label(this, SWT.BORDER_SOLID);
		blackScore.setBackground(GRAY);
		blackScore.setText(" Black: " + bScore);
		
		board.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		wScore = getPieceCount(board, PieceColor.WHITE);
		bScore = getPieceCount(board, PieceColor.BLACK);
		
		whiteScore.setText(" White: " + wScore);
		blackScore.setText(" Black: " + bScore);
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
