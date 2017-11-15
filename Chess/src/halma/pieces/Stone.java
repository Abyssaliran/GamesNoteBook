package halma.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

import halma.moves.HalmaMove;

/**
 * Фигура для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Stone extends Piece {

	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square source = square;
		Square target = squares[0];
		
		int dv = Math.abs(target.v - source.v);
		int dh = Math.abs(target.h - source.h);
		
		if (!target.isEmpty())
			return false; // На занятую клетку не ходим.
		
		if (dv > 0 && dh > 0)
			return false; // Ход не по горизонтали или вертикали.
		
		if (dv > 1 || dh > 1)
			return false; // Пока прыжки не делаем.
		
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		return new HalmaMove(squares);
	}
}
