package attract.moves;

import attract.pieces.AttracPiece;
import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.IPutMove;

import java.util.ArrayList;

public class AttractMove implements IPutMove {
    private final AttracPiece piece;
    private final Square target;

    ArrayList<SimpleMove> attractMoves  = new ArrayList<>();

    public AttractMove(AttracPiece piece, Square[] squares) {
        this.target = squares[0];
        this.piece = piece;
    }

    @Override
    public Square getTarget() {
        return target;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public void doMove() throws GameOver {
        target.setPiece(piece);
    }

    @Override
    public void undoMove() {
        target.removePiece();
    }

    public String toString() {
        return "" + target;
    }
}
