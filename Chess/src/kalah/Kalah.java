package kalah;

import game.core.Game;
import game.players.IPlayer;
import game.players.Neznaika;

public class Kalah extends Game {
	static {
		Game.addPlayer(Kalah.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Kalah.class, new Neznaika(100));
	}

	public Kalah() {
		initBoardDefault();
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(8, 2);
		
		for (int h = 0; h < 2; h++)
			for (int v = 1; v < 7; v++)
				new Heap(board.getSquare(v, h), 6);
	}
}