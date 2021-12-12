package snakegame.pieces;

public class Square {
    public Piece piece;
    public Board board;
    public int v;
    public int h;
    public Square (Board board, int v, int h) {
        this.board = board;
        this.v = v;
        this.h = h;
    }
    public Piece getPiece() {
        return piece;
    }
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        Square square = (Square)o;
        // If the object is compared with itself then return true
        if (square == this) {
            return true;
        }
        // If coords in squares are equal return true
        else if(this.h == square.h & this.v == square.v){
            return true;
        }
        else{
            return false;
        }

    }
}

