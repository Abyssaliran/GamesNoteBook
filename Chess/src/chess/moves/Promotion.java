package chess.moves;

import game.core.Square;

/**
 * Ход европейских шахмат - преврашение пешки на последней горизонтали
 * в новую фигуру с возможным взятием фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Promotion extends SimpleMove implements ICapture {

	public Promotion(Square[] squares) {
		super(squares);
	}

	@Override
	public void doMove() {
		// TODO Auto-generated method stub
	}

	@Override
	public void undoMove() {
		// TODO Auto-generated method stub
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
