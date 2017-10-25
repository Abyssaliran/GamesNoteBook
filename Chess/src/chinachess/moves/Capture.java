package chinachess.moves;

import game.core.Square;

/**
 * Ход китайских шахматах - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove {
	public Capture(Square[] squares) {
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
}
