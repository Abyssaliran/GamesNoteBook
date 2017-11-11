package checkers;

import checkers.pieces.Man;
import game.core.Board;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import tamerlan.TamerlanChess;

/**
 * Класс представляющий игру шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Checkers extends Game {
	static {
		addPlayer(TamerlanChess.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(TamerlanChess.class, new Neznaika());
	}

	/**
	 * Расстановка фигур шашек в начальную позицию.
	 * @return доска с расставленными фигурами.
	 */
	static 
	public Board getInitBoard() {
		Board board = new Board(8, 8);
		
		setHorizontal(board, 0, PieceColor.BLACK);
		setHorizontal(board, 1, PieceColor.BLACK);
		setHorizontal(board, 2, PieceColor.BLACK);
		
		setHorizontal(board, 5, PieceColor.WHITE);
		setHorizontal(board, 6, PieceColor.WHITE);
		setHorizontal(board, 7, PieceColor.WHITE);
		
		return board;
	}

	/**
	 * Расставить шашки на заданой горизонтали.
	 * 
	 * @param board - доска на которую ставят шашки.
	 * @param h - горизонталь для расстановки
	 * @param color - цвет шашек.
	 */
	static 
	private void setHorizontal(Board board, int h, PieceColor color) {
		for (int v = (h+1) % 2; v < board.nV; v += 2)
			new Man(board.getSquare(v, h), color);
	}
}
