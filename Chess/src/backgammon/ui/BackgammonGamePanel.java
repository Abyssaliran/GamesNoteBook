package backgammon.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import backgammon.Backgammon;
import backgammon.ui.images.BackgammonImages;
import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GamePanel;
import game.ui.GreenBoard;
import game.ui.ScorePanel;
import game.ui.listeners.MovePieceListener;
import game.ui.listeners.MovePiecePromptListener;
import backgammon.pieces.Stone;

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class BackgammonGamePanel extends GamePanel {
	private static final Color GREEN = new Color(null, 0, 192, 0);

	public BackgammonGamePanel(Composite composite) {
		super(composite, new Backgammon());
		setBackground(GREEN);

		BackgammonBoardPanel gameBoard = new BackgammonBoardPanel(this, game);
		insertSquares(gameBoard);

		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;

		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);
	}

	/**
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	public class BackgammonBoardPanel extends GreenBoard implements IPieceProvider {
		/**
		 * Создать доску для игры в нарды.
		 * 
		 * @param composite - составной элемент содержащий доску.
		 * @param game      - игра
		 */
		public BackgammonBoardPanel(Composite composite, Game game) {
			super(composite, game.board);

			// Слушатель мыши для постановки новой фигуры на доску.
			listener = new MovePieceListener(this);

			// Слушатель мыши для отрисовки подсказки на доске -
			// можно ли ставить фигуру на клетку на доски.
			mouseMoveListener = new MovePiecePromptListener(this);
		}

		@Override
		public Piece getPiece(Square square, PieceColor color) {
			return new Stone(square, color);
		}

		@Override
		public Image getPieceImage(Piece piece, PieceColor color) {
			return color == PieceColor.WHITE ? BackgammonImages.imageStoneWhite : BackgammonImages.imageStoneBlack;
		}
	}
}
