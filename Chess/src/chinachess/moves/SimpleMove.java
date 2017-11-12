package chinachess.moves;

import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Простой ход китайских шахмат - перемещение фигуры на пустую клетку.
 * 
 * Игра <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove implements Move {
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

	public SimpleMove(Square[] squares) {
		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}
}
