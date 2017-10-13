package chess;

import chess.pieces.Pawn;
import game.core.Board;
import game.core.PieceColor;

public class Chess {
	/**
	 * Расстановка шахматных фигур в начальную позицию.
	 * @return доска с расставленными фигурами.
	 */
	static public Board getInitBoard() {
		Board board = new Board(8, 8);
		for (int v = 0; v < board.nV; v++) {
			new Pawn(board.getSquare(v, 1), PieceColor.BLACK);
			new Pawn(board.getSquare(v, 6), PieceColor.WHITE);
		}
		return board;
	}
}
