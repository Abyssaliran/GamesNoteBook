/**
 * 
 */
package chinachess.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chinachess.pieces.Bishop;
import chinachess.pieces.Guardian;
import chinachess.pieces.King;
import chinachess.pieces.Knight;
import chinachess.pieces.Pawn;
import chinachess.pieces.Gun;
import chinachess.pieces.Rook;
import chinachess.ui.images.ChinaChessImages;
import chinachess.ChinaChess;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.AsiaBoardWithCastle;


public class ChinaChessBoardPanel extends AsiaBoardWithCastle {

	private static Map<Class<? extends Piece>, Image> whites;
	private static Map<Class<? extends Piece>, Image> blacks;
	
	private static Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		pieceImages = new HashMap<>();

		// Инициализируем карту изображений белых фигур.
		//
		whites = new HashMap<>();
		whites.put(Pawn.class,   ChinaChessImages.imagePawnWhite);
		whites.put(Rook.class,   ChinaChessImages.imageRookWhite);
		whites.put(Knight.class, ChinaChessImages.imageKnightWhite);
		whites.put(Bishop.class, ChinaChessImages.imageBishopWhite);
		whites.put(Gun.class,  	 ChinaChessImages.imageGunWhite);
		whites.put(King.class,   ChinaChessImages.imageKingWhite);
		whites.put(Guardian.class, ChinaChessImages.imageGuardWhite);
		
		pieceImages.put(PieceColor.WHITE, whites);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks = new HashMap<>();
		blacks.put(Pawn.class,   ChinaChessImages.imagePawnBlack);
		blacks.put(Rook.class,   ChinaChessImages.imageRookBlack);
		blacks.put(Knight.class, ChinaChessImages.imageKnightBlack);
		blacks.put(Bishop.class, ChinaChessImages.imageBishopBlack);
		blacks.put(Gun.class,    ChinaChessImages.imageGunBlack);
		blacks.put(King.class,   ChinaChessImages.imageKingBlack);
		blacks.put(Guardian.class, ChinaChessImages.imageGuardBlack);
		
		pieceImages.put(PieceColor.BLACK, blacks);
	}
		
	public ChinaChessBoardPanel(Composite composite) {
		super(composite, ChinaChess.getInitBoard());
	}

	/* (non-Javadoc)
	 * @see game.ui.GameBoard#getPieceImage(game.core.Piece)
	 */
	@Override
	public Image getPieceImage(Piece piece) {
		return pieceImages
				.get(piece.getColor())
				.get(piece.getClass());
	}

}
