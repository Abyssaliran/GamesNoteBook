/**
 * 
 */
package chinachess.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chinachess.ChinaChess;
import chinachess.pieces.Bishop;
import chinachess.pieces.Guardian;
import chinachess.pieces.Gun;
import chinachess.pieces.King;
import chinachess.pieces.Knight;
import chinachess.pieces.Pawn;
import chinachess.pieces.Rook;
import chinachess.ui.images.ChinaChessImages;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.AsiaBoardWithCastle;
import game.ui.GamePanel;
import game.ui.listeners.MovePieceListener;

/**
 * Панель для игры в китайские шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChinaChessGamePanel extends GamePanel {

	public ChinaChessGamePanel(Composite parent) {
		super(parent);
		
		insertSquares( new ChinaChessBoardPanel(this) );
	}
}

/**
 * Панель для доски китайских шахмат.
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 *
 */
class ChinaChessBoardPanel extends AsiaBoardWithCastle {
	private static Map<Class<? extends Piece>, Image> whites;
	private static Map<Class<? extends Piece>, Image> blacks;
	
	private static Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		whites = new HashMap<>();
		blacks = new HashMap<>();

		pieceImages = new HashMap<>();
		pieceImages.put(PieceColor.WHITE, whites);
		pieceImages.put(PieceColor.BLACK, blacks);

		// Инициализируем карту изображений белых фигур.
		//
		whites.put(Pawn.class,     ChinaChessImages.imagePawnWhite);
		whites.put(Rook.class,     ChinaChessImages.imageRookWhite);
		whites.put(Knight.class,   ChinaChessImages.imageKnightWhite);
		whites.put(Bishop.class,   ChinaChessImages.imageBishopWhite);
		whites.put(Gun.class,  	   ChinaChessImages.imageGunWhite);
		whites.put(King.class,     ChinaChessImages.imageKingWhite);
		whites.put(Guardian.class, ChinaChessImages.imageGuardWhite);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Pawn.class,     ChinaChessImages.imagePawnBlack);
		blacks.put(Rook.class,     ChinaChessImages.imageRookBlack);
		blacks.put(Knight.class,   ChinaChessImages.imageKnightBlack);
		blacks.put(Bishop.class,   ChinaChessImages.imageBishopBlack);
		blacks.put(Gun.class,      ChinaChessImages.imageGunBlack);
		blacks.put(King.class,     ChinaChessImages.imageKingBlack);
		blacks.put(Guardian.class, ChinaChessImages.imageGuardBlack);
	}
		
	public ChinaChessBoardPanel(Composite composite) {
		super(composite, ChinaChess.getInitBoard());
		
		listener = new MovePieceListener(this);
		
		setPromptColor( new Color(null, 0, 100, 0));
	}
	
	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		int dv = squareWidth  / 2;
		int dh = squareHeight / 2;
		
		int x = v * squareWidth  + dv;
		int y = h * squareHeight + dh;

		boolean isBlackMargin = (h == 4);
		boolean isWhiteMargin = (h == board.nH-5);
		
		if (isBlackMargin || isWhiteMargin) {
			// Рисуем "реку" между территориями противников.
			if (v !=          0) gc.drawLine(x, y, x - dv, y);
			if (v != board.nV-1) gc.drawLine(x, y, x + dv, y);
	
			if (!isWhiteMargin) gc.drawLine(x, y, x, y - dh);
			if (!isBlackMargin) gc.drawLine(x, y, x, y + dh);
		}
		else // Обычные клетки зиатской доски с двумя крепостями.
			super.drawSquare(gc, v, h, squareWidth, squareHeight);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return pieceImages
				.get(color)
				.get(piece.getClass());
	}
}
