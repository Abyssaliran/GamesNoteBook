/**
 * 
 */
package checkers.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Дамка в шашках.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends CheckersPiece {
	private Piece pastMan;
	
	public King(Piece pastMan, Square square, PieceColor color) {
		super(square, color);
		this.pastMan = pastMan;
	}
	
	public Piece getMan() {
		return pastMan;
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square source = square;
		Square target = squares[0];
		
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
