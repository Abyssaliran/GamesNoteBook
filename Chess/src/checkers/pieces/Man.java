package checkers.pieces;

import checkers.moves.Capture;
import checkers.moves.SimpleMove;
import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Простая фигура в шашках.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Man extends CheckersPiece {
	public Man(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;

		Square source = square;     // клетка где уже стоит фигура.
		Square target = squares[0]; // клетка куда хочет пойти фигура.
		
		// Вычислим смещение фигуры.
		boolean isBlack = getColor() == PieceColor.BLACK;
		int dv = target.v - source.v;
		int dh = isBlack 
				? target.h - source.h  // Черная фигура идет вниз (от h=0 до h=7).
				: source.h - target.h; // Белая фигура идет вверх (от h=7 до h=0).
		
		// Отбросим ходы не по диагонали.
		// У диагонали смещения по абсолютной величина совпадают.
		boolean isDiagonal = (Math.abs(dh) == Math.abs(dv));
		if (!isDiagonal)
			return false;
		
		// Теперь у нас ход диагональный.
		if (Math.abs(dh) == 1) {
			// Смещение на 1 клетку по диагонали 
			// - это простой ход без захвата.
			
			// Проверяем не хочет ли фигура пойти назад.
			// Если да, то ход неправильный.
			if (dh < 0)
				return false;
			
			// Проверяем не хочет ли фигура пойти занятую клетку.
			// Если да, то ход неправильный.
			if (!target.isEmpty())
				return false;
			
			// Все проверки фигура прошла. Ход правильный.
			return true;						
		}
		else 
		if (Math.abs(dh) == 2) {
			// Ход на две клетки - это простой ход с захватом.
			
			// Смотрим клетку, через которую перепрыгнули.
			int capturedH = (source.h + target.h) / 2;
			int capturedV = (source.v + target.v) / 2;
			
		    Board board = square.getBoard();
			Square capturedSquare = board.getSquare(capturedV, capturedH);
		    
			Piece captured = capturedSquare.getPiece();
			
			// Перепрыгнули через пустую клетку.
			if (captured == null)
				return false; 
			
			// Перепрыгнули через фигуру того же цвета.
			if (getColor() == captured.getColor())
				return false;
			
			// Все проверки фигура прошла. Ход правильный.
			return true;						
		}
		
		return false;
	}

	@Override
	public Move makeMove(Square... squares) {
		Move move = null;
		
		Square source = squares[0];
		Square target = squares[1];	
		
		boolean isBlack = getColor() == PieceColor.BLACK;
		boolean isPromotion = isBlack 
				? target.h == 8 
				: target.h == 0;
		
		boolean isCapture = Math.abs(target.v - source.v) == 2;

		if (!isCapture){
		    move = new SimpleMove(isPromotion , source, target);
		}
		else {
			int capturedH = (source.h + target.h) / 2;
			int capturedV = (source.v + target.v) / 2;
			
		    Board board = square.getBoard();
			Square capturedSquare = board.getSquare(capturedV, capturedH);
		    
			Piece capturedPiece = capturedSquare.getPiece();
			
			move = new Capture(isPromotion, capturedPiece, source, target);
		}
		return move;
	}
	
	@Override
	public String toString() {
		return "Man" + square;
	}
}
