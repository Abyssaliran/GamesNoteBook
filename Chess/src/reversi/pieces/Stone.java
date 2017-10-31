package reversi.pieces;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.Dirs;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import reversi.moves.ReversiMove;

/**
 * Фигура-камень 
 * для <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Stone extends Piece {

	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		if (!hasEnemy(target))
			return false;
		
		return true;
	}

	/**
	 * Будут ли у фигуры враги (фигуры противоположного цвета),
	 * если ее поставить на клетку target. 
	 * 
	 * Если врагов нет, захватывать некого, и такой ход в реверси недопустим.
	 * 
	 * @param target - проверяемая клетка.
	 * @return - есть ли враги при постановке фигуры на эту клетку.
	 */
	private boolean hasEnemy(Square target) {
		Board board = target.getBoard();
		
		int tv = target.v;
		int th = target.h;

		// Цикл по всем 8-и направлениям.
		for (Dirs d : Dirs.ALL) {
			int v = tv + d.dv;
			int h = th + d.dh;
			
			if (!board.onBoard(v, h))
				continue; // Рядом клетки нет, Вышли за пределы доски.
			
			Piece p = board.getSquare(v, h).getPiece();
			if (p == null)
				continue; // Рядом пустая клетка.
			
			if (getColor() != p.getColor())
				return true; // Нашли рядом вражескую фигуру.	
		}
		
		return false; // Не нашли рядом вражескую фигуру.
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[0];
		
		List<Square> captured = collectCaptured(target);
		
		return new ReversiMove(this, target, captured);
	}

	/**
	 * Собрать все клетки, на которых стоят захваченные в плен (окруженные)
	 * фигуры противника.
	 * 
	 * @param target - клетка куда поставлена фигура.
	 * @return - клетки с захваченными в плен фигурами противника.
	 */
	private List<Square> collectCaptured(Square target) {
		List<Square> captured = new ArrayList<>();
		
		// TODO Auto-generated method stub
		
		return captured;
	}

}
