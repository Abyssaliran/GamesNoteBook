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
	
	/**
	 * Клетки доски.
	 */
	private Square[][] squares;
	
	public Board(int nV, int nH) {
		this.nV = nV;
		this.nH = nH;
		
		squares = new Square[nV][nH];
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)
				squares[v][h] = new Square(this, v, h);
		}
	
	/** 
	 * Вернуть клетку доски
	 * @param v вертикаль клетки
	 * @param h горихонталь клетки
	 * @return клетка с задаными вертикалью и горизонталью.
	 */
	public Square getSquare(int v, int h) {
		return squares[v][h];
	}
}
