package attract;

import attract.pieces.AttracPiece;
import attract.players.BearPlayer;
import attract.players.PandaPlayer;
import game.core.Game;
import game.core.IPieceProvider;
import game.players.IPlayer;
import game.players.Vinni;

/**
 * 吸引棋游戏类
 * Attract game class
 * Класс игры Притяжение
 *
 * 支持多种棋盘尺寸：8x8（标准）和16x16（大棋盘）
 * Supports multiple board sizes: 8x8 (standard) and 16x16 (large board)
 * Поддерживает различные размеры доски: 8x8 (стандартный) и 16x16 (большая доска)
 */
public class Attract extends Game {
    private static final IPieceProvider pieceProvider = AttracPiece::new;

    /**
     * 当前棋盘尺寸
     * Current board size
     * Текущий размер доски
     */
    private int boardSize;

    static {
        Game.addPlayer(Attract.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Attract.class, new Vinni(pieceProvider));
        Game.addPlayer(Attract.class, new BearPlayer(pieceProvider));
        Game.addPlayer(Attract.class, new PandaPlayer(pieceProvider));
    }

    /**
     * 默认构造函数，使用8x8棋盘
     * Default constructor, uses 8x8 board
     * Конструктор по умолчанию, использует доску 8x8
     */
    public Attract() {
        this(8);
    }

    /**
     * 带棋盘尺寸参数的构造函数
     * Constructor with board size parameter
     * Конструктор с параметром размера доски
     *
     * @param boardSize - 棋盘尺寸 / board size / размер доски
     */
    public Attract(int boardSize) {
        this.boardSize = boardSize;
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));
    }

    /**
     * 初始化默认棋盘
     * Initialize default board
     * Инициализация доски по умолчанию
     *
     * 注意：如果棋盘已存在且尺寸有效，使用当前棋盘尺寸；否则使用构造时指定的尺寸
     * Note: If board exists and has valid size, use current board size; otherwise use size specified in constructor
     * Примечание: Если доска существует и имеет допустимый размер, используем текущий размер; иначе используем размер из конструктора
     */
    @Override
    public void initBoardDefault() {
        // 如果棋盘已经存在且尺寸有效，使用当前棋盘的尺寸（支持resizeBoard后的Start按钮）
        // If board already exists and has valid size, use current board size (support Start button after resizeBoard)
        // Если доска уже существует и имеет допустимый размер, используем текущий размер (поддержка кнопки Старт после resizeBoard)
        int size = (board != null && board.nV > 0) ? board.nV : boardSize;
        super.initBoard(size, size);
    }
}
