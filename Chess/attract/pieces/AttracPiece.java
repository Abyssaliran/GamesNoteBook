package attract.pieces;

import attract.moves.AttractMove;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

public class AttracPiece extends Piece {
    public AttracPiece(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        return target.isEmpty();
    }

    @Override
    public Move makeMove(Square... squares) {
        return new AttractMove(this, squares);
    }

    @Override
    public String toString() {
        return "" + square;
    }
}
