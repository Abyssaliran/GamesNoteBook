package shogi.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

abstract
public class ShogiPiece extends Piece {
	public boolean isTransformaed = false;

	public ShogiPiece(Square square, PieceColor color) {
		super(square, color);
	}

    @Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		if (target.isEmpty()) 
			return true;
		
		return false;
		
//		// Если идем на клетку, занятую фигурой 
//		// того же цвета, то ход не корректен.
//		return getColor() != target.getPiece().getColor();
	}
}