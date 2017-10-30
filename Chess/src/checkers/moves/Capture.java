/**
 * 
 */
package checkers.moves;

import game.core.Piece;
import game.core.Square;

/**
 * Ход шашкой с взятием одной фигуры противника.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove {
	/**
	 * Захваченая фигура.
	 */
	Piece captured;
	private Square capturedSquare;

	/**
	 * Создание хода представляющего взятие одной фигуры.
	 * 
	 * @param isPromotion - было ли превращение в дамку.
	 * @param captured - захваченная фигура. 
	 * @param squares - откуда и куда пошла фигура.
	 */
	public Capture(boolean isPromotion, Piece captured, Square... squares) {
		super(isPromotion, squares);
		
		this.captured = captured;
		capturedSquare = captured.square;
	}
	
	@Override
	public void doMove() {
		captured.remove();
		super.doMove();
	}

	@Override
	public void undoMove() {
		capturedSquare.setPiece(captured);
		super.undoMove();
	}
	
	@Override
	public String toString() {
		return "" + source + "x" + target;
	}
}
