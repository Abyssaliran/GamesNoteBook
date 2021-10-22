package linesofaction;

import game.core.Game;
import game.players.IPlayer;
import game.players.Neznaika;

public class LinesOfAction extends Game {
    static {
        addPlayer(LinesOfAction.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(LinesOfAction.class, new Neznaika());
    }

    public LinesOfAction() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(8, 8);

        // TODO расставить фигуры
    }
}
