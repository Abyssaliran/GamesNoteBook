package vikings.pieces;

import java.util.List;

import game.core.Board;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import vikings.moves.Capture;
import vikings.moves.SimpleMove;

/**
 * Фигура - викинг.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Viking extends VikingsPiece {
	public Viking(Square square, PieceColor color) {
		super(square, color);
	}
	
	@Override
	public boolean isCorrectMove(Square... squares) {
		if (!super.isCorrectMove(squares))
			return false;
		
		Square target = squares[0];
		
		if (square.isEmptyVertical(target))
			return true;
		
		if (square.isEmptyHorizontal(target))
			return true;
		
		return false;
	}

	@Override
	public Move makeMove(Square... squares) {
		// Соберем захваченные вражеские фигуры.
		Board board = square.getBoard();
		PieceColor oponentColor = board.getOponentColor(getColor());
		
		List<Square> captured = collectCaptured(board, oponentColor);
		
		// Если захваченные фигуры есть, 
		// то вернем ход - захват фигур.
		if (!captured.isEmpty())
			return new Capture(captured, squares);
		
		return new SimpleMove(squares);
	}

	@Override
	public String toString() {
		return "";
	}
}
