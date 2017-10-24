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
		
		//Guns on positions
		new Gun(board.getSquare(1, 2), PieceColor.BLACK);
		new Gun(board.getSquare(7, 2), PieceColor.BLACK);
		new Gun(board.getSquare(1, 6), PieceColor.WHITE);
		new Gun(board.getSquare(7, 6), PieceColor.WHITE);
		
		//Kings in the castle
		new King(board.getSquare(4, 0), PieceColor.BLACK);
		new King(board.getSquare(4, 8), PieceColor.WHITE);
		
		//Guardians with it's king
		new Guardian(board.getSquare(3, 0), PieceColor.BLACK);
		new Guardian(board.getSquare(5, 0), PieceColor.BLACK);
		new Guardian(board.getSquare(3, 8), PieceColor.WHITE);
		new Guardian(board.getSquare(5, 8), PieceColor.WHITE);
		
		//Bishops in positions
		new Bishop(board.getSquare(2, 0), PieceColor.BLACK);
		new Bishop(board.getSquare(6, 0), PieceColor.BLACK);
		new Bishop(board.getSquare(2, 8), PieceColor.WHITE);
		new Bishop(board.getSquare(6, 8), PieceColor.WHITE);
		
		//Knights on positions
		new Knight(board.getSquare(7, 0), PieceColor.BLACK);
		new Knight(board.getSquare(1, 0), PieceColor.BLACK);
		new Knight(board.getSquare(7, 8), PieceColor.WHITE);
		new Knight(board.getSquare(1, 8), PieceColor.WHITE);
		
		//Rooks in positions
		new Rook(board.getSquare(0, 0), PieceColor.BLACK);
		new Rook(board.getSquare(8, 0), PieceColor.BLACK);
		new Rook(board.getSquare(0, 8), PieceColor.WHITE);
		new Rook(board.getSquare(8, 8), PieceColor.WHITE);
		
		return board;
	}

}
