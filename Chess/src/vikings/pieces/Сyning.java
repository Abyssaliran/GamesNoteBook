package vikings.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import vikings.moves.SimpleMove;

/**
 * Фигура - король Викингов.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Сyning extends VikingsPiece {
	public Сyning(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public Move makeMove(Square... squares) {
		return new SimpleMove(squares);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		return true;
	}
}
