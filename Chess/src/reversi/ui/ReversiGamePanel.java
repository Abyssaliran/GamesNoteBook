package reversi.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GreenBoard;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import reversi.Reversi;
import reversi.pieces.Stone;
import reversi.ui.images.ReversiImages;

/**
 * 
 * Доска для игры в <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
 * Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiGamePanel extends Canvas {
	private static final Color GREEN = new Color(null, 0, 192, 0);

	public ReversiGamePanel(Composite composite, int nHoles) {
		super(composite, SWT.NONE);
		setBackground(GREEN);

		setLayout(new GridLayout(1, false));

		ReversiBoardPanel boardPanel = new ReversiBoardPanel(this, nHoles);
		
		GridData boardData = new GridData(SWT.FILL, SWT.FILL, true, true);
		boardPanel.setLayoutData(boardData);

		new ScorePanel(this, boardPanel.board);
	}

	/**
	 * Доска для игры в <a href=
	 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
	 * Реверси</a>
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	public class ReversiBoardPanel extends GreenBoard {
		/**
		 * Создать доску для игры в реверси.
		 * 
		 * @param composite
		 *            - составной элемент содержащий доску.
		 * @param nHoles
		 *            - количество случайно расположенных отверстий в доске.
		 */
		public ReversiBoardPanel(Composite composite, int nHoles) {
			super(composite, Reversi.getInitBoard(nHoles));

			// Слушатель мыши для постановки новой фигуры на доску.
			listener = new PutPieceListener(this);

			// Слушатель мыши для выдачи подсказки - можно ли ставить фигуру
			// клетку на доски.
			mouseMoveListener = new PutPiecePromptListener(this);
		}

		@Override
		public Piece getPiece(Square square, PieceColor color) {
			return new Stone(square, color);
		}

		@Override
		public Image getPieceImage(Piece piece, PieceColor color) {
			return color == PieceColor.WHITE 
					? ReversiImages.imageStoneWhite 
					: ReversiImages.imageStoneBlack;
		}
	}
}
