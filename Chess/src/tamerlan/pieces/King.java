package tamerlan.pieces;

import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Класс представляет шахматного короля.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends TamerlanPiece {
	public King(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		// TODO Утаев - проверить правильность хода королем.
		
		return true;
	}
	
	@Override
	public Move makeMove(Square... squares) {
		// TODO Утаев - если это захват фигуры противника,
		// то вернуть ход-захват фигуры new Capture();
		
		return new SimpleMove(squares);
	}
}
