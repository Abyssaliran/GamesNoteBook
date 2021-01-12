package renju;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Vinni;
import renju.pieces.Stone;
import renju.players.Buratino;
import renju.players.Carlo;
import renju.players.Karabas;
import renju.players.KarabasClever;
import renju.players.Malvina;
import renju.players.Nancy;

public class Renju extends Game {
	private static final int BOARD_SIZE = 15;
	
	private static final IPieceProvider pieceProvider = (square, color) -> new Stone(square, color);

	static {
		Game.addPlayer(Renju.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Renju.class, new Vinni(pieceProvider, BOARD_SIZE * BOARD_SIZE));
		Game.addPlayer(Renju.class, new Buratino(pieceProvider));
		Game.addPlayer(Renju.class, new Karabas(pieceProvider));
		Game.addPlayer(Renju.class, new KarabasClever(pieceProvider));
		Game.addPlayer(Renju.class, new Nancy(pieceProvider));
		Game.addPlayer(Renju.class, new Malvina(pieceProvider));
		Game.addPlayer(Renju.class, new Carlo(pieceProvider));
	}

	public Renju() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Vinni(pieceProvider, BOARD_SIZE * BOARD_SIZE));
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(BOARD_SIZE, BOARD_SIZE);

		new Stone(board.getSquare(BOARD_SIZE/2, BOARD_SIZE/2), PieceColor.BLACK);
	}
}
