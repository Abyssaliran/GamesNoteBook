package backgammon.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import backgammon.Backgammon;
import backgammon.BackgammonBoard;
import backgammon.pieces.Stone;
import backgammon.ui.images.BackgammonImages;
import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;
import game.ui.GamePanel;
import game.ui.ScorePanel;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import game.ui.listeners.MovePiecePromptListener;

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class BackgammonGamePanel extends GamePanel {
	public BackgammonGamePanel(Composite composite) {
		super(composite, new Backgammon());

		BackgammonBoardPanel gameBoard = new BackgammonBoardPanel(this, game);
		insertSquares(gameBoard);

		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;

		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);

		CubesPanel cp = new CubesPanel(control, (Backgammon) game);
		cp.setLayoutData(data);
	}
}

/**
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class BackgammonBoardPanel extends GameBoard implements IPieceProvider {
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
		return color == PieceColor.WHITE 
				? BackgammonImages.imageStoneWhite 
				: BackgammonImages.imageStoneBlack;
	}

	private final Color BLACK_COLOR = new Color(null, 0, 0, 0);

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		Rectangle bounds = GameImages.woodLight.getBounds();
		
		gc.drawImage(GameImages.woodLight, 
				0, 0, bounds .width, bounds.height, 
				area.x, area.y, area.width, area.height);

		gc.setForeground(BLACK_COLOR);
		gc.drawRectangle(area.x, area.y, area.width, area.height);
	}

	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		boolean isOdd = (v % 2 == 0);
		
		boolean isBar           = (v == 6);
		boolean hasLeftBorder   = (v == 0) || isBar || (v == 7);
		boolean hasRightBorder  = (v == board.nV-1);
		boolean hasTopBorder    = (h == 0);
		boolean hasBottomBorder = (h == board.nH-1);
		
		boolean isTopSide    = (h <= 4);
		boolean isMiddleSide = (5 <= h) & (h <= 6);
		boolean isBottomSide = (7 <= h);
		
		boolean topDark    = isTopSide && isOdd;
		boolean bottomDark = isBottomSide && !isOdd;
		boolean isDark     = !isMiddleSide && (topDark || bottomDark);
		
		int sw = squareWidth;
		int sh = squareHeight;
		
		int x = v * squareWidth;
		int y = h * squareHeight;
		
		Image wood = isDark ? GameImages.woodDark : GameImages.woodMedium;
		Rectangle bounds = wood.getBounds();
		
		if (!isBar)
			gc.drawImage(wood, 
		             0, 0, bounds.width, bounds.height, 
			         x, y, sw, sh);

		if (hasLeftBorder)  gc.drawLine(x, y, x, y + sh);
		if (hasRightBorder) gc.drawLine(x + sw, y, x + sw, y + sh);

		if (hasTopBorder)    gc.drawLine(x, y, x + sw, y);
		if (hasBottomBorder) gc.drawLine(x, y + sh, x + sw, y + sh);
	}
	
	@Override
	public void mouseUp(MouseEvent e) {
		super.mouseUp(e);
		
		// Человек сделал ход.
		BackgammonBoard b = (BackgammonBoard)board;
		b.dropCubes();
		b.setBoardChanged();
	}
}