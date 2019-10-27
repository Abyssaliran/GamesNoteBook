package kalah;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 *  Кучка шариков
 */
public class Heap extends Piece {
	public int nBalls = 0;

	public Heap(Square square, int n) {
		super(square, PieceColor.WHITE);
		nBalls = n;
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		if ((square.v == 0) || (square.v == 7))
			return false; // Из калаха фигуры не ходят
		
		return nBalls != 0; // Из пустой клетки фигуры не ходят
	}

	@Override
	public Move makeMove(Square... squares) {
		// TODO Auto-generated method stub
		return null;
	}
}
