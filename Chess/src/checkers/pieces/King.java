/**
 * 
 */
package checkers.pieces;

import checkers.moves.Capture;
import checkers.moves.SimpleMove;
import game.core.Board;
import game.core.DiagDirs;
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
			if (target.isEmptyDiagonal(source))
				return true;
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
		Move move = null;
		// TODO Checkers Создать ход шашек

		Square source = squares[0];
		Square target = squares[1];

		Piece captured = getOneOpponentDiagonalPiece(source, target);

		if (source.isEmptyDiagonal(target))
			move = new SimpleMove(false, source, target);
		else if (captured != null)
			move = new Capture(false, captured, source, target);
		// move.doMove();
		return move;
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	protected boolean hasCapture() {
		Board board = square.getBoard();

		for (DiagDirs d : DiagDirs.ALL) {
			// Смотрим по всем диагоналям.

			int nextV = square.v;
			int nextH = square.h;

			for (;;) {
				// Следующая клетка в направлении .
				nextV += d.dv;
				nextH += d.dh;

				if (!board.onBoard(nextV, nextH))
					break; // Дошли до края доски.

				Square nextS = board.getSquare(nextV, nextH);
				if (nextS.isEmpty())
					continue; // Продолжим движение в заданном направлении.

				Piece nextP = nextS.getPiece();
				if (nextP.getColor() == getColor())
					break; // Через свою фигуру не перепрыгнешь.

				// Смотрим клетку для прыжка через фигуру противника.
				int next2V = nextV + d.dv;
				int next2H = nextH + d.dh;

				if (!board.onBoard(next2V, next2H))
					break; // Фигура на доски краю. Прыгаем с доски.

				Square next2S = board.getSquare(next2V, next2H);
				if (next2S.isEmpty())
					return true; // // Прыгаем на пустое поле.
			}
		}
		return false;
	}
}
