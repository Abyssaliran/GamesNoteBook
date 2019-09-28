package backgammon.pieces;

import backgammon.moves.SimpleMove;
import game.core.ITrackPiece;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

public class Stone extends Piece implements ITrackPiece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		// В нардах нельзя ходить на поле занятое фигурой любого цвета.
		return target.isEmpty();
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		return new SimpleMove(this, square, target);
	}

	@Override
	public boolean hasCorrectMoveFrom(Square square) {
		return true;
	}
	
	@Override
	public String toString() {
		return "";
	}

}
