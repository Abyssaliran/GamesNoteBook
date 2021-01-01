package fisher.piece;

import chess.moves.Capture;
import fisher.moves.Castling;
import chess.moves.SimpleMove;
import game.core.*;
import chess.pieces.Rook;

import java.util.List;

public class King extends chess.pieces.King {
    public King(Square square, PieceColor color) {
        super(square, color);
    }

    public King() {

    }

    boolean ismoved = false;

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        int kingV = square.v;
        int kingH = square.h;

        int dv = target.v - kingV;
        int dh = target.h - kingH;

        Board board = square.getBoard();

        if (square.isNear(target) && (target.isEmpty() || target.getPiece().getColor() != square.getPiece().getColor())) {
            ismoved = true;
            return true; //Если идем обычным ходом либо на пустую клетку, либо на клетку с фигурой противника
        }

        if (target.v == 2) {
            //Возможно длинная рокировка
            if (!ismoved) { //Короля не двигали
                if (dv <= -2) {
                    if (target.isEmpty()) {
                        Piece rook = new Rook();
                        List<Piece> l = getFriends();
                        boolean foundRook = false;
                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i) instanceof Rook && l.get(i).square.v < kingV) { //Ищем ладью слева от короля.
                                foundRook = true;
                                rook = l.get(i);
                            }
                        }
                        if (!foundRook) { //Нет ладьи слева от короля.
                            return false;
                        }
                        int rookV = rook.square.v, rookH = rook.square.h;
                        if (rookH == kingH) { // Ладья и король на одном ряду.
                            for (int i = rookV + 1; i < kingV; i++) {
                                if (!board.isEmpty(i, kingH)) {
                                    return false; //Есть фигуры между королем и ладьей.
                                }
                            }
                            ismoved = true;
                            return true;
                        }
                        return false;
                    }
                } else if (dv == -1) {
                    if (board.getSquare(0, kingH).getPiece() instanceof Rook && board.getSquare(0, kingH).getPiece().getColor() == getColor() && target.isEmpty()
                            || board.getSquare(1, kingH).getPiece() instanceof Rook && board.getSquare(1, kingH).getPiece().getColor() == getColor()) {
                        return true;
                    }

                } else if (dv == 0) {
                    //Рокировка, но король остается на месте. Ладья должна быть на 0 столбце
                    if (board.getSquare(0, kingH).getPiece() instanceof Rook && board.getSquare(0, kingH).getPiece().getColor() == getColor()) {
                        return true;
                    }
                }
            }
        }

        if (target.v == 6) {
            //Возможно короткая рокировка
            if (!ismoved) { //Короля не двигали
                if (dv >= 2) {
                    if (target.isEmpty()) {
                        Piece rook = new Rook();
                        List<Piece> l = getFriends();
                        boolean foundRook = false;
                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i) instanceof Rook && l.get(i).square.v > kingV) { //Ищем ладью справа от короля.
                                foundRook = true;
                                rook = l.get(i);
                            }
                        }
                        if (!foundRook) { //Нет ладьи слева от короля.
                            return false;
                        }
                        int rookV = rook.square.v, rookH = rook.square.h;
                        if (rookH == kingH) { // Ладья и король на одном ряду.
                            for (int i = kingV + 1; i < rookV; i++) {
                                if (!board.isEmpty(i, kingH)) {
                                    return false; //Есть фигуры между королем и ладьей.
                                }
                            }
                            ismoved = true;
                            return true;
                        }
                        return false;
                    }
                } else if (dv == 1) {
                    if (board.getSquare(7, kingH).getPiece() instanceof Rook && board.getSquare(7, kingH).getPiece().getColor() == getColor() && target.isEmpty()
                            || board.getSquare(6, kingH).getPiece() instanceof Rook && board.getSquare(6, kingH).getPiece().getColor() == getColor()) {
                        return true;
                    }

                } else if (dv == 0) {
                    //Рокировка, но король остается на месте. Ладья должна быть на 7 столбце
                    if (board.getSquare(7, kingH).getPiece() instanceof Rook && board.getSquare(7, kingH).getPiece().getColor() == getColor()) {
                        return true;
                    }
                }
            }
        }

        if (square.isNear(target)) {
            return true;
        }

        return false;
    }

    @Override
    public Move makeMove(Square... squares) {
        Square source = squares[0];
        Square target = squares[1];

        int dv = Math.abs(target.v - source.v);
        if ((target.v == 2 || target.v == 6) && target.h == source.h) {
            return new Castling(squares);
        }

        if (!target.isEmpty()) {
            return new Capture(squares);
        }

        return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "K";
    }
}
