package clobber;

import game.core.Game;
import game.players.IPlayer;
import game.players.Neznaika;

/**
 * TODO Романовская Юлия
 * Правила игры:
 * http://www.iggamecenter.com/info/ru/clobber.html
 */
public class Clobber extends Game {
    static {
        addPlayer(Clobber.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Clobber.class, new Neznaika());
    }

    public Clobber() {
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
