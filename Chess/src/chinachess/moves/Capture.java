package chinachess.moves;

import chinachess.pieces.King;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.PieceColor;
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
	public void doMove() throws GameOver {
		capturedPiece.remove();
		super.doMove();
		
		if (capturedPiece instanceof King) {
			PieceColor kingColor = capturedPiece.getColor();
			
			GameResult result = (
				kingColor == PieceColor.WHITE
					? GameResult.BLACK_WIN 
					: GameResult.WHITE_WIN);
			
			throw new GameOver(result);
		}
	}

	@Override
	public void undoMove() {
		super.undoMove();
		capturedSquare.setPiece(capturedPiece);
	}
	
	@Override
	public String toString() {
		return "" + piece + source + "x" + target;
	}
}
