/**
 * 
 */
package game.core;

/**
 * Фигура стоящая на клетке доски.
 * Абстрактный базовый класс для всех фигур всех игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class Piece {
	/**
	 * Цвет фигуры.
	 */
	PieceColor color;

	/**
	 * Клетка на которой стоит фигура.
	 */
	public Square square;
	
	public Piece(Square square, PieceColor color) {
		this.square = square;
		this.color = color;
		
		square.setPiece(this);
	}
	
	/**
	 * @return вернуть цвет фигуры.
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * @return задать цвет фигуры.
	 */
	public void getColor(PieceColor color) {
		this.color = color;
	}
	
	/**
	 * Является ли корректным ход фигурой для заданой последовательности клеток?
	 * @param squares - последтвательность клеток через которые перемещается фигура.
	 * @return корректен ход или нет.
	 */
	abstract public boolean isCorrectMove(Square ...squares);
	
	/**
	 * Сделать ход фигурой для заданой последовательности клеток
	 * и вернуть описание хода для сохранения его в истории.
	 * 
	 * @param squares - последтвательность клеток через которые перемещается фигура.
	 * @return экжемпляр класса реализующего интерфейс <b>Move</b>.
	 */
	abstract public Move makeMove(Square ...squares);
}
