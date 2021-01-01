package fisher.moves;

import chess.pieces.Rook;
import game.core.Board;
import game.core.Square;


public class Castling extends chess.moves.Castling {

	private Square rookSource;
	private Square rookTarget;

	public Castling(Square[] squares) {
		super(squares);

		Board board = source.getBoard();
		if (source.v < target.v) {
			//Короткая рокировка
			for (int i = source.v + 1; i < 8; i++) {
				if (board.getSquare(i, source.h).getPiece() instanceof Rook) {
					rookSource = board.getSquare(i, source.h);
					rookTarget = board.getSquare(5, source.h);
					break;
				}
			}
		} else  {
			//Длинная рокировка
			for (int i = source.v - 1; i >= 0; i--) {
				if (board.getSquare(i, source.h).getPiece() instanceof Rook) {
					rookSource = board.getSquare(i, source.h);
					rookTarget = board.getSquare(3, source.h);
					break;
				}
			}
		}
	}

	/*
	 * Переставить короля и ладью.
	 * Если ладья должна попасть на место короля, то сначала нужно передвинуть короля, а затем ладью.
	 */
	@Override
	public void doMove() {
		if (rookTarget == source) {
			super.doMove();
			rookSource.movePieceTo(rookTarget);
		} else {
			rookSource.movePieceTo(rookTarget);
			super.doMove();
		}
	}

	/*
	 * Вернуть короля и ладью в исходной состояние.
	 * Если ладья переходила на место короля, то для отмены нужно сначала передвинуть ладью, а затем короля.
	 */
	@Override
	public void undoMove() {
		if (rookTarget == source) {
			rookTarget.movePieceTo(rookSource);
			super.undoMove();
		} else {
			super.undoMove();
			rookTarget.movePieceTo(rookSource);
		}
	}

	@Override
	public String toString() {
		return source.v < target.v ? "O-O" : "O-O-O";
	}
}
