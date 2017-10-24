package tamerlan.move;

import game.core.Square;

/**
 * Ход шахмат Тамерлана - перемещение фигуры на клетку 
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
		// TODO Утаев - реализовать захват фигуры 
	}

	@Override
	public void undoMove() {
		// TODO Утаев - реализовать отмену захвата фигуры 
	}
}
