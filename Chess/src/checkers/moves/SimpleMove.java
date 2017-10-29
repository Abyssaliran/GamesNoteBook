/**
 * 
 */
package checkers.moves;

import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Простой ход шашкой вперед без взятия фигуры противника.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove implements Move {
	/**
	 * Откуда пошла фигура.
	 */
	protected Square source;
	
	/**
	 * Куда пошла фигура.
	 */
	protected Square target;
	
	/**
	 * Бало ли превращение шащки в дамку?
	 */
	protected boolean isPromotion;

	/**
	 * Какая фигура пошла.
	 */
	private Piece piece;
	
	public SimpleMove(boolean isPromotion, Square ... squares) {
		this.isPromotion = isPromotion;
		
		source = squares[0];
		target = squares[1];
		
		piece = source.getPiece();
	}
	
	@Override
	public void doMove() {
		// TODO Auto-generated method stub
		
		if (isPromotion)
			putKing();
	}

	@Override
	public void undoMove() {
		if (isPromotion)
			removeKing();

		piece.moveTo(target);
	}

	private void putKing() {
		// TODO Auto-generated method stub
	}

	private void removeKing() {
		// TODO Auto-generated method stub
	}
}
