package attract.players;

import attract.ui.images.AttractImages;
import game.core.*;
import game.core.moves.IPutMove;
import org.eclipse.swt.graphics.Image;

import java.util.*;

/**
 * 熊玩家 - 使用贪心算法的智能AI玩家
 * Bear Player - Smart AI player using Greedy Algorithm
 * Медведь - Умный AI-игрок, использующий жадный алгоритм
 *
 * 贪心算法简单高效，每步选择当前最优的移动
 * Greedy algorithm is simple and efficient, choosing the best move at each step
 * Жадный алгоритм прост и эффективен, выбирает лучший ход на каждом шаге
 *
 * @author Фань Чжаньхун
 */
public class BearPlayer extends AttractPlayer {

    // ==========================================
    // === 基本信息 / Basic Info / Основная информация ===
    // ==========================================

    public BearPlayer(IPieceProvider pieceProvider) {
        super(pieceProvider);
    }

    @Override
    public String getName() {
        return "Медведь";
    }

    @Override
    public String getAuthorName() {
        return "Фань Чжаньхун";
    }

    @Override
    public boolean isBlackPlayer() {
        return false;
    }

    @Override
    public Image getImage() {
        return AttractImages.bearImage;
    }

    @Override
    public String getInfo() {
        return "живет в России";
    }

    // ==========================================
    // === 贪心算法实现 / Greedy Algorithm / Жадный алгоритм ===
    // ==========================================

    /**
     * 计算移动权重 - 使用贪心策略评估移动质量
     * Calculate move weight - evaluate move quality using greedy strategy
     * Вычисление веса хода - оценка качества хода с помощью жадной стратегии
     *
     * 获胜规则：一个棋子的4个对角邻居全是对方棋子时，该棋子为"获胜棋子"，拥有者得1分
     * Winning rule: A piece whose all 4 diagonal neighbors are opponent's pieces is a "winning piece", owner scores 1 point
     * Правило победы: Фигура, у которой все 4 диагональных соседа — фигуры противника, является "выигрышной", владелец получает 1 очко
     *
     * @param move - 要评估的移动 / move to evaluate / ход для оценки
     * @return 移动权重 / move weight / вес хода
     */
    @Override
    protected int getWeight(Move move) {
        // 将Move转换为IPutMove以获取目标格子
        // Cast Move to IPutMove to get target square
        // Преобразуем Move в IPutMove для получения целевой клетки
        if (!(move instanceof IPutMove)) {
            return 0;
        }

        IPutMove putMove = (IPutMove) move;
        Square target = putMove.getTarget();
        Board board = target.getBoard();

        int score = 0;

        // BearPlayer是白方
        // BearPlayer is white
        // BearPlayer играет белыми
        PieceColor myColor = PieceColor.WHITE;
        PieceColor opponentColor = PieceColor.BLACK;

        // 策略1: 让己方棋子被对方包围形成"获胜棋子"
        // Strategy 1: Make my pieces surrounded by opponent to form "winning pieces"
        // Стратегия 1: Сделать свои фигуры окружёнными противником для формирования "выигрышных фигур"
        score += evaluateWinningPieceScore(target, board, myColor, opponentColor);

        // 策略2: 阻止对手的棋子被己方包围（阻止对手形成获胜棋子）
        // Strategy 2: Prevent opponent's pieces from being surrounded by my pieces
        // Стратегия 2: Не давать фигурам противника быть окружёнными моими фигурами
        score += evaluateBlockingScore(target, board, myColor, opponentColor);

        // 策略3: 中心控制
        // Strategy 3: Center control
        // Стратегия 3: Контроль центра
        score += evaluateCenterScore(target, board);

        return score;
    }

