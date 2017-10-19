package vikings;

import game.core.Board;
import game.core.PieceColor;
import vikings.pieces.Сyning;

/**
 * Игра 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB">Викинги (Хнефатафл, Тавлеи) </a>.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Vikings {
	/**
	 * Создание доски заданного размера
	 * и расстановка фигур для этого размера доски.
	 * 
	 * @param boardSize - размер доски.
	 * @return доска с расставленными фигурами.
	 */
	public static Board getInitBoard(int boardSize) {
		switch (boardSize) {
			case  9: return initBoard9();
			case 11: return initBoard11();
		}

		return null;
	}

	/** 
	 * Создание доски размером 11х11
	 * и расстановка фигур для этого размера доски.
	 * 
	 * @return доска с расставленными фигурами.
	 */
	private static Board initBoard11() {
		Board board = new Board(11, 11);
		
		new Сyning(board.getSquare(5, 5), PieceColor.WHITE);

		return board ;
	}

	/** 
	 * Создание доски размером 9х9
	 * и расстановка фигур для этого размера доски.
	 * 
	 * @return доска с расставленными фигурами.
	 */
	private static Board initBoard9() {
		Board board = new Board(9, 9);
		
		new Сyning(board.getSquare(4, 4), PieceColor.WHITE);
		
		return board ;
	}
}
