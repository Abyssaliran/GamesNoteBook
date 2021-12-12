package snakegame;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import snakegame.pieces.Board;
import snakegame.pieces.Snake;
import snakegame.ui.SnakeBoard;
import snakegame.ui.SnakeImages;


public class SnakeGame {
    public static void main(String[] args) throws InterruptedException {
        final Display display = new Display();
        SnakeImages.load(display);

        final Shell shell = new Shell(display);

        shell.setSize(600, 500);

        shell.setText("Snake Game");
        shell.setImage(SnakeImages.iconSnakeNotebook);

        FillLayout layout = new FillLayout();
        shell.setLayout(layout);
        Board br = new Board(30,30);

        //Label label = new Label(shell, SWT.NONE);
        //label.setText("My Label");

        Snake snake = new Snake(br);

        SnakeBoard sb = new SnakeBoard(shell, br, snake);


        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    } // main
}
