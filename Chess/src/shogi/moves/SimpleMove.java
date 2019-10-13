package shogi.moves;

import java.util.HashMap;
import java.util.Map;

import checkers.pieces.King;
import game.core.GameOver;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;

/**
 * 
 */
public class SimpleMove implements ITransferMove {
	/**
	 * Откуда пошла фигура.
	 */
	protected final Square source;

	/**
	 * Куда пошла фигура.
	 */
	protected final Square target;

	/**
	 * Какая фигура пошла.
	 */
	protected Piece piece;

	public SimpleMove(Square... squares) {
		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	public SimpleMove(Piece piece, Square... squares) {
		source = squares[0];
		target = squares[1];

		this.piece = piece;
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}

	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}