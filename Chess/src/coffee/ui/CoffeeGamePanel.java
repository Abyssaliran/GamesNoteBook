package coffee.ui;

import coffee.Coffee;
import game.ui.GamePanel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import renju.Renju;
import renju.ui.RenjuBoardPanel;

public class CoffeeGamePanel extends GamePanel {

    public CoffeeGamePanel(Composite parent) {
        super(parent, new Coffee());

        CoffeeBoardPanel gameBoard = new CoffeeBoardPanel(this, game);
        insertSquares(gameBoard);
    }
}
