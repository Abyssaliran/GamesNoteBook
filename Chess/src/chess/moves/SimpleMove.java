package chess.moves;

import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Простой ход европейских шахмат - перемещение фигуры на пустую клетку.
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
	public void doMove() {
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}
}
