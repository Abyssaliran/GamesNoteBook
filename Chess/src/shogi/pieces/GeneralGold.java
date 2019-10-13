package shogi.pieces;

import game.core.PieceColor;
import game.core.Square;

public class GeneralGold extends ShogiPiece {

	public GeneralGold(Square square, PieceColor color) {
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
	public String toString() {
		return "G";
	}
}