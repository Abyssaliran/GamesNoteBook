package backgammon;

import backgammon.pieces.BackgammonGroup;
import backgammon.pieces.Stone;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;

/**
 * Правила игры в короткие нарды:
 * 
 * <a href="https://ru.wikipedia.org/wiki/Короткие_нарды">Короткие_нарды</a>
 */
public class Backgammon extends Game {
	static {
		Game.addPlayer(Backgammon.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Backgammon.class, new Neznaika());
	}

	private BackgammonBoard backgamonBoard;

	public Backgammon() {
		backgamonBoard = new BackgammonBoard();
		board  = backgamonBoard;
			
		initBoardDefault();
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	@Override
	public void initBoardDefault() {
		// + по одной колонке для захваченных фигур противника
		// и для своих сброшенных с доски фигур.
		super.initBoard(12+1+1, 2);
		
//		initDebugPosition();
		
		initDefaultPosition();
	}

	public void initDebugPosition() {
		new BackgammonGroup( board.getSquare(0, 0), PieceColor.WHITE,  5);
		new BackgammonGroup( board.getSquare(1, 0), PieceColor.WHITE,  6);
		new BackgammonGroup( board.getSquare(2, 0), PieceColor.WHITE,  7);
		new BackgammonGroup( board.getSquare(3, 0), PieceColor.WHITE,  8);
		new BackgammonGroup( board.getSquare(4, 0), PieceColor.WHITE,  9);
		new BackgammonGroup( board.getSquare(5, 0), PieceColor.WHITE, 10);
		
		new BackgammonGroup( board.getSquare(0, 1), PieceColor.BLACK, 10);
	}

	/**
	 * Умаличиваемая позиуия для игры в короткие нарды.
	 */
	public void initDefaultPosition() {
		new BackgammonGroup( board.getSquare( 0, 0), PieceColor.WHITE, 5);
		new BackgammonGroup( board.getSquare( 4, 1), PieceColor.WHITE, 3);
		new BackgammonGroup( board.getSquare( 7, 1), PieceColor.WHITE, 5);
		new BackgammonGroup( board.getSquare(12, 0), PieceColor.WHITE, 2);
		
		new BackgammonGroup( board.getSquare( 0, 1), PieceColor.BLACK, 5);
		new BackgammonGroup( board.getSquare( 4, 0), PieceColor.BLACK, 3);
		new BackgammonGroup( board.getSquare( 7, 0), PieceColor.BLACK, 5);
		new BackgammonGroup( board.getSquare(12, 1), PieceColor.BLACK, 2);
	}

	public void initBoardDefault1() {
		// + по одной колонке для захваченных фигур противника
		// и для своих сброшенных с доски фигур.
		super.initBoard(12+1+1, 12);
		
		//
		// Белые
		//
		new Stone( board.getSquare(0, 0), PieceColor.WHITE);
		new Stone( board.getSquare(0, 1), PieceColor.WHITE);
		new Stone( board.getSquare(0, 2), PieceColor.WHITE);
		new Stone( board.getSquare(0, 3), PieceColor.WHITE);
		new Stone( board.getSquare(0, 4), PieceColor.WHITE);
		
		new Stone( board.getSquare(4, 9), PieceColor.WHITE);
		new Stone( board.getSquare(4,10), PieceColor.WHITE);
		new Stone( board.getSquare(4,11), PieceColor.WHITE);

		new Stone( board.getSquare(7, 7), PieceColor.WHITE);
		new Stone( board.getSquare(7, 8), PieceColor.WHITE);
		new Stone( board.getSquare(7, 9), PieceColor.WHITE);
		new Stone( board.getSquare(7,10), PieceColor.WHITE);
		new Stone( board.getSquare(7,11), PieceColor.WHITE);

		new Stone( board.getSquare(12, 0), PieceColor.WHITE);
		new Stone( board.getSquare(12, 1), PieceColor.WHITE);

		//
		// Черные
		//
		new Stone( board.getSquare(0, 7), PieceColor.BLACK);
		new Stone( board.getSquare(0, 8), PieceColor.BLACK);
		new Stone( board.getSquare(0, 9), PieceColor.BLACK);
		new Stone( board.getSquare(0,10), PieceColor.BLACK);
		new Stone( board.getSquare(0,11), PieceColor.BLACK);

		new Stone( board.getSquare(4, 0), PieceColor.BLACK);
		new Stone( board.getSquare(4, 1), PieceColor.BLACK);
		new Stone( board.getSquare(4, 2), PieceColor.BLACK);

		new Stone( board.getSquare(7, 0), PieceColor.BLACK);
		new Stone( board.getSquare(7, 1), PieceColor.BLACK);
		new Stone( board.getSquare(7, 2), PieceColor.BLACK);
		new Stone( board.getSquare(7, 3), PieceColor.BLACK);
		new Stone( board.getSquare(7, 4), PieceColor.BLACK);

		new Stone( board.getSquare(12,10), PieceColor.BLACK);
		new Stone( board.getSquare(12,11), PieceColor.BLACK);
	}
}
