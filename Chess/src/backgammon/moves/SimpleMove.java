package backgammon.moves;

import backgammon.pieces.Stone;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class SimpleMove implements ITransferMove {
	private Stone piece;
	private Square source;
	private Square target;

	public SimpleMove(Stone piece, Square source, Square target) {
		this.piece = piece;
		this.source = source;
		this.target = target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() {
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
	public Square getSource() {
		return source;
	}

	@Override
	public Square getTarget() {
		return target;
	}
}
