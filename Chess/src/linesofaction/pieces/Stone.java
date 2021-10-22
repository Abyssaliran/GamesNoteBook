package linesofaction.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.Square;
import linesofaction.moves.Capture;

public class Stone extends Piece {
    @Override
    public boolean isCorrectMove(Square... squares) {
        return false;
    }

    @Override
    public Move makeMove(Square... squares) {
//        return new SimpleMove(this, squares);
        return new Capture(this, squares);
    }
}
