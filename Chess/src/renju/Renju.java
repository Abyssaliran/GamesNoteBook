package renju;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Vinni;

import renju.piece.Stone;

public class Renju extends Game {
	private static final IPieceProvider pieceProvider = (square, color) -> new Stone(square, color);

	static {
		Game.addPlayer(Renju.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Renju.class, new Vinni(pieceProvider));
	}

	/**
	 * Вернуть инициализированную доску для игры в реверси.
	 */
	public Renju() {
		initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
		board.setBlackPlayer(new Vinni(pieceProvider));
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(15, 15);

		new Stone(board.getSquare(7, 7), PieceColor.BLACK);
	}
}