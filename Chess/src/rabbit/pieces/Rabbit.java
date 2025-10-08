package rabbit.pieces;

import static java.lang.Math.abs;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import rabbit.moves.SimpleMove;

public class Rabbit extends Piece {
	public Rabbit(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];

		if (!target.isEmpty())
			return false;

		int dh = abs(target.h - square.h);
		int dv = abs(target.v - square.v);

		return dh == 1 && dv == 1;
	}

	@Override
	public Move makeMove(Square... squares) {
		return new SimpleMove(squares);
	}

	@Override
	public String toString() {
		return "R";
	}
}
