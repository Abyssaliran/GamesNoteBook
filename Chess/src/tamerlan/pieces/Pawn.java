package tamerlan.pieces;

import chess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import tamerlan.move.Capture;

/**
 * Класс представляющий на доске пешку европейских шахмат.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Pawn extends TamerlanPiece {
	public Pawn(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		// TODO Утаев - проверить правильность хода пешкой.
		
		//Пешка не может делать первый ход на два поля. 
		//Соответственно, отсутствует и взятие на проходе
		Square target = squares[0];
		if (Math.abs(target.h - square.h) > 1) 
			return false;
	 
		//Пешка не может делать ход по диагонали,если это не захват
		if (square.h != target.h && square.v != target.v && target.isEmpty()) 
			return false;
		 
		//Пешка не может делать ход на занятую клетку впереди
		if (square.v == target.v && !target.isEmpty()) 
			return false;
		 
		//Пешка не может делать ход по текущей горизонтали
		if (target.h == square.h) 
			return false;
		 
		//Пешка не может делать ход назад (правило для белой фигуры)
		if (getColor() == PieceColor.WHITE && target.h > square.h)  
			return false;
		 
		//Пешка не может делать ход назад (правило для черной фигуры)
		if (getColor() == PieceColor.BLACK && target.h < square.h)  
			return false;
		
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		// TODO Утаев - если это захват фигуры противника,
		// то вернуть ход-захват фигуры new Capture();
		
		Square target = squares[1];
		
		if (!target.isEmpty())
			 return new Capture(squares);
		else return new SimpleMove(squares);
	}
}
