package snakegame.pieces;

import java.util.Observable;

public class Board extends Observable {

    private int X;
    private int Y;
    private Square [ ][ ] squares;
    public Apple apple;

    public Board(int nV, int nH) {
        this.X = nV;
        this.Y = nH;
        squares = new Square[nV][nH];
        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++)
                squares[v][h] = new Square(this, v, h);
    }
    public boolean isEmpty(int v, int h) {
        return getSquare(v, h).piece == null;
    }
    public Square getSquare(int v, int h) {
        return squares[v][h];
    }
    public int getXLength(){
        return this.X;
    }
    public int getYLength(){
        return this.Y;
    }

    public void setBoardChanged ( ) {
        super.setChanged();
        super.notifyObservers();
    }

    public void startGame(Snake snake) throws InterruptedException {

            //Thread.sleep(500);
            //TimeUnit.SECONDS.sleep(1);
            snake.move();
            System.out.println(String.valueOf(snake.body.get(0).v) + " " + String.valueOf(snake.body.get(0).h));
            setBoardChanged();

    }
}
