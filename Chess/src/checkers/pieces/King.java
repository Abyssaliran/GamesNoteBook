/**
 * 
 */
package checkers.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Дамка в шашках.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends CheckersPiece {
	public King(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square source = squares[0];
		Square target = squares[1];
		
		// TODO Checkers Сделать проверку правильности хода
		// из клетки source в клетку target.

		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		Move move = null;
		// TODO Checkers Создать ход шашек
		// if (...)
		//    move = new SimpleMove(isPromotion, source, target);
		// else
		//    move = new Capture(isPromotion, captured, source, target);
		// move.doMove();
		return move;
	}
	
	@Override
	public String toString() {
		return "King" + square;
	}
}
