package tamerlan.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;

import tamerlan.TamerlanChess;
import tamerlan.pieces.Bishop;
import tamerlan.pieces.King;
import tamerlan.pieces.Knight;
import tamerlan.pieces.Pawn;
import tamerlan.pieces.Queen;
import tamerlan.pieces.Rook;
import tamerlan.ui.images.TamerlanChessImages;

/**
 * Панель для игры в 
 * <a href="https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">Шахматы Тамерлана</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChessBoardPanel extends EuropeBoard {
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
		whites.put(Pawn.class,   TamerlanChessImages.imagePawnWhite);
		whites.put(Rook.class,   TamerlanChessImages.imageRookWhite);
		whites.put(Knight.class, TamerlanChessImages.imageKnightWhite);
		whites.put(Bishop.class, TamerlanChessImages.imageBishopWhite);
		whites.put(Queen.class,  TamerlanChessImages.imageQueenWhite);
		whites.put(King.class,   TamerlanChessImages.imageKingWhite);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Pawn.class,   TamerlanChessImages.imagePawnBlack);
		blacks.put(Rook.class,   TamerlanChessImages.imageRookBlack);
		blacks.put(Knight.class, TamerlanChessImages.imageKnightBlack);
		blacks.put(Bishop.class, TamerlanChessImages.imageBishopBlack);
		blacks.put(Queen.class,  TamerlanChessImages.imageQueenBlack);
		blacks.put(King.class,   TamerlanChessImages.imageKingBlack);
		
		// TODO Добавить в карту изображений фигуры Vizir и WarMachine.		
	}

	public TamerlanChessBoardPanel(Composite composite) {
		super(composite, TamerlanChess.getInitBoard());
	}

	public Image getPieceImage(Piece piece) {
		return pieceImages
				.get( piece.getColor() )
				.get( piece.getClass() );
	}

}
