package chess.moves;

import game.core.Piece;
import game.core.Square;

/**
 * Ход европейских шахмат - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove implements ICapture {
	private Piece capturedPiece;
	private Square capturedSquare;

	/**
	 * Ход - захват фигуры.
	 * squares[0] - откуда идет фигура.
	 * squares[1] - куда идет фигура.
	 * Захваченая фигура стоит на squares[1].
	 * 
	 * @param squares - клетки хода.
	 */
	public Capture(Square[] squares) {
		super(squares);
		
		capturedSquare = squares[1];
		capturedPiece = capturedSquare.getPiece();
	}

	/**
	 * Ход - захват фигуры. <br>
	 * squares[0] - откуда идет фигура. <br>
	 * squares[1] - куда идет фигура. <br>
	 * Захваченая фигура - это первый параметр. <br>
	 * Используется при взятии пешки на проходе.
	 * 
	 * @param captured
	 *            - захваченная фигура.
	 * @param squares
	 *            - клетки хода.
	 */
	public Capture(Piece captured, Square[] squares) {
		super(squares);
		
		capturedPiece  = captured;
		capturedSquare = captured.square;
	}

	@Override
	public void doMove() {
		capturedPiece.remove();
		super.doMove();
	}

	@Override
	public void undoMove() {
		super.undoMove();
		capturedSquare.setPiece(capturedPiece);
	}

	@Override
	public void removePiece() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restotePiece() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}
}
