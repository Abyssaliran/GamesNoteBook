package backgammon;

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
	}}
