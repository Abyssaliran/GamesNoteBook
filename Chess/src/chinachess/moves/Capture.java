package chinachess.moves;

import game.core.Piece;
import game.core.Square;

/**
 * Ход китайских шахматах - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 * Игра <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 * 
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
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
