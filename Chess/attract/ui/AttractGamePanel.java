package attract.ui;

import attract.Attract;
import game.ui.BoardSizePanel;
import game.ui.GamePanel;
import org.eclipse.swt.widgets.Composite;

/**
 * 吸引棋游戏面板
 * Attract game panel
 * Панель игры Притяжение
 *
 * 支持8x8和16x16两种棋盘尺寸
 * Supports both 8x8 and 16x16 board sizes
 * Поддерживает размеры доски 8x8 и 16x16
 */
public class AttractGamePanel extends GamePanel {

    /**
     * 默认构造函数，使用8x8棋盘
     * Default constructor, uses 8x8 board
     * Конструктор по умолчанию, использует доску 8x8
     */
    public AttractGamePanel(Composite parent) {
        this(parent, 8);
    }

    /**
     * 带棋盘尺寸参数的构造函数
     * Constructor with board size parameter
     * Конструктор с параметром размера доски
     *
     * @param parent    - 父组件 / parent composite / родительский компонент
     * @param boardSize - 棋盘尺寸 / board size / размер доски
     */
    public AttractGamePanel(Composite parent, int boardSize) {
        super(parent, new Attract(boardSize));

        AttractBoardPanel gameBoard = new AttractBoardPanel(this, game);
        insertSquares(gameBoard);

        // 添加棋盘尺寸选择面板
        // Add board size selection panel
        // Добавляем панель выбора размера доски
        int[][] sizes = {{8, 8}, {16, 16}};
        new BoardSizePanel(control, this, sizes);
    }
}