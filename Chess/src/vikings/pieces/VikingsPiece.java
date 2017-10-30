package vikings.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для фигур игры Викинги.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class VikingsPiece extends Piece {
	public VikingsPiece(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		return target.isEmpty();
	}
	
	static 
	public boolean isTron(Square s) {
		if (s.v != s.getBoard().nV/2)
			return false;
		
		if (s.h != s.getBoard().nH/2)
			return false;
		
		return true;
	}
	
	static 
	public boolean isExit(Square s) {
		int nv = s.getBoard().nV-1;
		int nh = s.getBoard().nH-1;

		if ((s.v == 0) && (s.h == 0)) 
			return true;
		
		if ((s.v == 0) && (s.h == nh)) 
			return true;
		
		if ((s.v == nv) && (s.h == 0)) 
			return true;
		
		if ((s.v == nv) && (s.h == nh)) 
			return true;

		return false;
	}
}
