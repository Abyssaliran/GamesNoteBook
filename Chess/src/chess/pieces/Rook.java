package chess.pieces;

import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Класс представляет шахматную ладью.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Rook extends ChessPiece {
	public Rook(Square square, PieceColor color) {
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
