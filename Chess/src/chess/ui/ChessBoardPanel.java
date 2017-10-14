/**
 * 
 */
package chess.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chess.Chess;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import chess.ui.images.ChessImages;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;

/**
 * Панель для отрисовки шахматных фигур на доске.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChessBoardPanel extends EuropeBoard {
	private static Map<Class<? extends Piece>, Image> whites;
	private static Map<Class<? extends Piece>, Image> blacks;
	
	private static Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		pieceImages = new HashMap<>();

		// Инициализируем карту изображений белых фигур.
		//
		whites = new HashMap<>();
		whites.put(Pawn.class,   ChessImages.imagePawnWhite);
		whites.put(Rook.class,   ChessImages.imageRookWhite);
		whites.put(Knight.class, ChessImages.imageKnightWhite);
		whites.put(Bishop.class, ChessImages.imageBishopWhite);
		whites.put(Queen.class,  ChessImages.imageQueenWhite);
		whites.put(King.class,   ChessImages.imageKingWhite);
		
		pieceImages.put(PieceColor.WHITE, whites);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks = new HashMap<>();
		blacks.put(Pawn.class,   ChessImages.imagePawnBlack);
		blacks.put(Rook.class,   ChessImages.imageRookBlack);
		blacks.put(Knight.class, ChessImages.imageKnightBlack);
		blacks.put(Bishop.class, ChessImages.imageBishopBlack);
		blacks.put(Queen.class,  ChessImages.imageQueenBlack);
		blacks.put(King.class,   ChessImages.imageKingBlack);
		
		pieceImages.put(PieceColor.BLACK, blacks);
	}

	public ChessBoardPanel(Composite composite) {
		super(composite, Chess.getInitBoard());
	}

	public Image getPieceImage(Piece piece) {
		return pieceImages
				.get( piece.getColor() )
				.get( piece.getClass() );
	}
}
