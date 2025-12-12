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

        // BearPlayer是白方
        // BearPlayer is white
        // BearPlayer играет белыми
        PieceColor myColor = PieceColor.WHITE;
        PieceColor opponentColor = PieceColor.BLACK;

        // 策略1: 优先形成包围阵型（得分）
        // Strategy 1: Prioritize forming surrounding patterns (scoring)
        // Стратегия 1: Приоритет формирования окружающих комбинаций (очки)
        score += evaluateSurroundingScore(target, board, myColor);

        // 策略2: 阻止对手形成包围阵型
        // Strategy 2: Block opponent from forming surrounding patterns
        // Стратегия 2: Блокировать формирование окружающих комбинаций противника
        score += evaluateBlockingScore(target, board, opponentColor);

        // 策略3: 中心控制
        // Strategy 3: Center control
        // Стратегия 3: Контроль центра
        score += evaluateCenterScore(target, board);

        return score;
    }

    /**
     * 评估形成包围阵型的得分
     * Evaluate score for forming surrounding patterns
     * Оценка очков за формирование окружающих комбинаций
     *
     * 规则：如果某格子的4个对角都是己方棋子，己方得1分
     * Rule: If all 4 diagonals of a square are my pieces, I score 1 point
     * Правило: Если все 4 диагонали клетки — мои фигуры, я получаю 1 очко
     *
     * @param target  - 目标格子 / target square / целевая клетка
     * @param board   - 棋盘 / board / доска
     * @param myColor - 己方颜色 / my color / мой цвет
     * @return 得分评估 / score evaluation / оценка очков
     */
    private int evaluateSurroundingScore(Square target, Board board, PieceColor myColor) {
        int score = 0;
        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        // 检查该位置能参与哪些包围阵型
        // Check which surrounding patterns this position can participate in
        // Проверяем, в каких окружающих комбинациях может участвовать эта позиция
        for (int[] dir : directions) {
            // 以(target + dir)为中心的格子
            // Square centered at (target + dir)
            // Клетка с центром в (target + dir)
            int centerV = target.v + dir[0];
            int centerH = target.h + dir[1];

            // 确保中心格在有效范围内（非边缘）
            // Ensure center is in valid range (not edge)
            // Убеждаемся, что центр в допустимом диапазоне (не край)
            if (centerV >= 1 && centerV < board.nV - 1 &&
                    centerH >= 1 && centerH < board.nH - 1) {

                int myPieceCount = 0;

                // 检查4个对角位置
                // Check 4 diagonal positions
                // Проверяем 4 диагональные позиции
                for (int[] checkDir : directions) {
                    int checkV = centerV + checkDir[0];
                    int checkH = centerH + checkDir[1];

                    if (board.onBoard(checkV, checkH)) {
                        // 如果是目标位置，算作己方棋子
                        // If it's target position, count as my piece
                        // Если это целевая позиция, считаем своей фигурой
                        if (checkV == target.v && checkH == target.h) {
                            myPieceCount++;
                        } else {
                            Square sq = board.getSquare(checkV, checkH);
                            if (!sq.isEmpty() && sq.getPiece().getColor() == myColor) {
                                myPieceCount++;
                            }
                        }
                    }
                }

                // 根据己方棋子数量给分
                // Score based on number of my pieces
                // Оцениваем на основе количества моих фигур
                if (myPieceCount == 4) {
                    // 完成包围阵型！极高分
                    // Complete surrounding pattern! Very high score
                    // Завершение окружающей комбинации! Очень высокий балл
                    score += 1000;
                } else if (myPieceCount == 3) {
                    // 差一步
                    // One step away
                    // Один шаг до завершения
                    score += 100;
                } else if (myPieceCount == 2) {
                    score += 20;
                }
            }
        }

        return score;
    }

    /**
     * 评估阻止对手形成包围阵型的得分
     * Evaluate score for blocking opponent's surrounding patterns
     * Оценка очков за блокирование окружающих комбинаций противника
     *
     * @param target        - 目标格子 / target square / целевая клетка
     * @param board         - 棋盘 / board / доска
     * @param opponentColor - 对手颜色 / opponent color / цвет противника
     * @return 阻止得分 / blocking score / балл блокирования
     */
    private int evaluateBlockingScore(Square target, Board board, PieceColor opponentColor) {
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

                // 如果对手有3个棋子，阻止第4个很重要
                // If opponent has 3 pieces, blocking the 4th is important
                // Если у противника 3 фигуры, важно заблокировать 4-ю
                if (opponentPieceCount == 3 && targetIsOneOfDiagonals) {
                    score += 500;
                } else if (opponentPieceCount == 2 && targetIsOneOfDiagonals) {
                    score += 30;
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
