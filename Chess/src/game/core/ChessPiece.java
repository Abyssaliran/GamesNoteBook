package game.core;

/**
 * Базовый класс для всех шахматных фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class ChessPiece extends Piece {
	public ChessPiece(Square square) {
		super(square);
	}
}
