package chess.players;

import chess.moves.Capture;
import game.core.Move;

public class TinTack extends ChessPlayer {
    @Override
    public String getName() {
        return "Кнопочка";
    }

    @Override
    public String getAuthorName() {
        return "Ананьева Мария";
    }

    @Override
    protected int getWeight(Move m) {
        return 0;
    }
}
