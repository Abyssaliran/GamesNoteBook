package go;

import game.core.Board;

/**
 * Игра <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Go {
	public static Board getInitBoard(int v, int h) {
		Board board = new Board(v, h);

		return board;
	}
}
