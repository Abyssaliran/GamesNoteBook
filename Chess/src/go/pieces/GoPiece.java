package go.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура для игры в <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GoPiece extends Piece {
	public GoPiece(Square square, PieceColor color) {
		super(square, color);
	}

	/* (non-Javadoc)
	 * @see game.core.Piece#isCorrectMove(game.core.Square[])
	 */
	@Override
	public boolean isCorrectMove(Square... squares) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see game.core.Piece#makeMove(game.core.Square[])
	 */
	@Override
	public Move makeMove(Square... squares) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Может ли фигура "дышать"?
	 * Есть ли у нее или у соседних своих клеток пустая 
	 * ближайшая клетка по вертикали или коризонтали.
	 * @return
	 */
	public boolean hasDame() {
		return true;
	}
}
