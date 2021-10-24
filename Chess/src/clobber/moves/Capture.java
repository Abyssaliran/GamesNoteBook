package clobber.moves;

import clobber.pieces.Stone;
import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class Capture implements ITransferMove {
    private final Square source;
    private final Square target;
    private final Piece piece;
    private final Piece captured;

    public Capture(Stone stone, Square[] squares) {
        source = squares[0];
        target = squares[1];

        piece = source.getPiece();
        captured = target.getPiece();
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public void doMove() throws GameOver {
        // TODO Романовская Юлия
    }

    @Override
    public void undoMove() {
        // TODO Романовская Юлия
    }

    @Override
    public Square getSource() {
        return source;
    }

    @Override
    public Square getTarget() {
        return target;
    }
}
