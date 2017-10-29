package chess.pieces;

import chess.moves.Capture;
import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Класс представляющий на доске пешку европейских шахмат.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Pawn extends ChessPiece {
	public Pawn(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square target = squares[0];
		
		if (square.v != target.v)
			return false;
		
		int dh = (getColor() == PieceColor.WHITE) 
				? square.h - target.h 
				: target.h - square.h;
		
		boolean isStartPosition = 
					(getColor() == PieceColor.WHITE) 
						? square.h == 6 : square.h == 1;  
		
		int upper = isStartPosition ? 2 : 1;
		
		if ((1 <= dh) && (dh <= upper))
			return true;
		
		return false;
	}
	
	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		
		if (!target.isEmpty())
			return new Capture(squares);
		
		return new SimpleMove(squares);
	}
}
