package rabbit.ui;

import game.ui.GamePanel;
import lines.Lines;
import lines.ui.LinesBoardPanel;
import org.eclipse.swt.widgets.Composite;
import rabbit.RabbitWolfs;

public class RabbitGamePanel extends GamePanel {

    public RabbitGamePanel(Composite parent) {
        super(parent, new RabbitWolfs());

        RabbitBoardPanel gameBoard = new RabbitBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}
