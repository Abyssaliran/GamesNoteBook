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
	 * Удалить фигуру с доски. 
	 */
	public void remove() {

		square.piece = null;

		square = null;
	}
	/**
	 * Переместить фигуру на указанную клетку. 
	 * 
	 * @param target - куда переместить.
	 */
	public void moveTo(Square target) {
		if (square == null)
			System.out.println("" + target);
		
		square.piece = null;
		
		target.piece = this;
		square = target;
	}

	/**
	 * Стоит ли на клетке <b>s</b> вражеская фигура.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	protected boolean hasEnemy(Square s) {
		if (s.isEmpty())
			return false;
		
		return s.getPiece().getColor() != getColor();
	}

	/**
	 * Стоит ли на клетке <b>s</b> вражеская фигура.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	protected boolean hasFriend(Square s) {
		if (s.isEmpty())
			return false;
		
		return s.getPiece().getColor() == getColor();
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
