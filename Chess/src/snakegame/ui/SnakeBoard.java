package snakegame.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import snakegame.pieces.Board;
import snakegame.pieces.Directions;
import snakegame.pieces.Snake;
import org.eclipse.swt.graphics.Color;

import snakegame.pieces.Apple;


import java.util.Observable;
import java.util.Observer;

public class SnakeBoard extends Canvas implements PaintListener, KeyListener, Observer {
    protected int v_cells;
    protected int h_cells;
    protected Board board;
    protected Snake snake;
    private boolean isGameStarted = false;
    private boolean isGamePaused = false;

    public SnakeBoard(Composite parent, Board board, Snake snake) throws InterruptedException {
        super(parent, SWT.NONE);
        this.board = board;
        this.snake = snake;
        this.board.apple = new Apple(board);

        addPaintListener(this);
        addKeyListener(this);
        board.addObserver(this);
        //this.board.startGame(this.snake);
    }

    @Override
    public void update(Observable o, Object arg) {
        //update();
        redraw();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.keyCode == SWT.SPACE) {

            if(!isGameStarted){
                gameProcess(this.getParent().getDisplay());
                isGameStarted = true;
            }
            if(!this.snake.IsAlive){
                gameProcess(this.getParent().getDisplay());
                this.snake = new Snake(this.board);
                this.board.apple = new Apple(board);
                this.snake.IsAlive = true;
            }


        }
        if(e.keyCode == SWT.ARROW_UP){
            System.out.println("UP");
            this.snake.CurrentDirection = Directions.UP;
        }
        if(e.keyCode == SWT.ARROW_DOWN){
            System.out.println("DOWN");
            this.snake.CurrentDirection = Directions.DOWN;
        }
        if(e.keyCode == SWT.ARROW_LEFT){
            System.out.println("LEFT");
            this.snake.CurrentDirection = Directions.LEFT;
        }
        if(e.keyCode == SWT.ARROW_RIGHT){
            System.out.println("RIGHT");
            this.snake.CurrentDirection = Directions.RIGHT;
        }
    }
    public void gameProcess(Display display){
        Thread thread = new Thread () {
            @Override
            public void run() {
                while (snake.IsAlive) {
                    try {
                        display.asyncExec(runnable);
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
    }

    public Runnable runnable = () -> {
        try {
            //String name = Thread.currentThread().getName();
            //System.out.println("Foo " + name);
            this.board.startGame(this.snake);
            //TimeUnit.SECONDS.sleep(1);
            //System.out.println("Bar " + name);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    };

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void paintControl(PaintEvent e) {
        Rectangle clientArea = getClientArea();

        int squareWidth = getClientArea().width / board.getXLength();
        int squareHeight = getClientArea().height / board.getYLength();
        //e.gc.drawRectangle(0, 0, clientArea.width - 1, clientArea.height - 1);
        //e.gc.drawRectangle(5, 5, clientArea.width - 10, clientArea.height - 10);
        this.v_cells = board.getXLength();//
        this.h_cells = board.getYLength();//

        //for(int i = 0; i <board.getXLength();i++){
        //    for(int j = 0; j <board.getYLength();j++){
        //        drawSquare(e.gc, i , j, squareWidth,  squareHeight, new Color(getDisplay(), 0, 0, 0));
        //    }
        //}
        e.gc.drawText("Score "+ String.valueOf(this.snake.Score), getClientArea().width-70, 5);
        drawSnake(e,snake);
        drawApple(e);


    }

    protected void drawSquare(GC gc, int i, int j, int sw, int sh, Color color){
        int sx = i * sw;
        int sy = j * sh;
        gc.setBackground(color);
        gc.fillRectangle(sx, sy, sw, sh);
        //gc.drawRectangle(sx, sy, sw, sh);

    }

    protected void drawSnake(PaintEvent e, Snake snake){
        int squareWidth = (getClientArea().width )/ board.getXLength();
        int squareHeight = (getClientArea().height )/ board.getYLength();
        for(int i = 0;i<snake.body.size();i++){
            Color clr = new Color(getDisplay(), 0, 150, 0);
            drawSquare(e.gc, snake.body.get(i).v, snake.body.get(i).h, squareWidth,  squareHeight, clr);
        }
    }

    protected void drawApple(PaintEvent e){
        int squareWidth = (getClientArea().width)/ board.getXLength();
        int squareHeight = (getClientArea().height)/ board.getYLength();
        Color clr = new Color(getDisplay(), 200, 0, 0);
        if(this.board.apple.isAlive){
            drawSquare(e.gc, this.board.apple.square.v, this.board.apple.square.h, squareWidth,  squareHeight, clr);
        }
        else{
            for(;;){
                Apple app = new Apple(this.board);

                if(this.snake.body.contains(app.square)){
                    continue;
                }
                else {
                    this.board.apple.square.v = app.square.v;
                    this.board.apple.square.h = app.square.h;
                    break;
                }
            }
            drawSquare(e.gc, this.board.apple.square.v, this.board.apple.square.h, squareWidth,  squareHeight, clr);
            this.board.apple.isAlive = true;

        }



    }

    //public Image getPieceImage (Piece piece);


}
