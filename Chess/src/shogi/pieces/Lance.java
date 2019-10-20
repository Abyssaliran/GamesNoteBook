package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class Lance extends ShogiPiece {
	public Lance(Square square, PieceColor color) {
		super(square, color);
	}
	
	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square target = squares[0];
		Square source = square;
		
		int dh = isWhite() ? source.h - target.h : target.h - source.h;
		
		if (square.isEmptyVertical(target) && dh > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "L";
	}
}
