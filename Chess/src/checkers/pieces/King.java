/**
 * 
 */
package checkers.pieces;

import checkers.moves.Capture;
import checkers.moves.SimpleMove;
import game.core.Dirs;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Дамка в шашках.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends CheckersPiece {

	public King(Square square, PieceColor color) {
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

		if (target.isDiagonal(source)) {
			if (target.isEmptyDiagonal(source)) {
				// Это простой ход дамкой.
				
				// Проверим может быть есть ходы с захватом фигуры.
				// В шашках такие ходы-захваты обязательны.
				if (hasCaptures())
					return false; // Простой ход не делаем.
				
				return true;
			}

			Piece captured = getOneOpponentDiagonalPiece(source, target);
			if (captured != null)
				return true;
		}

		// TODO Checkers Сделать проверку правильности хода
		// из клетки source в клетку target.

		return false;
	}

	private static Piece getOneOpponentDiagonalPiece(Square a, Square b) {
		if (!a.isDiagonal(b) || a.isEmpty())
			return null;
		int count = 0;
		Piece oneDiagonalPiece = null;
		int n = Math.abs(a.v - b.v);
		int dv = a.v > b.v ? -1 : 1;
		int dh = a.h > b.h ? -1 : 1;
		for (int k = 1; k <= n - 1; k++) {
			Square temp = a.getBoard().getSquare(a.v + k * dv, a.h + k * dh);
			if (!temp.isEmpty()) {
				if (count == 1)
					return null;
				if (a.getPiece().getColor() != temp.getPiece().getColor()) {
					oneDiagonalPiece = temp.getPiece();
					count++;
				}
			}
		}
		return oneDiagonalPiece;
	}

	@Override
	public Move makeMove(Square... squares) {
		Square source = squares[0];
		Square target = squares[1];

		Piece captured = getOneOpponentDiagonalPiece(source, target);

		if (source.isEmptyDiagonal(target))
			 return new SimpleMove(false, source, target);
		else return new Capture(false, captured, source, target);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	protected boolean hasCapture() {
		// Двигаемся по всем диагоналям.
		for (Dirs d : Dirs.DIAGONAL) {
			Square s = square;

			while (s.hasNext(d)) {
				s = s.next(d);
				
				if (s.isEmpty()) // Клетка пустая.
					continue;    // Продолжим движение дамки в направлении d.

				if (hasFriend(s))
					break; // Через свою фигуру не перепрыгнешь.

				if (!s.hasNext(d))
					break; // Вражеская фигура на краю доски.
				
				if (!s.next(d).isEmpty())
					break; // Клетка для прыжка дамки занята.
				
				return true; // Клетка для прыжка дамки свободна.
			}
		}
		return false;
	}
}
