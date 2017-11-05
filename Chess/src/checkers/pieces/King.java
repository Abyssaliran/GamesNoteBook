/**
 * 
 */
package checkers.pieces;

import java.util.ArrayList;
import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Дамка в шашках.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends CheckersPiece {
	private Piece pastMan;
	
	public King(Piece pastMan, Square square, PieceColor color) {
		super(square, color);
		this.pastMan = pastMan;
	}
	
	public Piece getMan() {
		return pastMan;
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		Square source = square;
		Square target = squares[0];
		
		if (target.isDiagonal(source)) {
			List<PieceColor> diagonalPiecesColor = getDiagonalPiecesColor(source, target);
			if (target.isEmptyDiagonal(source)) return true;
			if (diagonalPiecesColor.size() > 1) return false;
			else if (!diagonalPiecesColor.contains(getColor())) return true;
		}
		
		// TODO Checkers Сделать проверку правильности хода
		// из клетки source в клетку target.

		return false;
	}
	
	private List<PieceColor> getDiagonalPiecesColor (Square a, Square b){
		if (!a.isDiagonal(b)||a.isEmpty()) return null;
		List<PieceColor> diagonalPiecesColor = new ArrayList<>();
		int n = Math.abs(a.v - b.v);
		int dv = a.v > b.v ? -1: 1;
		int dh = a.h > b.h ? -1: 1;
		for (int k = 1; k < n - 1; k++) {
			Square temp = a.getBoard().getSquare(a.v + k*dv, a.h + k*dh);
			if (!temp.isEmpty()) {
				diagonalPiecesColor.add(temp.getPiece().getColor());
			}
		}
		return diagonalPiecesColor;
	}

	@Override
	public Move makeMove(Square... squares) {
		Move move = null;
		// TODO Checkers Создать ход шашек
		// if (...)
		//    move = new SimpleMove(isPromotion, source, target);
		// else
		//    move = new Capture(isPromotion, captured, source, target);
		// move.doMove();
		return move;
	}
	
	@Override
	public String toString() {
		return "King" + square;
	}
}
