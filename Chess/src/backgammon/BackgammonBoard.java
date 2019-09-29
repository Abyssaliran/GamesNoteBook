package backgammon;

import game.core.Board;
import game.core.Cube;
import game.core.GameOver;
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
		
		// Пока всегда первыми ходят белыею
		moveColor = PieceColor.WHITE;
		
		System.out.format("%n%nBackgammonBoard.startGame() %n"); 
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
		return s.h < nH / 2;
	}
}