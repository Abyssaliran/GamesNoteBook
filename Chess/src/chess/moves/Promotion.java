package chess.moves;

import chess.pieces.Queen;
import game.core.Piece;
import game.core.Square;

/**
 * Ход европейских шахмат - преврашение пешки на последней горизонтали
 * в новую фигуру с возможным взятием фигуры противника.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Promotion extends SimpleMove {

	private Piece capturedPiece;
	private Queen promotedPiece;
	private Piece pawn;

	public Promotion(Square[] squares) {
		super(squares);
		
		pawn = source.getPiece();
		
		if (source.v != target.v)  
			// Ход по диагонали со взятием фигуры.
			capturedPiece = target.getPiece();
	}
	
	/* 
	 * Удалить пешку, поставить фигуру.
	 */
	@Override
	public void doMove() {
		if (capturedPiece != null)
			target.removePiece();

		source.removePiece();
		promotedPiece = new Queen(target, pawn.getColor());
		
		target.setPiece(promotedPiece);
	}

	/* 
	 * Удалить фигуру, поставить пешку.
	 */
	@Override
	public void undoMove() {
		target.removePiece();
		source.setPiece(piece);

		if (capturedPiece != null)
			target.setPiece(capturedPiece);
	}
	
	@Override
	public String toString() {
		String movekind  = (capturedPiece == null) ? "-" : "x";
		String pieceKind = promotedPiece.toString();
		
		return "" + piece + source + movekind + target + pieceKind;
	}
}
