package points.moves;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import points.pieces.Stone;

public class PointMove implements Move {
	private Stone piece;
	private Square target;

	public PointMove(Stone stone, Square[] squares) {
		piece = stone;
		target = squares[0];
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() throws GameOver {
		target.setPiece(piece);
		
		if (isGameOver())
			throw new GameOver( GameResult.win(piece) );
	}

	@Override
	public void undoMove() {
		target.removePiece();
	}

	@Override
	public String toString() {
		return "" + target;
	}

	private boolean isGameOver() {
		// TODO Алексей Голосов. Проверить завершение игры.
		return false;
	}
}
