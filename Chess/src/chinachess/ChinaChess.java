package chinachess;


import chinachess.pieces.*;

import game.core.Board;
import game.core.PieceColor;

public class ChinaChess {
	public static Board getInitBoard() {
		Board board = new Board(9, 9);
		
		//расставляем пешки через одну, на все четные линии
		for (int v = 0; v < board.nV; v++) {
			if (v%2 == 0) {
				new Pawn(board.getSquare(v, 3), PieceColor.BLACK);
				new Pawn(board.getSquare(v, 5), PieceColor.WHITE);
			}
			
		}
		new King(board.getSquare(4, 0), PieceColor.BLACK);
		new King(board.getSquare(4, 8), PieceColor.WHITE);
		
		new Rook(board.getSquare(0, 0), PieceColor.BLACK);
		new Rook(board.getSquare(7, 0), PieceColor.BLACK);
		new Rook(board.getSquare(0, 7), PieceColor.WHITE);
		new Rook(board.getSquare(7, 7), PieceColor.WHITE);
		
		return board;
	}

}