    /**
     * 评估己方棋子形成"获胜棋子"的得分
     * Evaluate score for my pieces becoming "winning pieces"
     * Оценка очков за формирование "выигрышных фигур"
     *
     * 规则：如果己方棋子的4个对角邻居全是对方棋子，该棋子成为"获胜棋子"，己方得1分
     * Rule: If all 4 diagonal neighbors of my piece are opponent's pieces, it becomes a "winning piece", I score 1 point
     * Правило: Если все 4 диагональных соседа моей фигуры — фигуры противника, она становится "выигрышной", я получаю 1 очко
     *
     * @param target        - 目标格子 / target square / целевая клетка
     * @param board         - 棋盘 / board / доска
     * @param myColor       - 己方颜色 / my color / мой цвет
     * @param opponentColor - 对方颜色 / opponent color / цвет противника
     * @return 得分评估 / score evaluation / оценка очков
     */
    private int evaluateWinningPieceScore(Square target, Board board, PieceColor myColor, PieceColor opponentColor) {
        int score = 0;
        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        // 检查现有己方棋子能否因这一步而被对方完全包围（形成获胜棋子）
        // Check if existing my pieces can be fully surrounded by opponent due to this move (forming winning piece)
        // Проверяем, могут ли мои существующие фигуры быть полностью окружены противником благодаря этому ходу
        for (int v = 1; v < board.nV - 1; v++) {
            for (int h = 1; h < board.nH - 1; h++) {
                Square sq = board.getSquare(v, h);
                // 只检查己方棋子
                // Only check my pieces
                // Проверяем только свои фигуры
                if (!sq.isEmpty() && sq.getPiece().getColor() == myColor) {
                    int opponentCount = 0;
                    boolean targetIsNeighbor = false;

                    // 检查4个对角邻居
                    // Check 4 diagonal neighbors
                    // Проверяем 4 диагональных соседа
                    for (int[] dir : directions) {
                        int checkV = v + dir[0];
                        int checkH = h + dir[1];

                        if (board.onBoard(checkV, checkH)) {
                            if (checkV == target.v && checkH == target.h) {
                                // 目标位置将放置己方棋子，不是对方棋子
                                // Target position will have my piece, not opponent's
                                // Целевая позиция будет занята моей фигурой, не противника
                                targetIsNeighbor = true;
                            } else {
                                Square neighbor = board.getSquare(checkV, checkH);
                                if (!neighbor.isEmpty() && neighbor.getPiece().getColor() == opponentColor) {
                                    opponentCount++;
                                }
                            }
                        }
                    }

                    // 如果4个对角都是对方棋子，形成获胜棋子
                    // If all 4 diagonals are opponent's pieces, forming winning piece
                    // Если все 4 диагонали — фигуры противника, формируется выигрышная фигура
                    if (opponentCount == 4) {
                        score += 1000;  // 该己方棋子已经是获胜棋子
                    } else if (opponentCount == 3 && !targetIsNeighbor) {
                        score += 100;   // 差一步成为获胜棋子
                    }
                }
            }
        }

        // 如果这步棋放置后，该棋子本身能被对方包围
        // If after placing this piece, it can be surrounded by opponent
        // Если после размещения этой фигуры она может быть окружена противником
        if (target.v >= 1 && target.v < board.nV - 1 &&
                target.h >= 1 && target.h < board.nH - 1) {
            int opponentCount = 0;
            for (int[] dir : directions) {
                int checkV = target.v + dir[0];
                int checkH = target.h + dir[1];
                if (board.onBoard(checkV, checkH)) {
                    Square neighbor = board.getSquare(checkV, checkH);
                    if (!neighbor.isEmpty() && neighbor.getPiece().getColor() == opponentColor) {
                        opponentCount++;
                    }
                }
            }
            if (opponentCount == 4) {
                score += 1500;  // 直接形成获胜棋子！
            } else if (opponentCount == 3) {
                score += 80;    // 潜在获胜棋子
            }
        }

        return score;
    }

