package attract;

import attract.pieces.AttracPiece;
import attract.players.BearPlayer;
import attract.players.PandaPlayer;
import game.core.Game;
import game.core.IPieceProvider;
import game.players.IPlayer;
import game.players.Vinni;

public class Attract extends Game {
    private static final IPieceProvider pieceProvider = AttracPiece::new;

    static {
		Game.addPlayer(Attract.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Attract.class, new Vinni(pieceProvider));
		Game.addPlayer(Attract.class, new BearPlayer(pieceProvider));
		Game.addPlayer(Attract.class, new PandaPlayer(pieceProvider));
    }

    public Attract() {
        initBoardDefault();

		board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(8, 8);
    }
}
