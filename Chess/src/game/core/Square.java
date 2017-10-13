package game.core;

/**
 * Клетка на доске настольных игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Square {
	/**
	 * Вертикаль клетки
	 */
	public int v;

	/**
	 * Горизонталь клетки
	 */
	public int h;

	/**
	 * Доска на которой расположена клетка.
	 */
	private Board board;
	
	protected Square(Board board, int v, int h) {
		this.v = v;
		this.h = h;
		this.board = board;
	}

	/**
	 * @return доска на которой стоит клетка.
	 */
	public Board getBoard() {
		return board;
	}
}

