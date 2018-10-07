package chess.moves;

import game.core.Square;

/**
 * Ход европейских шахмат - взятие пешки на проходе.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class EnPassant extends Capture implements ICapture {

	public EnPassant(Square[] squares) {
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
	public void restorePiece() {
		// TODO Auto-generated method stub
		
	}
}
