package halma.moves;

import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Ход для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class HalmaMove implements Move {
	/**
	 * Какая фигура перемещается.
	 */
	private Piece piece;
	
	/**
	 * Откуда перемещается.
	 */
	private Square source;
	
	/**
	 * Куда перемещается.
	 */
	private Square target;

	public HalmaMove(Square... squares) {
		source = squares[0];
		target = squares[1];
		
		piece = source.getPiece();
	}

	@Override
	public void doMove() {
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}
}
