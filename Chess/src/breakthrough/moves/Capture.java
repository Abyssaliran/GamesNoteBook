package breakthrough.moves;

import game.core.Piece;
import game.core.Square;

/**
 * Взятие фигуры.
 * TODO Гаца Павел
 * 
 * Правила:
 * https://www.chessprogramming.org/Breakthrough_(Game)
 */
public class Capture extends SimpleMove {
	private Piece captured;

	public Capture(Square[] squares) {
		super(squares);
		
		captured = target.getPiece();
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}
}
