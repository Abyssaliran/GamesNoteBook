package go.pieces;

import java.util.ArrayList;
import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import go.moves.Capture;
import go.moves.SimpleMove;

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
		// TODO Go Реализовать проверку правильности хода.
		return true;
	}

	/* (non-Javadoc)
	 * @see game.core.Piece#makeMove(game.core.Square[])
	 */
	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[0];
		
		Move move = null;
		// TODO Go Реализовать создание и выполнение хода.
		move = new SimpleMove(squares);

//		List<Square> captured = collectCaptured(squares);
//		move = new Capture(target, captured);
		return move ;
	}
	
	List<Square> collectCaptured(Square[] squares) {
		List<Square> captured = new ArrayList<>();
		return captured ;
	}

	/**
	 * Может ли фигура "дышать"?
	 * Есть ли у нее или у соседних своих клеток пустая 
	 * ближайшая клетка по вертикали или коризонтали.
	 * @return
	 */
	public boolean hasDame() {
		// TODO Go Реализовать проверку правила Dame.
		return true;
	}
}
