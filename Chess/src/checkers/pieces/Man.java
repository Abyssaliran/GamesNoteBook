/**
 * 
 */
package checkers.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import halma.moves.HalmaMove;

/**
 * Простая фигура в шашках.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Man extends CheckersPiece {
	public Man(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		return new HalmaMove(squares);
	}
}
