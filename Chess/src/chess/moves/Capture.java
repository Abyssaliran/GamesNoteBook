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

	public Capture(Square[] squares) {
		super(squares);
		
		capturedSquare = squares[1];
		capturedPiece = capturedSquare.getPiece();
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
}
