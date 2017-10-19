package vikings.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для фигур игры Викинги.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class VikingsPiece extends Piece {
	public VikingsPiece(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		return true;
	}
}
