package reversi.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GamePanel;
import game.ui.GreenBoard;
import game.ui.ScorePanel;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import reversi.Reversi;
import reversi.pieces.Stone;
import reversi.ui.images.ReversiImages;

/**
 * 
 * Доска для игры в <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
 * Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiGamePanel extends GamePanel {
	private static final Color GREEN = new Color(null, 0, 192, 0);

	public ReversiGamePanel(Composite composite, int nHoles) {
		super(composite, new Reversi(nHoles));
		setBackground(GREEN);
		
		ReversiBoardPanel gameBoard = new ReversiBoardPanel(this, game, nHoles);
		insertSquares( gameBoard );

		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;
		
		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);
	}

	/**
	 * Доска для игры в <a href=
	 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
	 * Реверси</a>
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	public static class ReversiBoardPanel extends GreenBoard implements IPieceProvider {
		/**
		 * Создать доску для игры в реверси.
		 * 
		 * @param composite
		 *            - составной элемент содержащий доску.
		 * @param game 
		 * @param nHoles
		 *            - количество случайно расположенных отверстий в доске.
		 */
		public ReversiBoardPanel(Composite composite, Game game, int nHoles) {
			super(composite, game.board);

			// Слушатель мыши для постановки новой фигуры на доску.
			listener = new PutPieceListener(this);

			// Слушатель мыши для отрисовки подсказки на доске - 
			// можно ли ставить фигуру на клетку на доски.
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
