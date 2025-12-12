package attract.players;

import attract.ui.images.AttractImages;
import game.core.*;
import game.core.moves.IPutMove;
import org.eclipse.swt.graphics.Image;

/**
 * 熊猫玩家 - 使用Minimax（极小极大）算法的智能AI玩家
 * Panda Player - Smart AI player using Minimax algorithm
 * Панда - Умный AI-игрок, использующий алгоритм Минимакс
 *
 * Minimax是经典的博弈树搜索算法，适用于双人零和游戏
 * Minimax is a classic game tree search algorithm, suitable for two-player zero-sum games
 * Минимакс — классический алгоритм поиска по дереву игр для игр с нулевой суммой
 *
 * @author Юй Сыцзэ
 */
public class PandaPlayer extends AttractPlayer {

    // ==========================================
    // === 基本信息 / Basic Info / Основная информация ===
    // ==========================================

    public PandaPlayer(IPieceProvider pieceProvider) {
        super(pieceProvider);
    }

    @Override
    public String getName() {
        return "Панда";
    }

    @Override
    public String getAuthorName() {
        return "Юй Сыцзэ";
    }

    @Override
    public boolean isWhitePlayer() {
        return false;
    }

    @Override
    public Image getImage() {
        return AttractImages.pandaImage;
    }

    @Override
    public String getInfo() {
        return "живет в Китае";
    }

    // ==========================================
    // === Minimax算法实现 / Minimax Algorithm / Алгоритм Минимакс ===
    // ==========================================

    /**
     * 计算移动权重 - 使用Minimax策略评估移动质量
     * Calculate move weight - evaluate move quality using Minimax strategy
     * Вычисление веса хода - оценка качества хода с помощью стратегии Минимакс
     *
     * 获胜规则：遍历每个非边缘格子，如果该格子的4个对角都是对方棋子，则对方得1分
     * Winning rule: For each non-edge square, if all 4 diagonals are opponent's pieces, opponent scores 1 point
     * Правило победы: Для каждой не-крайней клетки, если все 4 диагонали — фигуры противника, противник получает 1 очко
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

        // PandaPlayer是黑方
        // PandaPlayer is black
        // PandaPlayer играет черными
        PieceColor myColor = PieceColor.BLACK;
        PieceColor opponentColor = PieceColor.WHITE;

        // 策略1: 最大化己方得分
        // Strategy 1: Maximize my score
        // Стратегия 1: Максимизация своих очков
        score += evaluateOffensiveScore(target, board, myColor);

        // 策略2: 最小化对手得分（防守）
        // Strategy 2: Minimize opponent's score (defense)
        // Стратегия 2: Минимизация очков противника (защита)
        score += evaluateDefensiveScore(target, board, opponentColor);

        // 策略3: 位置策略
        // Strategy 3: Position strategy
        // Стратегия 3: Позиционная стратегия
        score += evaluatePositionScore(target, board);

        return score;
    }

    /**
     * 评估进攻得分 - 形成包围阵型
     * Evaluate offensive score - forming surrounding patterns
     * Оценка атакующих очков - формирование окружающих комбинаций
     *
     * @param target  - 目标格子 / target square / целевая клетка
     * @param board   - 棋盘 / board / доска
     * @param myColor - 己方颜色 / my color / мой цвет
     * @return 进攻得分 / offensive score / атакующие очки
     */
    private int evaluateOffensiveScore(Square target, Board board, PieceColor myColor) {
        int score = 0;
        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        // 检查该位置能参与哪些包围阵型
        // Check which surrounding patterns this position can participate in
        // Проверяем, в каких окружающих комбинациях может участвовать эта позиция
        for (int[] dir : directions) {
            int centerV = target.v + dir[0];
            int centerH = target.h + dir[1];

            if (centerV >= 1 && centerV < board.nV - 1 &&
                    centerH >= 1 && centerH < board.nH - 1) {

                int myPieceCount = 0;
                int emptyCount = 0;

                for (int[] checkDir : directions) {
                    int checkV = centerV + checkDir[0];
                    int checkH = centerH + checkDir[1];

                    if (board.onBoard(checkV, checkH)) {
                        if (checkV == target.v && checkH == target.h) {
                            myPieceCount++;
                        } else {
                            Square sq = board.getSquare(checkV, checkH);
                            if (sq.isEmpty()) {
                                emptyCount++;
                            } else if (sq.getPiece().getColor() == myColor) {
                                myPieceCount++;
                            }
                        }
                    }
                }

                // 根据己方棋子数量给分（Minimax思想：最大化己方优势）
                // Score based on my piece count (Minimax: maximize own advantage)
                // Оцениваем на основе количества своих фигур (Минимакс: максимизация своего преимущества)
                if (myPieceCount == 4) {
                    score += 2000;  // 完成包围 / complete surrounding / завершение окружения
                } else if (myPieceCount == 3 && emptyCount == 1) {
                    score += 200;   // 差一步 / one step away / один шаг
                } else if (myPieceCount == 3) {
                    score += 150;
                } else if (myPieceCount == 2) {
                    score += 30;
                }
            }
        }

        return score;
    }

