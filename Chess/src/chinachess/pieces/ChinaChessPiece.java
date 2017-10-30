package chinachess.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 * 
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
abstract
public class ChinaChessPiece extends Piece {
	public ChinaChessPiece(Square square, PieceColor color) {
		super(square, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		if (target.isEmpty()) 
			return true;
		
		// Если идем на клетку, занятую фигурой 
		// того же цвета, то ход не корректен.
		return getColor() != target.getPiece().getColor();
	}
}
