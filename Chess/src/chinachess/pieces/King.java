package chinachess.pieces;

import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

public class King extends ChinaChessPiece{

	public King(Square square, PieceColor color) {
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
		return new SimpleMove(squares);
	}
}