    /**
     * 评估防守得分 - 阻止对手形成包围阵型
     * Evaluate defensive score - block opponent from forming surrounding patterns
     * Оценка защитных очков - блокирование окружающих комбинаций противника
     *
     * @param target        - 目标格子 / target square / целевая клетка
     * @param board         - 棋盘 / board / доска
     * @param opponentColor - 对手颜色 / opponent color / цвет противника
     * @return 防守得分 / defensive score / защитные очки
     */
    private int evaluateDefensiveScore(Square target, Board board, PieceColor opponentColor) {
        int score = 0;
        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        for (int[] dir : directions) {
            int centerV = target.v + dir[0];
            int centerH = target.h + dir[1];

            if (centerV >= 1 && centerV < board.nV - 1 &&
                    centerH >= 1 && centerH < board.nH - 1) {

                int opponentPieceCount = 0;
                boolean targetIsOneOfDiagonals = false;

                for (int[] checkDir : directions) {
                    int checkV = centerV + checkDir[0];
                    int checkH = centerH + checkDir[1];

                    if (board.onBoard(checkV, checkH)) {
                        if (checkV == target.v && checkH == target.h) {
                            targetIsOneOfDiagonals = true;
                        } else {
                            Square sq = board.getSquare(checkV, checkH);
                            if (!sq.isEmpty() && sq.getPiece().getColor() == opponentColor) {
                                opponentPieceCount++;
                            }
                        }
                    }
                }

                // Minimax思想：最小化对手优势（阻止对手得分）
                // Minimax: minimize opponent's advantage (block opponent's scoring)
                // Минимакс: минимизация преимущества противника (блокирование очков противника)
                if (opponentPieceCount == 3 && targetIsOneOfDiagonals) {
                    score += 800;   // 阻止对手完成包围 / block opponent's completion / блокирование завершения
                } else if (opponentPieceCount == 2 && targetIsOneOfDiagonals) {
                    score += 50;
                }
            }
        }

        return score;
    }

    /**
     * 评估位置得分 - 考虑棋盘位置战略价值
     * Evaluate position score - consider strategic value of board positions
     * Оценка позиционных очков - учёт стратегической ценности позиций на доске
     *
     * @param target - 目标格子 / target square / целевая клетка
     * @param board  - 棋盘 / board / доска
     * @return 位置得分 / position score / позиционные очки
     */
    private int evaluatePositionScore(Square target, Board board) {
        int score = 0;
        int centerV = board.nV / 2;
        int centerH = board.nH / 2;

        // 中心位置奖励
        // Center position bonus
        // Бонус за центральные позиции
        int distanceToCenter = Math.abs(target.v - centerV) + Math.abs(target.h - centerH);
        score += (board.nV - distanceToCenter) * 3;

        // 边缘位置惩罚
        // Edge position penalty
        // Штраф за крайние позиции
        if (target.v == 0 || target.v == board.nV - 1 ||
                target.h == 0 || target.h == board.nH - 1) {
            score -= 15;
        }

        // 角落位置额外惩罚
        // Additional corner penalty
        // Дополнительный штраф за углы
        if ((target.v == 0 || target.v == board.nV - 1) &&
                (target.h == 0 || target.h == board.nH - 1)) {
            score -= 25;
        }

        // 次中心位置奖励（1,1到6,6区域）
        // Sub-center position bonus (area 1,1 to 6,6)
        // Бонус за околоцентральные позиции (область 1,1 до 6,6)
        if (target.v >= 1 && target.v <= board.nV - 2 &&
                target.h >= 1 && target.h <= board.nH - 2) {
            score += 10;
        }

        return score;
    }
}