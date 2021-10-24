package clobber.pieces;

import clobber.moves.Capture;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

public class Stone extends Piece {
    @Override
    public boolean isCorrectMove(Square... squares) {
        return false;
    }

    @Override
    public Move makeMove(Square... squares) {
        return new Capture(this, squares);
    }
}