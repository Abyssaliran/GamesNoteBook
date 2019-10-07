package backgammon;

import game.core.Board;
import game.core.Cube;
import game.core.GameOver;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.IPlayer;

/**
 * Доска для игры в нарды.
 * Используются два кубика для получения случайных чисел.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class BackgammonBoard extends Board {
	public Cube cube1 = new Cube();
	public Cube cube2 = new Cube();
	
	public BackgammonBoard() {
		dropCubes4Start();
	}
	
	public void dropCubes() {
		cube1.drop();
		cube2.drop();
	}

	/**
	 * Бросание кубиков чтобы определить кто пойдет первым.
	 */
	public void dropCubes4Start() {
		do { dropCubes(); }
		while (cube1.getValue() == cube2.getValue());
	}
	
	public void startGame() {
//		// Выбираем случайным образом очередность хода.
//		dropCubes4Start();
//		moveColor = (cube1.getValue() > cube2.getValue())
//				? PieceColor.WHITE : PieceColor.BLACK;
//		
//		setBoardChanged();
		
		// Пока всегда первыми ходят белые.
		moveColor = PieceColor.WHITE;
		
		for (;;) {
			IPlayer player = players.get(moveColor);

			// Бросаем кубики для хода очередного игрока.
			dropCubes();
			setBoardChanged();

			if (player == IPlayer.HOMO_SAPIENCE) 
				break; // Ход сделает человек.
			
			try { player.doMove(this, moveColor); }			
			catch (GameOver e) 
				{ break; }

			moveColor = getOponentColor(moveColor);
		}
	}

	/**
	 * Расположена ли заданная клетка в верхней части доски.
	 * @param s - заданная клетка
	 * @return расположение.
	 */
	public boolean isTopSide(Square s) {
		return s.h == 0;
	}
	
	/**
	 * Расположена ли заданная клетка в верхней части доски.
	 * @param s - заданная клетка
	 * @return расположение.
	 */
	public boolean isBottomSide(Square s) {
		return !isTopSide(s);
	}
	
	/**
	 * Расположена ли заданная клетка в левой части доски.
	 * @param s - заданная клетка
	 * @return расположение.
	 */
	public boolean isLeftSide(Square s) {
		return s.v < nV/2-2;
	}

	/**
	 * Это клетка для выкладывания захваченных фигур противника?
	 * Находится на вертикали расположеннй в середине доски.
	 */
	public boolean isBar(Square s) {
		return s.v == nV/2-1;
	}

	/**
	 * Расположена ли заданная клетка в правой части доски.
	 * @param s - заданная клетка
	 * @return расположение клетки.
	 */
	public boolean isRightSide(Square s) {
		return (nV/2-1 < s.v) && (s.v < nV-1);
	}
	
	/**
	 * Это клетка для сбрасывания своих фигур с доски?
	 * Находится на вертикали расположеннй справа от доски.
	 */
	public boolean isForBearing(Square s) {
		return s.v == nV-1;
	}
	
	/**
	 * Это клетка для выкладывания захваченных фигур противника?
	 * 
	 * @param s - проверяемая клетка доски.
	 * @param color - для фигур какого цвета проверка.
	 * @return та ли это клетка.
	 */
	public boolean isBar4Color(Square s, PieceColor color) {
		boolean isTheSide = (color == PieceColor.WHITE) 
				? s.h == 0 : s.h == 1;
				
		return isBar(s) && isTheSide;
	}
	
	/**
	 * Это клетка для выкладывания захваченных фигур противника?
	 * 
	 * @param s - проверяемая клетка доски.
	 * @param p - для какой фигуры проверка.
	 * @return та ли это клетка.
	 */
	public boolean isBar4Piece(Square s, Piece p) {
		return isBar4Color(s, p.getColor());
	}
	
	/**
	 * Это клетка для сбрасывания фигур заданного цвета с доски?
	 * Находится на вертикали расположеннй справа от доски.
	 * 
	 * @param s - проверяемая клетка доски.
	 * @param color - для фигур какого цвета проверка.
	 * @return та ли это клетка.
	 */
	public boolean isForBearing(Square s, PieceColor color) {
		boolean isTheSide = (color == PieceColor.WHITE) 
				? s.h == 1 : s.h == 0;
				
		return isForBearing(s) && isTheSide;
	}

	/**
	 * Все ли фигуры заданного параметром цвета
	 * находятся дома?
	 * @param color - цвет фигуры.
	 * @return все ли дома
	 */
	public boolean allInHome(PieceColor color) {
		for (Piece p : getPieces(color))
			if (!isInHome(p))
				return false;
		
		return true;
	}

	/**
	 * Находится ли фигура во своем дворе
	 * (на подходе к своему дому)?
	 * @param p - проверяемая фигура.
	 * @return во дворе или нет.
	 */
	public boolean isInInner(Piece p) {
		return isPieceSide(p) && isLeftSide(p.square);
	}

	/**
	 * Находится ли фигура дома?
	 * @param p - проверяемая фигура.
	 * @return дома или нет
	 */
	public boolean isInHome(Piece p) {
		return isPieceSide(p) && isRightSide(p.square);
	}

	/**
	 * Это своя сторона доски для заданной параметром фигуры, 
	 * @param p фигура
	 * @return та ли сторона.
	 */
	private boolean isPieceSide(Piece p) {
		Square square = p.square;
		return (p.getColor() == PieceColor.BLACK) 
				? isTopSide(square) 
				: isBottomSide(square);
	}
}