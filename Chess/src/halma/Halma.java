package halma;

import game.core.Board;
import game.core.PieceColor;
import halma.pieces.Stone;

/**
 * Игра <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Halma {

	public static Board getInitBoard(int boardSize) {
		Board board = new Board(boardSize, boardSize);

		new Stone( board.getSquare(0, 0), PieceColor.WHITE);
		new Stone( board.getSquare(boardSize - 1, boardSize - 1), PieceColor.BLACK);

		return board;
	}
}
