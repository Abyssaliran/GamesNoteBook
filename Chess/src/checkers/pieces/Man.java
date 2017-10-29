/**
 * 
 */
package checkers.pieces;

import game.core.Move;
import game.core.PieceColor;
import checkers.moves.Capture;
import game.core.Dirs;
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
		
		Square source = squares[0];
		Square target = squares[1];
		//Введены промежуточные клетки для атакующего хода
		Square middleLeftDown = squares[2];
		Square middleRightDown = squares[3];
		Square middleLeftUp = squares[4];
		Square middleRightUp = squares[5];
		
		
		//В перечислении Dirs введены геттеры
		boolean isSimpleMoveBlack = (source.getPiece().getColor() == PieceColor.BLACK) && 
									target.h - source.h == Dirs.LEFT_DOWN.getDh() && 
									target.v - source.v == Dirs.LEFT_DOWN.getDv() &&
									target.h - source.h == Dirs.RIGHT_DOWN.getDh() && 
									target.v - source.v == Dirs.RIGHT_DOWN.getDv();
		
		boolean isSimpleMoveWhite = (source.getPiece().getColor() == PieceColor.WHITE) && 
									target.h - source.h == Dirs.LEFT_UP.getDh() && 
									target.v - source.v == Dirs.LEFT_UP.getDv() &&
									target.h - source.h == Dirs.RIGHT_UP.getDh() && 
									target.v - source.v == Dirs.RIGHT_UP.getDv();
		
		if (isSimpleMoveBlack || isSimpleMoveWhite) return true; 
		
		//Временное решение
		//TODO: нужно исправить
		boolean isCaptureMove = Math.abs(target.h - source.h) == 2 &&
				  				Math.abs(target.v - source.v) == 2;

		
		if (isCaptureMove) {
			
			//Определение координат ближайших диагональных клеток
			middleLeftDown.h = source.h + Dirs.LEFT_DOWN.getDh();
			middleLeftDown.v = source.v + Dirs.LEFT_DOWN.getDh();
			middleRightDown.h = source.h + Dirs.RIGHT_DOWN.getDh();
			middleRightDown.v = source.v + Dirs.RIGHT_DOWN.getDh();
			middleLeftUp.h = source.h + Dirs.LEFT_UP.getDh();
			middleLeftUp.h = source.h + Dirs.LEFT_UP.getDh();
			middleRightUp.h = source.h + Dirs.RIGHT_UP.getDh();
			middleRightUp.h = source.h + Dirs.RIGHT_UP.getDh();	
			
			//Условие для выяснения занятости клетки
			boolean isOccupied = !middleLeftDown.isEmpty() ||
							 	 !middleRightDown.isEmpty() ||
							 	 !middleLeftUp.isEmpty() ||
							 	 !middleRightUp.isEmpty();
			
			//Ближайшие диагональные клетки свободны, следовательно, ход нелегален
			if (!isOccupied) return false;
			
			//Условие для выяснения цвета захваченной фигуры
			
			boolean isSameColor = middleLeftDown.getPiece().getColor() == source.getPiece().getColor() ||
								   middleRightDown.getPiece().getColor() == source.getPiece().getColor() ||
							       middleLeftUp.getPiece().getColor() == source.getPiece().getColor() ||
							       middleRightUp.getPiece().getColor() == source.getPiece().getColor();
					
			if (isCaptureMove && isOccupied && !isSameColor) return true;
		} 
		
		// TODO Checkers Сделать проверку правильности хода
		// из клетки source в клетку target.
		
		//Частично выполнено, будет дополняться.
		
		//В противном случае, если вышестоящие условия не выполняются, ход нелегален.
		return false;
	}

	@Override
	public Move makeMove(Square... squares) {
		Move move = null;
		
		// TODO Checkers Создать ход шашек
		// Просьба проверить.
		
		Square source = squares[0];
		Square target = squares[1];	
		
		if (isCorrectMove(source, target)){
		    move = new SimpleMove(isPromotion, source, target);
		}
		else {
		    move = new Capture(isPromotion, captured, source, target);
		    move.doMove();
		}
		return move;
	}
}
