package reversi;

import game.core.Board;
import game.core.Game;
import game.core.PieceColor;
import reversi.pieces.Hole;
import reversi.pieces.Stone;

/**
 * Игра 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Reversi extends Game {
	/**
	 * Вернуть инициализированную доску для игры в реверси.
	 * 
	 * @param nHoles - количество случайно расположенных отверстий.
	 * @return доска с расставленными отверстиями (если они нужны).
	 */
	public static Board getInitBoard(int nHoles) {
		Board board = new Board(8, 8);
		
		new Stone( board.getSquare(3, 3), PieceColor.BLACK);
		new Stone( board.getSquare(4, 4), PieceColor.BLACK);
		
		new Stone( board.getSquare(3, 4), PieceColor.WHITE);
		new Stone( board.getSquare(4, 3), PieceColor.WHITE);
		
		if (nHoles != 0) {
			int randomV = (int) (8 * Math.random());
			int randomH = (int) (8 * Math.random());
			
			new Hole( board.getSquare(randomV, randomH), PieceColor.BLACK);
		}
		
		return board;
	}
}
