package go.moves;

import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Ход без захвата фигуры противника 
 * для <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove implements Move {
	/**
	 * Куда поставят фигуру..
	 */
	private Square target;
	
	/**
	 * Какую фигуру поставят.
	 */
	private Piece piece;

	/**
	 * Простой ход без взятия фигур противника.
	 * @param piece - какая фигура ставится.
	 * @param squares - клетки.
	 * Клетка squares[0] - куда ставится фигура.
	 */
	public SimpleMove(Piece piece, Square... squares) {
		target = squares[0];
		this.piece = piece;
	}

	@Override
	public void doMove() {
		target.setPiece(piece);
	}

	@Override
	public void undoMove() {
		piece.remove();
	}
}
