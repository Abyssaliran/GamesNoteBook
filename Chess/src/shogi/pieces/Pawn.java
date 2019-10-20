package shogi.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import shogi.moves.SimpleMove;

public class Pawn extends ShogiPiece {
	public Pawn(Square square, PieceColor color) {
		super(square, color);
	}
	
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square source = square;
		Square target = squares[0];
		
		int dh = isWhite() ? source.h - target.h : target.h - source.h;
		int dv = Math.abs(source.v-target.v);
		
		if (!(dv == 0 && dh == 1)) {
			return false;
		}
		
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		Square source = squares[0];
		Square target = squares[1];

		return new SimpleMove(this, source, target);
	}
	
	//test push
	
	@Override
	public String toString() {
		return "P";
	}
}
