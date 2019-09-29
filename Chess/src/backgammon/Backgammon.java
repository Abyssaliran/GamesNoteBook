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
		super.initBoard(12, 12);
		
		new Stone( board.getSquare(0, 0), PieceColor.WHITE);
		new Stone( board.getSquare(0, 1), PieceColor.WHITE);
		new Stone( board.getSquare(0, 2), PieceColor.WHITE);
		new Stone( board.getSquare(0, 3), PieceColor.WHITE);
		new Stone( board.getSquare(0, 4), PieceColor.WHITE);
		
		new Stone( board.getSquare(4, 9), PieceColor.WHITE);
		new Stone( board.getSquare(4,10), PieceColor.WHITE);
		new Stone( board.getSquare(4,11), PieceColor.WHITE);

		new Stone( board.getSquare(6, 7), PieceColor.WHITE);
		new Stone( board.getSquare(6, 8), PieceColor.WHITE);
		new Stone( board.getSquare(6, 9), PieceColor.WHITE);
		new Stone( board.getSquare(6,10), PieceColor.WHITE);
		new Stone( board.getSquare(6,11), PieceColor.WHITE);

		new Stone( board.getSquare(11, 0), PieceColor.WHITE);
		new Stone( board.getSquare(11, 1), PieceColor.WHITE);

		new Stone( board.getSquare(0, 7), PieceColor.BLACK);
		new Stone( board.getSquare(0, 8), PieceColor.BLACK);
		new Stone( board.getSquare(0, 9), PieceColor.BLACK);
		new Stone( board.getSquare(0,10), PieceColor.BLACK);
		new Stone( board.getSquare(0,11), PieceColor.BLACK);

		new Stone( board.getSquare(4, 0), PieceColor.BLACK);
		new Stone( board.getSquare(4, 1), PieceColor.BLACK);
		new Stone( board.getSquare(4, 2), PieceColor.BLACK);

		new Stone( board.getSquare(6, 0), PieceColor.BLACK);
		new Stone( board.getSquare(6, 1), PieceColor.BLACK);
		new Stone( board.getSquare(6, 2), PieceColor.BLACK);
		new Stone( board.getSquare(6, 3), PieceColor.BLACK);
		new Stone( board.getSquare(6, 4), PieceColor.BLACK);

		new Stone( board.getSquare(11,10), PieceColor.BLACK);
		new Stone( board.getSquare(11,11), PieceColor.BLACK);
	}}
