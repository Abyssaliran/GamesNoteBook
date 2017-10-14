package checkers.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import checkers.Checkers;
import checkers.pieces.Man;
import checkers.pieces.King;
import checkers.ui.images.CheckersImages;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;

/**
 * Доска для игры в шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CheckersBoardPanel extends EuropeBoard {

	public CheckersBoardPanel(Composite composite) {
		super(composite, Checkers.getInitBoard());
	}

	private static Map<Class<? extends Piece>, Image> whites;
	private static Map<Class<? extends Piece>, Image> blacks;
	
	private static Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		pieceImages = new HashMap<>();

		// Инициализируем карту изображений белых фигур.
		//
		whites = new HashMap<>();
		whites.put(Man.class,  CheckersImages.imageManWhite);
		whites.put(King.class, CheckersImages.imageKingWhite);
		
		pieceImages.put(PieceColor.WHITE, whites);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks = new HashMap<>();
		blacks.put(Man.class,  CheckersImages.imageManBlack);
		blacks.put(King.class, CheckersImages.imageKingBlack);
		
		pieceImages.put(PieceColor.BLACK, blacks);
	}

	public Image getPieceImage(Piece piece) {
		return pieceImages
				.get( piece.getColor() )
				.get( piece.getClass() );
	}
}
