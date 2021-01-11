package fisher.players;

import chess.pieces.*;
import fisher.piece.King;
import game.core.*;
import game.players.MovePiecePlayer;

import java.util.Collections;
import java.util.List;


public class Magnus extends MovePiecePlayer {

    public class myPair {
        Move move;
        Integer integer;

        myPair(Move m, int i) {
            move = m;
            integer = i;
        }
    }

    @Override
    public String getName() {
        return "Магнус";
    }

    @Override
    public String getAuthorName() {
        return "Голяков К.С.";
    }

    @Override
    public void doMove (Board board, PieceColor color) throws GameOver {
        Move m = minmax(board, 5, -10000000, 10000000, color, true).move;
        try{m.doMove();}
        catch (GameOver e) {
            board.history.addMove(m);
            board.history.setResult(e.result);
            board.setBoardChanged();
            throw new GameOver(e.result);
        }
        board.history.addMove(m);
        board.setBoardChanged();
    }

    public myPair minmax (Board board, int depth, int alpha, int beta, PieceColor color, boolean maximizing) {
        if (depth == 0) {
            return new myPair(null, score(board, color));
        }
        int res;
        Move resMove = null;
        if (maximizing) {
            res = -10000000;
            List<Move> moves = getCorrectMoves(board, color);
            Collections.shuffle(moves);
            for (Move m: moves) {
                boolean isGameOver = false;
                try{m.doMove();}
                catch (GameOver e) {
                    isGameOver = true;
                    System.out.println("Caught a Game Over");
                }
                myPair tmp;
                if (color == PieceColor.BLACK) {
                    tmp = minmax(board, depth - 1, -beta, -alpha, PieceColor.WHITE, false);
                } else {
                    tmp = minmax(board, depth - 1, -beta, -alpha, PieceColor.BLACK, false);
                }
                int eval = -tmp.integer;
                m.undoMove();
                if (isGameOver) {
                    break;
                }
                if (eval > res) {
                    resMove = m;
                    res = eval;
                }
                alpha = Math.max(alpha, res);
                if (beta <= alpha) {
                    break;
                }
            }
        } else {
            res = 10000000;
            List<Move> moves = getCorrectMoves(board, color);
            Collections.shuffle(moves);
            for (Move m: moves) {
                try{m.doMove();}
                catch (GameOver e) {
                    System.out.println("Caught a Game Over");
                }
                myPair tmp;
                if (color == PieceColor.BLACK) {
                    tmp = minmax(board, depth - 1, -beta, -alpha, PieceColor.WHITE, true);
                } else {
                    tmp = minmax(board, depth - 1, -beta, -alpha, PieceColor.BLACK, true);
                }
                int eval = tmp.integer;
                m.undoMove();
                if (eval < res) {
                    res = eval;
                    resMove = m;
                }
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return new myPair(resMove, res);
    }

    public int score(Board board, PieceColor color) {
        int res = 0;
        int[] values = {100, 305, 333, 563, 950, 100000};
        for (Piece p: board.getAllPieces()) {
            int score;
            if (p instanceof Pawn) {
                score = values[0];
            } else if (p instanceof Knight) {
                score = values[1];
            } else if (p instanceof Bishop) {
                score = values[2];
            } else if (p instanceof Rook) {
                score = values[3];
            } else if (p instanceof Queen) {
                score = values[4];
            } else if (p instanceof King) {
                score = values[5];
            } else {
                score = 0;
            }
            if (p.getColor() == color) {
                res += score;
            } else {
                res -= score;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return getName();
    }
}