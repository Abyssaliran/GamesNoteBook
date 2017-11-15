package tamerlan.move;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import tamerlan.pieces.King;

/**
 * Ход шахмат Тамерлана - перемещение фигуры на клетку 
 * с захватом фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove {
	private Square capturedSquare;
	private Piece capturedPiece;
	
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
