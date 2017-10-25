/**
 * 
 */
package chinachess.pieces;

import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 *
 */
public class Bishop extends ChinaChessPiece {

	public Bishop(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		return new SimpleMove(squares);
	}
}
