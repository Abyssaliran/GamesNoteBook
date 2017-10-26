package chinachess.moves;

import game.core.Piece;
import game.core.Square;

/**
 * Ход китайских шахматах - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove {
	private Square capturedSquare;
	private Piece  capturedPiece;

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
}
