/**
 * 
 */
package checkers.moves;

import checkers.pieces.King;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
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

	public SimpleMove(boolean isPromotion, Square... squares) {
		this.isPromotion = isPromotion;

		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	@Override
	public void doMove() {
		piece.moveTo(target);
		
		if (isPromotion)
			putKing(target);
	}

	@Override
	public void undoMove() {
		if (isPromotion)
			removeKing(target);
		
		piece.moveTo(source);
	}

	/**
	 * Заменить на поле s простую шашку на дамку.
	 * 
	 * @param s
	 */
	private void putKing(Square s) {
		// TODO Auto-generated method stub
		if (isPromotion)
			piece = s.getPiece();
			Piece tempMan = piece;
			PieceColor kingColor = piece.getColor();
			s.removePiece();
			piece = new King(tempMan, s, kingColor);
	}

	/**
	 * Заменить на поле s  на дамку простую шашку.
	 * 
	 * @param s
	 */
	private void removeKing(Square s) {
		// TODO Auto-generated method stub
		King tempKing = (King) s.getPiece();
		s.removePiece();
		piece = tempKing.getMan();
		s.setPiece(piece);
		//TODO: fix rare bug with lost Man!
		//FIXED!
	}
	
	@Override
	public String toString() {
		return "" + piece + "-" + target;
	}
}
