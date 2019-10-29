package breakthrough.moves;

import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class SimpleMove implements ITransferMove  {
	protected Piece piece;

	protected Square source;
	protected Square target;

	public SimpleMove(Square[] squares) {
		source = squares[0];
		target = squares[1];
		
		piece = source.getPiece();
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
	public Square getSource() {
		return source;
	}

	@Override
	public Square getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}
}
