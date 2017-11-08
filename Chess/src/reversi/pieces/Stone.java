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
 * Фигура-камень для <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
 * Реверси</a>
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

		// Если рядом с клеткой нет вражеских фигур,
		// то ход туда не корректен.
		if (!hasEnemy(target))
			return false;

		// Если при ходе на клетку target не произойдет захват вражеских фигур,
		// то ход туда не корректен.
		if (!isPossibleCapture(target))
			return false;

		return true;
	}

	/**
	 * Будут ли у фигуры враги (фигуры противоположного цвета), если ее
	 * поставить на клетку target.
	 * 
	 * Если врагов нет, захватывать некого, и такой ход в реверси недопустим.
	 * 
	 * @param target
	 *            - проверяемая клетка.
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

	/**
	 * Проверить произойдет захват вражеских фигур при постановке фигуры на
	 * клетку.
	 * 
	 * @param target
	 *            - куда ставят фигуру.
	 * @return - возможен ли захват вражеских фигур.
	 */
	private boolean isPossibleCapture(Square target) {
		// Цикл по всем 8-и направлениям.
		for (Dirs direction : Dirs.ALL)
			if (hasCaptured(target, direction))
				return true; // Захват фигур хотя бы по одному направлению
							 // возможен.

		return false;
	}

	/**
	 * Возможен ли захват фигуры при движении из заданной клетки в заданном
	 * направлении.
	 * 
	 * @param source
	 *            - из какой клетки двигаемся.
	 * @param direction
	 *            - в каком направлении двигаемся.
	 * @return - возможен ли захват вражеских фигур.
	 */
	private boolean hasCaptured(Square source, Dirs direction) {
		Board board = source.getBoard();
		
		// TODO Задорожная.
		// Реализовать проверку возможности захвата вражеских фигур.
		
		int sv = source.v + direction.dv;
		int sh = source.h + direction.dh;
		
		int nCaptured = 0;
		
		if ((sv == 2) && (sh == 3))
			System.out.println();
		
		while (board.onBoard(sv, sh)) {
			Square nextSquare = board.getSquare(sv, sh);
			
			if (nextSquare.isEmpty())
				return false;
			
			PieceColor nextColor = nextSquare.getPiece().getColor();
			
			if (nextColor != getColor()) {
				// Это враг.
				nCaptured++;

				// Смещаемся в заданном направлении.
				sv = sv + direction.dv;
				sh = sv + direction.dh;
				
				continue;
			}
				
			if (nextColor == getColor()) {
				// Это друг.
				return nCaptured >  0;
			}
		}

		return true;
	}

	/**
	 * Двигаясь из клетки <b>source</b> (куда ставится наша фигура), в заданном
	 * направлении, в список captured собираются клетки, на которых соят
	 * вражеские фигуры.
	 * 
	 * @param source
	 *            - из какой клетки движемся.
	 * @param direction
	 *            - в каком направлении движемся.
	 * @param captured
	 *            - список в который добавляем клетки с вражескими фигурами.
	 */
	private void collectCaptured(Square source, Dirs direction, List<Square> captured) {
		// TODO Задорожная.
		// Реализовать захват вражеских фигур.

	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[0];

		// Соберем захваченные вражеские фигуры.
		List<Square> captured = collectCaptured(target);

		return new ReversiMove(this, target, captured);
	}

	/**
	 * Собрать все клетки, на которых стоят захваченные в плен (окруженные)
	 * фигуры противника.
	 * 
	 * @param target
	 *            - клетка куда поставлена фигура.
	 * @return - клетки с захваченными в плен фигурами противника.
	 */
	private List<Square> collectCaptured(Square target) {
		List<Square> captured = new ArrayList<>();

		// Цикл по всем 8-и направлениям.
		for (Dirs direction : Dirs.ALL)
			if (hasCaptured(target, direction))
				collectCaptured(target, direction, captured);

		return captured;
	}
}
