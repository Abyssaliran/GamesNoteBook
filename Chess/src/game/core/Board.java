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
	 * История партии (последовательность ходов игры).
	 */
	public History history = new History(this);
	
	/**
	 * Цвет фигуры которая должна сделать ход.
	 */
	public PieceColor moveColor = PieceColor.WHITE;

	public Board(int nV, int nH) {
		this.nV = nV;
		this.nH = nH;
		
		squares = new Square[nV][nH];
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)
				squares[v][h] = new Square(this, v, h);
		}

	/**
	 * Уведомить обозревателей доски (классы реализующие интерфейс Observable)
	 * что на доске произошли изменения.
	 * 
	 * @see java.util.Observable
	 * @see java.util.Observer
	 */
	public void setBoardChanged() {
		// Вызвать protected метод базового класса - Observer.
		super.setChanged();
		super.notifyObservers();
	}

	/** 
	 * Вернуть клетку доски
	 * 
	 * @param v - вертикаль клетки.
	 * @param h - горизонталь клетки.
	 * @return - клетка с задаными вертикалью и горизонталью.
	 */
	public Square getSquare(int v, int h) {
		return squares[v][h];
	}

	/**
	 * Проверка выхода координат клетки за границы доски.
	 * 
	 * @param v - вертикаль клетки
	 * @param h - горизонталь клетки
	 * @return - есть ли клетка с такими координатами на доске.
	 */
	public boolean onBoard(int v, int h) {
		if (v < 0) return false;
		if (h < 0) return false;
		
		if (v > nV-1) return false;
		if (h > nH-1) return false;
		
		return true;
	}

	/**
	 * @return - ширина доски
	 */
	public int getWidth() {
		return nV;
	}

	/**
	 * @return - высота доски
	 */
	public int getHeight() {
		return nH;
	}

	/**
	 * Пуста ли клетка с заданными координатами?
	 * 
	 * @param v - вертикаль
	 * @param h - горизонталь
	 * @return
	 */
	public boolean isEmpty(int v, int h) {
		return getSquare(v, h).isEmpty();
	}
}
