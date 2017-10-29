package tamerlan;

import tamerlan.pieces.Bishop;
import tamerlan.pieces.Giraffe;
import tamerlan.pieces.King;
import tamerlan.pieces.Knight;
import tamerlan.pieces.Pawn;
import tamerlan.pieces.Queen;
import tamerlan.pieces.Rook;
import game.core.Board;
import game.core.PieceColor;
import tamerlan.pieces.Vizir;
import tamerlan.pieces.WarMachine;

/**
 * Расстановка фигур для <a href=
 * "https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">
 * Шахмат Тамерлана</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChess {

	public static Board getInitBoard() {
		Board board = new Board(10, 14);
		
		// Расставляем пешки.
		for (int v = 0; v < board.nV; v++) {
			if (v != 4 && v != 5) {
				new Pawn(board.getSquare(v, 3), PieceColor.BLACK);
				new Pawn(board.getSquare(v, 10), PieceColor.WHITE);
			}
			else {
				new Pawn(board.getSquare(v, 4), PieceColor.BLACK);
				new Pawn(board.getSquare(v, 9), PieceColor.WHITE);
			}
		}

		// Расставляем ладьи.
		new Rook(board.getSquare(0, 2), PieceColor.BLACK);
		new Rook(board.getSquare(9, 2), PieceColor.BLACK);
		new Rook(board.getSquare(0, 11), PieceColor.WHITE);
		new Rook(board.getSquare(9, 11), PieceColor.WHITE);

		// Расставляем коней.
		new Knight(board.getSquare(1, 2), PieceColor.BLACK);
		new Knight(board.getSquare(8, 2), PieceColor.BLACK);
		new Knight(board.getSquare(1, 11), PieceColor.WHITE);
		new Knight(board.getSquare(8, 11), PieceColor.WHITE);

		// Расставляем слонов.
		new Bishop(board.getSquare(2, 2), PieceColor.BLACK);
		new Bishop(board.getSquare(7, 2), PieceColor.BLACK);
		new Bishop(board.getSquare(2, 11), PieceColor.WHITE);
		new Bishop(board.getSquare(7, 11), PieceColor.WHITE);

		// Расставляем ферзей.
		new Queen(board.getSquare(3, 2), PieceColor.BLACK);
		new Queen(board.getSquare(6, 11), PieceColor.WHITE);

		// Расставляем королей.
		new King(board.getSquare(4, 2), PieceColor.BLACK);
		new King(board.getSquare(5, 11), PieceColor.WHITE);
		
		// Расставляем жирафов.
		new Giraffe(board.getSquare(5, 2), PieceColor.BLACK);
		new Giraffe(board.getSquare(4, 11), PieceColor.WHITE);
		
		// Расставляем визирей.
		new Vizir(board.getSquare(6, 2), PieceColor.BLACK);
		new Vizir(board.getSquare(3, 11), PieceColor.WHITE);

		// Расставляем визирей.
		new WarMachine(board.getSquare(4, 3), PieceColor.BLACK);
		new WarMachine(board.getSquare(5, 3), PieceColor.BLACK);
		new WarMachine(board.getSquare(4, 10), PieceColor.WHITE);
		new WarMachine(board.getSquare(5, 10), PieceColor.WHITE);

		return board;
	}
}
