package snakegame.pieces;

import java.util.*;
public class Snake{

    public ArrayList<Square> body = new ArrayList<>();
    public Directions CurrentDirection = Directions.RIGHT;
    public Board board;
    public boolean IsAlive = true;
    public int Score;

    public Snake(Board board){
        this.board = board;
        for (int i = 0; i<3;i++){
            body.add(new Square(board,board.getXLength()/2 - i,board.getYLength()/2));
        }
        this.Score = 0;

    }

    public void move(){

        int prev_x = this.body.get(0).v;
        int prev_y = this.body.get(0).h;
        this.body.remove(this.body.size()-1);

        if(this.CurrentDirection == Directions.UP){
            if(prev_y < 0){
                Square sq = new Square(this.board, prev_x, 30);
                lookForApple(sq);
                moveSnake(sq);
            }
            else if(prev_y>30){
                Square sq = new Square(this.board, prev_x, 0);
                lookForApple(sq);
                moveSnake(sq);
            }
            else{
                Square sq = new Square(this.board, prev_x, prev_y - 1);
                lookForApple(sq);
                moveSnake(sq);
            }
        }

        if(this.CurrentDirection == Directions.DOWN){
            if(prev_y < 0){
                Square sq = new Square(this.board, prev_x, 30);
                lookForApple(sq);
                moveSnake(sq);
            }
            else if(prev_y>30){
                Square sq = new Square(this.board, prev_x, 0);
                lookForApple(sq);
                moveSnake(sq);
            }
            else{
                Square sq = new Square(this.board, prev_x, prev_y + 1);
                lookForApple(sq);
                moveSnake(sq);
            }

        }

        if(this.CurrentDirection == Directions.LEFT){
            if(prev_x < 0){
                Square sq = new Square(this.board, 30, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
            else if(prev_x>30){
                Square sq = new Square(this.board, 0, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
            else{
                Square sq = new Square(this.board, prev_x - 1, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }

        }

        if(this.CurrentDirection == Directions.RIGHT){
            if(prev_x < 0){
                Square sq = new Square(this.board, 30, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
            else if(prev_x>30){
                Square sq = new Square(this.board, 0, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
            else{
                Square sq = new Square(this.board, prev_x + 1, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
        }

    }

    private void lookForApple(Square sq){
        if(sq.equals(this.board.apple.square)){
            this.board.apple.isAlive = false;

            int tail_x = this.body.get(this.body.size()-1).h;
            int tail_y = this.body.get(this.body.size()-1).v;
            Square tail = new Square(this.board, tail_x, tail_y);

            if(this.CurrentDirection == Directions.UP) {
                tail.h = tail_x;
                tail.v = tail_y - 1;
            }
            else if(this.CurrentDirection == Directions.DOWN){
                tail.h = tail_x;
                tail.v = tail_y + 1;
            }
            else if(this.CurrentDirection == Directions.RIGHT){
                tail.h = tail_x + 1;
                tail.v = tail_y;
            }
            else if(this.CurrentDirection == Directions.LEFT){
                tail.h = tail_x - 1;
                tail.v = tail_y;
            }
            this.Score += 1;
            this.body.add(this.body.size(), tail);
        }
    }

    private void moveSnake(Square sq){
        if(checkCollision(sq)){
            this.IsAlive = false;
        }
        else {
            this.body.add(0, sq);
        }
    }

    private boolean checkCollision(Square square){
        if(body.contains(square)){
            return true;

        }
        else{
            return false;
        }
    }

}
