package game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import game.players.IPlayer;
import game.players.Neznaika;

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
	private PieceColor moveColor = PieceColor.WHITE;
	
	Map<PieceColor, IPlayer> players = new HashMap<>(); 
	{
		setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		setBlackPlayer( new Neznaika() );
	}

	/**
	 * Создать доску с заданным количеством вертикалей и горизонталей.
	 * 
	 * @param nV
	 *            - количество вертикалей
	 * @param nH
	 *            - количество горизонталей.
	 */
	public Board(int nV, int nH) {
		this.nV = nV;
		this.nH = nH;
		
		squares = new Square[nV][nH];
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)
				squares[v][h] = new Square(this, v, h);
	}

	/**
	 * Уведомить обозревателей доски (классы реализующие интерфейс <b>Observable</b>)<br>
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
	 * Смена цвета (игрока который должен сделать ход).
	 */
	public void changeMoveColor() {
		moveColor = getOponentColor(moveColor);
		
		IPlayer player = players.get(moveColor);
		if (player == IPlayer.HOMO_SAPIENCE)
			return; // Ход сделает человек мышкой.
		
		player.doMove(this, moveColor);
	}

	/**
	 * Дать цвет противоположный заданному цвету.
	 * 
	 * @param сolor
	 *            - заданный цвет фигуры.
	 * @return противоположный цвет фигур.
	 */
	public PieceColor getOponentColor(PieceColor сolor) {
		return сolor == PieceColor.WHITE 
				? PieceColor.BLACK : PieceColor.WHITE;
	}
	
	/**
	 * Выдать цвет фигуры, которая должна сделать ход.
	 * 
	 * @return - цвет фигуры.
	 */
	public PieceColor getMoveColor() {
		return moveColor;
	}

	/**
	 * Вернуть клетку доски.
	 * 
	 * @param v
	 *            - вертикаль клетки.
	 * @param h
	 *            - горизонталь клетки.
	 * @return клетка с задаными вертикалью и горизонталью.
	 */
	public Square getSquare(int v, int h) {
		return squares[v][h];
	}

	/**
	 * Проверка выхода координат клетки за границы доски.
	 * 
	 * @param v
	 *            - вертикаль клетки
	 * @param h
	 *            - горизонталь клетки
	 * @return есть ли клетка с такими координатами на доске.
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

	/**
	 * Зададать игрока белыми фигурами.
	 * 
	 * @param player - игрок белыми фигурами.
	 * 
	 * @see game.players.IPlayer
	 */
	public void setWhitePlayer(IPlayer player) {
		players.put(PieceColor.WHITE, player);
	}

	/**
	 * Зададать игрока черными фигурами.
	 * 
	 * @param player - игрок черными фигурами.
	 * 
	 * @see game.players.IPlayer
	 */
	public void setBlackPlayer(IPlayer player) {
		players.put(PieceColor.BLACK, player);
	}

	/**
	 * Выдать игрока белыми фигурами.
	 * 
	 * @return
	 */
	public IPlayer getWhitePlayer() {
		return players.get(PieceColor.WHITE);
	}

	/**
	 * Выдать игрока черными фигурами.
	 * @return
	 */
	public IPlayer getBlackPlayer() {
		return players.get(PieceColor.BLACK);
	}
	
	/**
	 * Выдать список всех расположенных на доске фигур заданного цвета.
	 * 
	 * @param color
	 *            - цвет фигуры.
	 * @return - список фигур.
	 */
	public List<Piece> getPieces(PieceColor color) {
		List<Piece> pieces = new ArrayList<>();		

		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				Square s = getSquare(v, h);
				
				Piece p = s.getPiece();
				if (p == null) continue;
				
				if (p.color != color)
					continue;
				
				pieces.add(p);
			}
		
		return pieces;
	}
	
	/**
	 * Выдать список всех пустых клеток доски.
	 * 
	 * @return - список всех клеток доски.
	 */
	public List<Square> getEmptySquares() {
		List<game.core.Square> emptySquares = new ArrayList<>();
		
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				game.core.Square square = getSquare(v, h);
				if (square.isEmpty())
					emptySquares.add( square);
			}

		return emptySquares;
	}

	/**
	 * Для заданной фигуры найти список клеток, на которые ход данной фигурой
	 * допустим.
	 * 
	 * @param piece
	 *            - проверяемая фигура.
	 * @return список допустимых для хода клеток.
	 */
	public List<Square> getPieceTargets(Piece piece) {
		List<Square> targets = new ArrayList<>();
		
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++) {
				Square target = getSquare(v, h);
				
				if (piece.isCorrectMove(target))
					targets .add(target);
			}
		
		return targets;
	}

	/**
	 * Выдать список всех клеток доски.
	 * 
	 * @return - список
	 */
	public List<Square> getSquares() {
		List<Square> allSquares = new ArrayList<>();
		
		for (int v = 0; v < nV; v++)
			for (int h = 0; h < nH; h++)  
				allSquares.add(getSquare(v, h));
		
		return allSquares;
	}
}
