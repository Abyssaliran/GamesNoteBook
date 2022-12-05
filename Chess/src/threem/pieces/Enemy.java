package threem.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import threem.moves.SimpleMove;

public class Enemy extends Piece {
	public Enemy(Square s, PieceColor color) {
		super(s, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
