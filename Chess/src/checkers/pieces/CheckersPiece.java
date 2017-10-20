package checkers.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для всех фигур шашек.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class CheckersPiece extends Piece {

	public CheckersPiece(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// В шашках нельзя ходить на поле занятое фигурой любого цвета.
		Square target = squares[0];
		
		return !target.isEmpty();
	}
}