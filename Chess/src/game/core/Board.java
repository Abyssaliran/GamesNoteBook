package game.core;

/**
 * Доска для расстановки фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Board {
	/**
	 * Количество вертикалей на доске.
	 */
	public int nV;
	/**
	 * Количество горизонталей на доске.
	 */
	public int nH;
	
	public Board(int nV, int nH) {
		this.nV = nV;
		this.nH = nH;
	}
}
