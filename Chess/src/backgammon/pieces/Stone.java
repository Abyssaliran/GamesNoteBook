package backgammon.pieces;

import backgammon.BackgammonBoard;
import backgammon.moves.SimpleMove;
import game.core.ITrackPiece;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура для игры в нарды.
 */
public class Stone extends Piece implements ITrackPiece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		BackgammonBoard board = (BackgammonBoard) square.getBoard();
		
		int step1 = board.cube1.getValue();
		int step2 = board.cube2.getValue();
		
		PieceColor color = getColor();
		
		//
		// Проверяем клетку из которой делаем ход.
		//
		int topH = board.isTopSide(square) ? 1 : -1;

		// Проверяем есть ди фигура над клеткой
		// с которой делается ход.
		// Самая ли верхняя это фигура,
		if (board.onBoard(square.v, square.h + topH))
			if (!board.isEmpty(square.v, square.h + topH))
				return false; // Сверху стоит фигура. 
		
		//
		// Проверяем клетку куда идем.
		//
		Square target = squares[0];
		
		// Сама фигура пойти на клетку для хранения захваченных фигур 
		// (сдаться в плен) не может.
		if (board.isBar(target))
			return false;
		
		// Пока все фигуры такого же цвета не дома,
		// сбрасывать фигуру с доски нельзя.
		if (board.isForBearing(target) && !board.allInHome(color))
			return false;
		
		// В нардах нельзя фигурой ходить на поле 
		// уже занятое фигурой любого цвета.
		if (!target.isEmpty())
			return false;	

		int bottomH = board.isTopSide(target) ? -1 : 1;

		// Проверяем есть ли фигура ПОД клеткой 
		// на которую ставим фигуру.
		if (!board.onBoard(target.v, target.h + bottomH))
			return true; // Ставим фигуру на край доски.
		
		if (board.isEmpty(target.v, target.h + bottomH))
			return false; // Нет фигуры на которую можно поставить.
		
		return true;	
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		return new SimpleMove(this, square, target);
	}

	@Override
	public boolean hasCorrectMoveFrom(Square square) {
		return true;
	}
	
	@Override
	public String toString() {
		return "";
	}
}
