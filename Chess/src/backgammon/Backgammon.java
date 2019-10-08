package backgammon;

import game.core.Game;
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
		Game.addPlayer(Backgammon.class, new Neznaika(100));
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
		backgamonBoard.initDefaultPosition();
	}


}
