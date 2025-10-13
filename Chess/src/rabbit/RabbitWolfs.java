package rabbit;

import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import rabbit.pieces.Rabbit;
import rabbit.pieces.Wolf;
import rabbit.players.*;

/**
 * <a href="https://www.iggamecenter.com/info/ru/foxh.html">Правила</a>
 */
public class RabbitWolfs extends Game {
    private static final int BOARD_SIZE = 8;


    static {
        Game.addPlayer(RabbitWolfs.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(RabbitWolfs.class, new Neznaika());
        Game.addPlayer(RabbitWolfs.class, new WolfPlayer());
        Game.addPlayer(RabbitWolfs.class, new RabbitPlayer());
        Game.addPlayer(RabbitWolfs.class, new CatPlayer());
        Game.addPlayer(RabbitWolfs.class, new MousePlayer());
        Game.addPlayer(RabbitWolfs.class, new SnakePlayer());
        Game.addPlayer(RabbitWolfs.class, new FrogPlayer());
        Game.addPlayer(RabbitWolfs.class, new FoxPlayer());
        Game.addPlayer(RabbitWolfs.class, new HedgehogPlayer());
    }

    public RabbitWolfs() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setWhitePlayer(new Neznaika());
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(BOARD_SIZE, BOARD_SIZE);

        new Rabbit(board.getSquare(3, 4), PieceColor.WHITE);

        new Wolf(board.getSquare(1, 0), PieceColor.BLACK);
        new Wolf(board.getSquare(3, 0), PieceColor.BLACK);
        new Wolf(board.getSquare(5, 0), PieceColor.BLACK);
        new Wolf(board.getSquare(7, 0), PieceColor.BLACK);
    }
}
