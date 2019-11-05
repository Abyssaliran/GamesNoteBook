package points.pieces;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

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
		// TODO Илья Гневашев. Проверить завершение игры (5 фишек в ряд).
		return false;
	}
}
