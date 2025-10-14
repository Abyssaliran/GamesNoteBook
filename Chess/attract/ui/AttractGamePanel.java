package attract.ui;

import attract.Attract;
import game.ui.GamePanel;
import org.eclipse.swt.widgets.Composite;

public class AttractGamePanel extends GamePanel {
    public AttractGamePanel(Composite parent) {
        super(parent, new Attract());

        AttractBoardPanel gameBoard = new AttractBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}