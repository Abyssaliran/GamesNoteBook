package game.core;

import java.util.Observable;

/**
 * Доска для расстановки фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Board extends Observable {
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
	
	/**
	 * История партии (последовательность ходов).
	 */
	public History history = new History();
	
	public Board(int nV, int nH) {
		this.nV = nV;
		this.nH = nH;
		
		squares = new Square[nV][nH];
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)
				squares[v][h] = new Square(this, v, h);
		}

	public void setChanged() {
		super.setChanged();
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

	/**
	 * Проверка выхода за границы доски.
	 * 
	 * @param v - вертикаль
	 * @param h - горизонталь
	 * @return на доске или нет
	 */
	public boolean onBoard(int v, int h) {
		if (v < 0) return false;
		if (h < 0) return false;
		
		if (v > nV-1) return false;
		if (h > nH-1) return false;
		
		return true;
	}
}
