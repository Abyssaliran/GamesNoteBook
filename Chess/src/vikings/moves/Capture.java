package vikings.moves;

import java.util.List;

import game.core.Square;
import game.core.moves.ICaptureMove;

/**
 * Ход с захватом фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove implements ICaptureMove {
	/**
	 * Клетка на которых стоят захваченные фигуры.
	 */
	private List<Square> captured;

	/**
	 * Ход с захватом фигур противника.
	 * 
	 * @param captured - клетки где стоят захваченные фигуры противника.
	 * @param squares
	 *            - клетки хода. 
	 * 
	 * <pre>
	 * squares[0] откуда идет (source) 
	 * squares[1] куда идет (target)
	 * </pre>
	 */
	public Capture(List<Square> captured, Square[] squares) {
		super(squares);
		
		this.captured = captured;
	}
	
	@Override
	public List<Square> getCaptured() {
		return captured;
	}

	@Override
	public void doMove() {
		// TODO Auto-generated method stub
	}

	@Override
	public void undoMove() {
		// TODO Auto-generated method stub
	}

	public void removePiece() {
		// TODO Auto-generated method stub
	}

	public void restotePiece() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}
}
