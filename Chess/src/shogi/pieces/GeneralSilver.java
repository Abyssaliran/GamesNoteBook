package shogi.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import shogi.moves.SimpleMove;

public class GeneralSilver extends ShogiPiece {

	public GeneralSilver(Square square, PieceColor color) {
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
		Square source = squares[0];
		Square target = squares[1];

		return new SimpleMove(this, source, target);
	}
	
	@Override
	public String toString() {
		return "S";
	}
}