    /**
     * 评估阻止对手形成"获胜棋子"的得分
     * Evaluate score for blocking opponent's "winning pieces"
     * Оценка очков за блокирование "выигрышных фигур" противника
     *
     * 规则：如果对方棋子的4个对角邻居全是己方棋子，则对方形成获胜棋子，需要阻止
     * Rule: If all 4 diagonal neighbors of opponent's piece are my pieces, opponent forms winning piece, need to block
     * Правило: Если все 4 диагональных соседа фигуры противника — мои фигуры, противник формирует выигрышную фигуру, нужно блокировать
     *
     * @param target        - 目标格子 / target square / целевая клетка
     * @param board         - 棋盘 / board / доска
     * @param myColor       - 己方颜色 / my color / мой цвет
     * @param opponentColor - 对方颜色 / opponent color / цвет противника
     * @return 阻止得分 / blocking score / балл блокирования
     */
    private int evaluateBlockingScore(Square target, Board board, PieceColor myColor, PieceColor opponentColor) {
        int score = 0;
        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        // 检查对方棋子是否会因这步棋而被己方完全包围（形成对方获胜棋子）
        // Check if opponent's pieces will be fully surrounded by my pieces due to this move
        // Проверяем, будут ли фигуры противника полностью окружены моими фигурами благодаря этому ходу
        for (int v = 1; v < board.nV - 1; v++) {
            for (int h = 1; h < board.nH - 1; h++) {
                Square sq = board.getSquare(v, h);
                // 只检查对方棋子
                // Only check opponent's pieces
                // Проверяем только фигуры противника
                if (!sq.isEmpty() && sq.getPiece().getColor() == opponentColor) {
                    int myPieceCount = 0;
                    boolean targetIsNeighbor = false;

                    for (int[] dir : directions) {
                        int checkV = v + dir[0];
                        int checkH = h + dir[1];

                        if (board.onBoard(checkV, checkH)) {
                            if (checkV == target.v && checkH == target.h) {
                                // 目标位置将放置己方棋子
                                // Target position will have my piece
                                // Целевая позиция будет занята моей фигурой
                                targetIsNeighbor = true;
                                myPieceCount++;
                            } else {
                                Square neighbor = board.getSquare(checkV, checkH);
                                if (!neighbor.isEmpty() && neighbor.getPiece().getColor() == myColor) {
                                    myPieceCount++;
                                }
                            }
                        }
                    }

                    // 如果这步棋会让对方棋子被己方完全包围，对方得分，扣分！
                    // If this move will make opponent's piece fully surrounded by my pieces, opponent scores, penalty!
                    // Если этот ход сделает фигуру противника полностью окружённой моими фигурами, противник получит очко, штраф!
                    if (myPieceCount == 4 && targetIsNeighbor) {
                        score -= 800;  // 不应该让对方形成获胜棋子
                    } else if (myPieceCount == 3 && targetIsNeighbor) {
                        score -= 50;   // 危险的位置
                    }
                }
            }
        }

        return score;
    }

    /**
     * 评估中心控制得分
     * Evaluate center control score
     * Оценка очков за контроль центра
     *
     * @param target - 目标格子 / target square / целевая клетка
     * @param board  - 棋盘 / board / доска
     * @return 中心控制分数 / center control score / балл контроля центра
     */
    private int evaluateCenterScore(Square target, Board board) {
        int centerV = board.nV / 2;
        int centerH = board.nH / 2;

        // 计算到中心的距离
        // Calculate distance to center
        // Вычисляем расстояние до центра
        int distanceToCenter = Math.abs(target.v - centerV) + Math.abs(target.h - centerH);

        // 边缘惩罚
        // Edge penalty
        // Штраф за края
        int penalty = 0;
        if (target.v == 0 || target.v == board.nV - 1 ||
                target.h == 0 || target.h == board.nH - 1) {
            penalty = -10;
        }

        return (board.nV - distanceToCenter) * 2 + penalty;
    }
}
