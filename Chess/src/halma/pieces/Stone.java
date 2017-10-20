package halma.pieces;

import java.util.ArrayList;
import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import reversi.moves.ReversiMove;

/**
 * Фигура для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Stone extends Piece {

	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[0];
		
		List<Square> captured = collectCaptured(target);
		
		return new ReversiMove(target, captured);
	}

	/**
	 * Собрать все клетки, на которых стоят захваченные в плен (окруженные)
	 * фигуры противника.
	 * 
	 * @param target - клетка куда поставлена фигура.
	 * @return - клетки с захваченными в плен фигурами противника.
	 */
	private List<Square> collectCaptured(Square target) {
		List<Square> captured = new ArrayList<>();
		
		// TODO Auto-generated method stub
		
		return captured;
	}

}
