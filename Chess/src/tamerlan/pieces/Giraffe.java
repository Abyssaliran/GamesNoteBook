package tamerlan.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

public class Giraffe extends TamerlanPiece {

	public Giraffe(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public Move makeMove(Square... squares) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		return true;
	}
}
