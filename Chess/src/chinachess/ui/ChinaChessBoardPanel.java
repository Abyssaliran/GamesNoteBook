/**
 * 
 */
package chinachess.ui;

import java.util.HashMap;
import java.util.Map;

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
import game.ui.listeners.MovePieceListener;


public class ChinaChessBoardPanel extends AsiaBoardWithCastle {

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
		whites.put(Pawn.class,   ChinaChessImages.imagePawnWhite);
		whites.put(Rook.class,   ChinaChessImages.imageRookWhite);
		whites.put(Knight.class, ChinaChessImages.imageKnightWhite);
		whites.put(Bishop.class, ChinaChessImages.imageBishopWhite);
		whites.put(Gun.class,  	 ChinaChessImages.imageGunWhite);
		whites.put(King.class,   ChinaChessImages.imageKingWhite);
		whites.put(Guardian.class, ChinaChessImages.imageGuardWhite);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Pawn.class,   ChinaChessImages.imagePawnBlack);
		blacks.put(Rook.class,   ChinaChessImages.imageRookBlack);
		blacks.put(Knight.class, ChinaChessImages.imageKnightBlack);
		blacks.put(Bishop.class, ChinaChessImages.imageBishopBlack);
		blacks.put(Gun.class,    ChinaChessImages.imageGunBlack);
		blacks.put(King.class,   ChinaChessImages.imageKingBlack);
		blacks.put(Guardian.class, ChinaChessImages.imageGuardBlack);
	}
		
	public ChinaChessBoardPanel(Composite composite) {
		super(composite, ChinaChess.getInitBoard());
		
		listener = new MovePieceListener(this) {
			@Override
			public Image getPieceImage(Piece piece, PieceColor color) {
				return ChinaChessBoardPanel.this.getPieceImage(piece, color);
			}
		};
	}

	/* (non-Javadoc)
	 * @see game.ui.GameBoard#getPieceImage(game.core.Piece)
	 */
	@Override
	public Image getPieceImage(Piece piece) {
		return getPieceImage(piece,  piece.getColor());
	}

	private Image getPieceImage(Piece piece, PieceColor color) {
		return pieceImages
				.get(color)
				.get(piece.getClass());
	}
}
