package checkers.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import checkers.Checkers;
import checkers.pieces.King;
import checkers.pieces.Man;
import checkers.ui.images.CheckersImages;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import game.ui.listeners.MovePieceListener;

/**
 * Доска для игры в шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CheckersBoardPanel extends EuropeBoard {
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
		whites.put(Man.class,  CheckersImages.imageManWhite);
		whites.put(King.class, CheckersImages.imageKingWhite);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Man.class,  CheckersImages.imageManBlack);
		blacks.put(King.class, CheckersImages.imageKingBlack);
	}

	public CheckersBoardPanel(Composite composite) {
		super(composite, Checkers.getInitBoard());
		
		listener = new MovePieceListener(this);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return pieceImages
				.get(color)
				.get( piece.getClass() );
	}
}
