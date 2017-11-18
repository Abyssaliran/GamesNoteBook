/**
 * 
 */
package checkers.moves;

import java.util.Arrays;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;

/**
 * Ход шашкой с взятием одной фигуры противника.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove implements ICaptureMove {
	/**
	 * Захваченая фигура.
	 */
	Piece captured;
	
	/**
	 * Клетка где стоит захваченная фигура. 
	 */
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
	public void doMove() throws GameOver {
		super.doMove();
		captured.remove();
		
		Board board = source.getBoard();
		PieceColor enemyColor = captured.getColor();
		List<Piece> enemies = board.getPieces(enemyColor);
		
		if (enemies.isEmpty())
			throw new GameOver( GameResult.lost(captured) );
	}

	@Override
	public void undoMove() {
		capturedSquare.setPiece(captured);
		super.undoMove();
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}

	@Override
	public List<Square> getCaptured() {
		return Arrays.asList(capturedSquare);
	}
}
