package tamerlan;

import game.core.Board;

/**
 * Расстановка фигур для <a href=
 * "https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">
 * Шахмат Тамерлана</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChess {

	public static Board getInitBoard() {
		Board board = new Board(10, 10);

		return board;
	}
}
