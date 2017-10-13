/**
 * 
 */
package game.core;

/**
 * Фигура стоящая на клетке доски.
 * Абстрактный базовый класс для всех фигур всех игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 *
 */
abstract
public class Piece {
	/**
	 * Клетка на которой стоит фигура.
	 */
	public Square square;
	
	public Piece(Square square) {
		this.square = square;
		square.setPiece(this);
	}
}
