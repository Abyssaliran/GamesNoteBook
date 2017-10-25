package tamerlan.move;

import java.lang.annotation.Target;

import chess.moves.ICapture;
import game.core.PieceColor;
import game.core.Square;

/**
 * Ход шахмат Тамерлана - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove implements ICapture {
	Square[] sq = null;
	public Capture(Square[] squares) {
		super(squares);
		sq = squares;
	}

	@Override
	public void doMove() {
		// TODO Утаев - реализовать захват фигуры 
		if (isCapture()) {
			Square targetCache = target;
			removePiece();
			piece.moveTo(targetCache);
		}
	}

	@Override
	public void undoMove() {
		// TODO Утаев - реализовать отмену захвата фигуры 
	}

	@Override
	public void removePiece() {
		for (int i = 0; i < target.getBoard().nV; i++) {
			if (piece.getColor() == PieceColor.BLACK) {
				for (int j = 0; j < 2; j++) {
					if (target.getBoard().getSquare(i, j).isEmpty()) {
						target.movePieceTo(target.getBoard().getSquare(i, j));
						return;
					}
				}
			} else {
				for (int j = 13; j > 11; j--) {
					if (target.getBoard().getSquare(i, j).isEmpty()) {
						target.movePieceTo(target.getBoard().getSquare(i, j));
						return;
					}
				}
			}
		}
	}

	@Override
	public void restotePiece() {
		// TODO Auto-generated method stub
		
	}
	
}
