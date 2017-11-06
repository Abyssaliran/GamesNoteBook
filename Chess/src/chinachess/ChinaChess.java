package chinachess;


import chinachess.pieces.*;

import game.core.Board;
import game.core.PieceColor;

/**
 * Игра <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 *
 */
public class ChinaChess {
	public static Board getInitBoard() {
		Board board = new Board(9, 10);
		
		putPieces(board, PieceColor.BLACK);
		putPieces(board, PieceColor.WHITE);
		
		return board;
	}

	private static void putPieces(Board board, PieceColor color) {
		int hPiece = (color == PieceColor.BLACK ? 0 : board.nH - 1);
		int hGun   = (color == PieceColor.BLACK ? 2 : board.nH - 3);
		int hPawn  = (color == PieceColor.BLACK ? 3 : board.nH - 4);
		
		// Расставляем пешки через одну, на все четные линии
		for (int v = 0; v < board.nV; v += 2) 
			new Pawn(board.getSquare(v, hPawn), color);
		
		// Guns on positions
		new Gun(board.getSquare(1, hGun), color);
		new Gun(board.getSquare(7, hGun), color);

		// Kings in the castle
		new King(board.getSquare(4, hPiece), color);

		// Guardians with it's king
		new Guardian(board.getSquare(3, hPiece), color);
		new Guardian(board.getSquare(5, hPiece), color);
		
		// Bishops in positions
		new Bishop(board.getSquare(2, hPiece), color);
		new Bishop(board.getSquare(6, hPiece), color);

		// Knights on positions
		new Knight(board.getSquare(7, hPiece), color);
		new Knight(board.getSquare(1, hPiece), color);
		
		// Rooks in positions
		new Rook(board.getSquare(0,  hPiece), color);
		new Rook(board.getSquare(8, hPiece), color);
	}
}
