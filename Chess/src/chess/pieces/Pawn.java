package chess.pieces;

import chess.moves.Capture;
import chess.moves.Promotion;
import chess.moves.SimpleMove;
import game.core.Board;
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
		
		Square source = square;
		Square target = squares[0];
		
		int dv = Math.abs(target.v - source.v);
		int dh = (getColor() == PieceColor.WHITE) 
				? source.h - target.h 
				: target.h - source.h;
		
		if (dv != 0) {
			// Есть смещение пешки по вертикали. 
			// Возможно это взятие пешкой вражеской фигуры.
			
			if (dv > 1)
			   return false; // Смещение больше чем на 1 клетку
			
			if (target.isEmpty())
				return false; // Бить некого.
			
			if (dh <= 0)      // Смещение пешки назад.
				return false; // Назад пешки не бьют.
			
			if (dh > 1)       // Так далеко пешки не бьют.
				return false;  
			
			return true;
		}
		
		// По вертикали пешка фигуры не бьет.
		if (!target.isEmpty())
			return false;
		
		boolean isStartPosition = // Откуда пошла пешка.
					(getColor() == PieceColor.WHITE) 
						? source.h == 6 : source.h == 1;  
		
		int upper = isStartPosition ? 2 : 1; // Насколько может пойти.
		
		if (upper == 2) {
			// Пешка прыгает с начальной позиции.
			
			// Не пытается ли пешка перепыгнуть через фигуру (барьер)?
			int barierV = source.v;
			int barierH = (source.h + target.h) / 2;
			
			Board board = source.getBoard();
			if (!board.isEmpty(barierV, barierH))
				return false; // Перепрыгивать нельзя.
		}
		
		if ((1 <= dh) && (dh <= upper))
			return true;
		
		return false;
	}
	
	@Override
	public Move makeMove(Square... squares) {
		Square source = squares[0];
		Square target = squares[1];
		
		boolean isLastHorizontal = (
				getColor() == PieceColor.WHITE 
					? target.h == 0 
					: target.h == 7);
		
		if (isLastHorizontal) // Ход на последнюю горизонталь.
			return new Promotion(squares);
		
		if (Math.abs(target.v - source.v) == 1) // Ход по диагонали.
			return new Capture(squares);
		
		return new SimpleMove(squares);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
