package threem.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;

public class Capture extends SimpleMove {
	private Piece captured;

	public Capture(Square[] squares) {
		super(squares);

		captured = target.getPiece();
	}

	@Override
	public void doMove() throws GameOver {
		captured.remove();
		super.doMove();
	}

	@Override
	public void undoMove() {
		super.undoMove();
		target.setPiece(captured);
	}

	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}
}
