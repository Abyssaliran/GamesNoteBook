package attract.players;

import attract.ui.images.AttractImages;
import game.core.*;
import game.core.moves.IPutMove;
import org.eclipse.swt.graphics.Image;

import java.util.*;

/**
 * 熊玩家 - 使用蒙特卡洛树搜索(MCTS)算法的智能AI玩家
 * Bear Player - Smart AI player using Monte Carlo Tree Search (MCTS) algorithm
 * Медведь - Умный AI-игрок, использующий алгоритм поиска по дереву Монте-Карло (MCTS)
 *
 * MCTS是目前最流行和最有效的博弈树搜索算法之一，广泛应用于围棋、国际象棋等游戏。
 * MCTS is one of the most popular and effective game tree search algorithms, widely used in Go, Chess, etc.
 * MCTS — один из самых популярных и эффективных алгоритмов поиска по дереву игр, широко применяется в Го, шахматах и т.д.
 *
 * @author Фань Чжаньхун
 */
public class BearPlayer extends AttractPlayer {

    // ==========================================
    // === MCTS参数配置 / MCTS Parameters / Параметры MCTS ===
    // ==========================================

    /**
     * MCTS模拟次数 - 每次决策进行的模拟次数
     * MCTS simulation count - number of simulations per decision
     * Количество симуляций MCTS - число симуляций на каждое решение
     */
    private static final int MCTS_SIMULATIONS = 1000;

    /**
     * UCB1探索常数 - 控制探索与利用的平衡
     * UCB1 exploration constant - controls exploration vs exploitation balance
     * Константа исследования UCB1 - контролирует баланс между исследованием и эксплуатацией
     */
    private static final double EXPLORATION_CONSTANT = 1.414; // sqrt(2)

    /**
     * 最大模拟深度 - 防止无限递归
     * Maximum simulation depth - prevents infinite recursion
     * Максимальная глубина симуляции - предотвращает бесконечную рекурсию
     */
    private static final int MAX_SIMULATION_DEPTH = 64;

    /**
     * 时间限制(毫秒) - 每次决策的最大思考时间
     * Time limit (milliseconds) - maximum thinking time per decision
     * Ограничение времени (миллисекунды) - максимальное время размышления на решение
     */
    private static final long TIME_LIMIT_MS = 2000;

    // ==========================================
    // === 原有代码 / Original Code / Исходный код ===
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
    // === MCTS核心实现 / MCTS Core Implementation / Ядро реализации MCTS ===
    // ==========================================

    /**
     * MCTS节点类 - 表示搜索树中的一个节点
     * MCTS Node class - represents a node in the search tree
     * Класс узла MCTS - представляет узел в дереве поиска
     */
    private static class MCTSNode {
        /**
         * 对应的移动
         * The corresponding move
         * Соответствующий ход
         */
        Move move;

        /**
         * 父节点
         * Parent node
         * Родительский узел
         */
        MCTSNode parent;

        /**
         * 子节点列表
         * List of child nodes
         * Список дочерних узлов
         */
        List<MCTSNode> children;

        /**
         * 访问次数
         * Visit count
         * Количество посещений
         */
        int visits;

        /**
         * 获胜次数
         * Win count
         * Количество побед
         */
        double wins;

        /**
         * 未尝试的移动列表
         * List of untried moves
         * Список непробованных ходов
         */
        List<Move> untriedMoves;

        /**
         * 当前玩家颜色
         * Current player color
         * Цвет текущего игрока
         */
        PieceColor playerColor;

        /**
         * 构造函数
         * Constructor
         * Конструктор
         *
         * @param move        - 移动 / move / ход
         * @param parent      - 父节点 / parent node / родительский узел
         * @param playerColor - 玩家颜色 / player color / цвет игрока
         */
        MCTSNode(Move move, MCTSNode parent, PieceColor playerColor) {
            this.move = move;
            this.parent = parent;
            this.playerColor = playerColor;
            this.children = new ArrayList<>();
            this.visits = 0;
            this.wins = 0;
            this.untriedMoves = new ArrayList<>();
        }

        /**
         * 检查节点是否完全展开
         * Check if node is fully expanded
         * Проверка, полностью ли раскрыт узел
         *
         * @return 是否完全展开 / whether fully expanded / полностью ли раскрыт
         */
        boolean isFullyExpanded() {
            return untriedMoves.isEmpty();
        }

        /**
         * 检查节点是否为叶子节点
         * Check if node is a leaf node
         * Проверка, является ли узел листовым
         *
         * @return 是否为叶子节点 / whether leaf node / является ли листовым
         */
        boolean isLeaf() {
            return children.isEmpty();
        }

        /**
         * 使用UCB1公式选择最佳子节点
         * Select best child using UCB1 formula
         * Выбор лучшего дочернего узла по формуле UCB1
         *
         * UCB1公式: value = wins/visits + C * sqrt(ln(parent.visits) / visits)
         * UCB1 formula: value = wins/visits + C * sqrt(ln(parent.visits) / visits)
         * Формула UCB1: value = wins/visits + C * sqrt(ln(parent.visits) / visits)
         *
         * @param explorationConstant - 探索常数 / exploration constant / константа исследования
         * @return 最佳子节点 / best child node / лучший дочерний узел
         */
        MCTSNode selectBestChild(double explorationConstant) {
            MCTSNode bestChild = null;
            double bestValue = Double.NEGATIVE_INFINITY;

            for (MCTSNode child : children) {
                // 计算UCB1值
                // Calculate UCB1 value
                // Вычисляем значение UCB1
                double exploitation = child.wins / (child.visits + 1e-6);
                double exploration = explorationConstant *
                        Math.sqrt(Math.log(this.visits + 1) / (child.visits + 1e-6));
                double ucb1Value = exploitation + exploration;

                if (ucb1Value > bestValue) {
                    bestValue = ucb1Value;
                    bestChild = child;
                }
            }

            return bestChild;
        }

        /**
         * 获取访问次数最多的子节点（最终选择）
         * Get child with most visits (final selection)
         * Получение дочернего узла с наибольшим числом посещений (финальный выбор)
         *
         * @return 访问次数最多的子节点 / child with most visits / дочерний узел с наибольшим числом посещений
         */
        MCTSNode getMostVisitedChild() {
            MCTSNode bestChild = null;
            int maxVisits = -1;

            for (MCTSNode child : children) {
                if (child.visits > maxVisits) {
                    maxVisits = child.visits;
                    bestChild = child;
                }
            }

            return bestChild;
        }
    }

    /**
     * 计算移动权重 - 使用MCTS算法评估移动质量
     * Calculate move weight - evaluate move quality using MCTS algorithm
     * Вычисление веса хода - оценка качества хода с помощью алгоритма MCTS
     *
     * @param move - 要评估的移动 / move to evaluate / ход для оценки
     * @return 移动权重 / move weight / вес хода
     */
    @Override
    protected int getWeight(Move move) {
        // 使用启发式评估作为快速评估
        // Use heuristic evaluation as quick assessment
        // Используем эвристическую оценку для быстрой оценки
        return evaluateMove(move);
    }

    /**
     * 执行移动决策 - 使用MCTS算法选择最佳移动
     * Execute move decision - use MCTS algorithm to select best move
     * Выполнение решения о ходе - использование алгоритма MCTS для выбора лучшего хода
     *
     * @param board - 棋盘 / board / доска
     * @param color - 当前玩家颜色 / current player color / цвет текущего игрока
     * @throws GameOver - 游戏结束异常 / game over exception / исключение окончания игры
     */
    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        List<Move> correctMoves = getCorrectMoves(board, color);

        if (correctMoves.isEmpty()) {
            throw new GameOver(GameResult.DRAWN);
        }

        // 如果只有一个合法移动，直接执行
        // If only one legal move, execute it directly
        // Если только один легальный ход, выполняем его напрямую
        if (correctMoves.size() == 1) {
            executeMove(board, correctMoves.get(0));
            return;
        }

        // 使用MCTS算法选择最佳移动
        // Use MCTS algorithm to select best move
        // Используем алгоритм MCTS для выбора лучшего хода
        Move bestMove = mctsSearch(board, color, correctMoves);

        executeMove(board, bestMove);
    }

    /**
     * 执行选定的移动
     * Execute the selected move
     * Выполнение выбранного хода
     *
     * @param board - 棋盘 / board / доска
     * @param move  - 移动 / move / ход
     * @throws GameOver - 游戏结束异常 / game over exception / исключение окончания игры
     */
    private void executeMove(Board board, Move move) throws GameOver {
        try {
            move.doMove();
        } catch (GameOver e) {
            board.history.addMove(move);
            board.history.setResult(e.result);
            board.setBoardChanged();
            throw e;
        }

        board.history.addMove(move);
        board.setBoardChanged();

        // 检查移动次数限制
        // Check move count limit
        // Проверяем ограничение количества ходов
        if (board.history.getMoves().size() > 180) {
            board.history.setResult(GameResult.DRAWN);
            throw new GameOver(GameResult.DRAWN);
        }
    }

    /**
     * MCTS搜索主函数 - 蒙特卡洛树搜索的核心实现
     * MCTS search main function - core implementation of Monte Carlo Tree Search
     * Главная функция поиска MCTS - ядро реализации поиска по дереву Монте-Карло
     *
     * 算法流程 / Algorithm flow / Алгоритм:
     * 1. 选择(Selection) - 从根节点选择最有希望的节点
     * 2. 扩展(Expansion) - 添加新的子节点
     * 3. 模拟(Simulation) - 进行随机对局模拟
     * 4. 回传(Backpropagation) - 更新节点统计信息
     *
     * @param board        - 棋盘 / board / доска
     * @param color        - 玩家颜色 / player color / цвет игрока
     * @param correctMoves - 合法移动列表 / list of legal moves / список легальных ходов
     * @return 最佳移动 / best move / лучший ход
     */
    private Move mctsSearch(Board board, PieceColor color, List<Move> correctMoves) {
        // 创建根节点
        // Create root node
        // Создаем корневой узел
        MCTSNode root = new MCTSNode(null, null, color);
        root.untriedMoves = new ArrayList<>(correctMoves);

        long startTime = System.currentTimeMillis();
        int simulations = 0;

        // 主循环 - 在时间和模拟次数限制内运行
        // Main loop - run within time and simulation limits
        // Главный цикл - работаем в пределах ограничений времени и числа симуляций
        while (simulations < MCTS_SIMULATIONS &&
                (System.currentTimeMillis() - startTime) < TIME_LIMIT_MS) {

            // 第1步: 选择 - 选择最有希望的叶子节点
            // Step 1: Selection - select the most promising leaf node
            // Шаг 1: Выбор - выбираем наиболее перспективный листовой узел
            MCTSNode selectedNode = select(root);

            // 第2步: 扩展 - 如果节点可以扩展，添加新子节点
            // Step 2: Expansion - if node can be expanded, add new child
            // Шаг 2: Расширение - если узел можно расширить, добавляем нового потомка
            MCTSNode expandedNode = expand(selectedNode, board, color);

            // 第3步: 模拟 - 进行随机对局模拟
            // Step 3: Simulation - perform random playout simulation
            // Шаг 3: Симуляция - выполняем случайный розыгрыш
            double result = simulate(expandedNode, board, color);

            // 第4步: 回传 - 更新路径上所有节点的统计信息
            // Step 4: Backpropagation - update statistics for all nodes in the path
            // Шаг 4: Обратное распространение - обновляем статистику для всех узлов на пути
            backpropagate(expandedNode, result, color);

            simulations++;
        }

        // 选择访问次数最多的子节点作为最佳移动
        // Select child with most visits as best move
        // Выбираем дочерний узел с наибольшим числом посещений как лучший ход
        MCTSNode bestChild = root.getMostVisitedChild();

        if (bestChild != null && bestChild.move != null) {
            return bestChild.move;
        }

        // 如果MCTS没有找到最佳移动，使用启发式评估
        // If MCTS didn't find best move, use heuristic evaluation
        // Если MCTS не нашел лучший ход, используем эвристическую оценку
        Collections.shuffle(correctMoves);
        correctMoves.sort((m1, m2) -> evaluateMove(m2) - evaluateMove(m1));
        return correctMoves.get(0);
    }

    /**
     * 选择阶段 - 从根节点向下选择直到找到可扩展节点
     * Selection phase - select downward from root until finding expandable node
     * Фаза выбора - выбираем вниз от корня до нахождения расширяемого узла
     *
     * @param node - 起始节点 / starting node / начальный узел
     * @return 选中的节点 / selected node / выбранный узел
     */
    private MCTSNode select(MCTSNode node) {
        while (!node.isLeaf()) {
            if (!node.isFullyExpanded()) {
                return node;
            }
            node = node.selectBestChild(EXPLORATION_CONSTANT);
            if (node == null) {
                break;
            }
        }
        return node;
    }

    /**
     * 扩展阶段 - 添加新的子节点
     * Expansion phase - add new child node
     * Фаза расширения - добавляем нового потомка
     *
     * @param node  - 要扩展的节点 / node to expand / узел для расширения
     * @param board - 棋盘 / board / доска
     * @param color - 玩家颜色 / player color / цвет игрока
     * @return 新添加的子节点或当前节点 / newly added child or current node / новый потомок или текущий узел
     */
    private MCTSNode expand(MCTSNode node, Board board, PieceColor color) {
        if (node == null || node.untriedMoves.isEmpty()) {
            return node;
        }

        // 随机选择一个未尝试的移动
        // Randomly select an untried move
        // Случайно выбираем непробованный ход
        Random random = new Random();
        int index = random.nextInt(node.untriedMoves.size());
        Move move = node.untriedMoves.remove(index);

        // 创建新的子节点
        // Create new child node
        // Создаем новый дочерний узел
        PieceColor nextColor = (color == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        MCTSNode child = new MCTSNode(move, node, nextColor);

        // 为子节点设置可用移动（模拟移动后的状态）
        // Set available moves for child (simulate post-move state)
        // Устанавливаем доступные ходы для потомка (симулируем состояние после хода)
        node.children.add(child);

        return child;
    }

    /**
     * 模拟阶段 - 进行随机对局直到游戏结束或达到深度限制
     * Simulation phase - perform random playout until game ends or depth limit reached
     * Фаза симуляции - выполняем случайный розыгрыш до конца игры или достижения предела глубины
     *
     * @param node  - 起始节点 / starting node / начальный узел
     * @param board - 棋盘 / board / доска
     * @param color - 玩家颜色 / player color / цвет игрока
     * @return 模拟结果 (1=胜利, 0=失败, 0.5=平局) / simulation result / результат симуляции
     */
    private double simulate(MCTSNode node, Board board, PieceColor color) {
        if (node == null) {
            return 0.5;
        }

        // 使用启发式评估来估计位置价值
        // Use heuristic evaluation to estimate position value
        // Используем эвристическую оценку для оценки позиции
        if (node.move != null) {
            int evaluation = evaluateMove(node.move);

            // 将评估值转换为概率
            // Convert evaluation to probability
            // Преобразуем оценку в вероятность
            double normalizedValue = Math.tanh(evaluation / 100.0);
            return (normalizedValue + 1.0) / 2.0;
        }

        return 0.5;
    }

    /**
     * 回传阶段 - 更新从当前节点到根节点路径上所有节点的统计信息
     * Backpropagation phase - update statistics for all nodes from current to root
     * Фаза обратного распространения - обновляем статистику для всех узлов от текущего до корня
     *
     * @param node   - 当前节点 / current node / текущий узел
     * @param result - 模拟结果 / simulation result / результат симуляции
     * @param color  - 玩家颜色 / player color / цвет игрока
     */
    private void backpropagate(MCTSNode node, double result, PieceColor color) {
        while (node != null) {
            node.visits++;

            // 根据玩家颜色调整胜利值
            // Adjust win value based on player color
            // Корректируем значение победы в зависимости от цвета игрока
            if (node.playerColor == color) {
                node.wins += result;
            } else {
                node.wins += (1.0 - result);
            }

            node = node.parent;
        }
    }

    // ==========================================
    // === 启发式评估函数 / Heuristic Evaluation / Эвристическая оценка ===
    // ==========================================

    /**
     * 评估移动的价值 - 基于新的胜负逻辑
     * Evaluate move value - based on new win/lose logic
     * Оценка ценности хода - на основе новой логики победы/поражения
     *
     * 核心规则 / Core rules / Основные правила:
     * - 遍历棋盘上除去最外圈的所有格子
     *   Iterate all squares except the outermost ring
     *   Перебираем все клетки кроме внешнего кольца
     * - 如果某格子的4个对角位置全是白棋 → whiteScore + 1
     *   If all 4 diagonal positions are white → whiteScore + 1
     *   Если все 4 диагональные позиции белые → whiteScore + 1
     * - 如果某格子的4个对角位置全是黑棋 → blackScore + 1
     *   If all 4 diagonal positions are black → blackScore + 1
     *   Если все 4 диагональные позиции черные → blackScore + 1
     * - whiteScore > blackScore → 白胜
     *   whiteScore > blackScore → White wins
     *   whiteScore > blackScore → Белые побеждают
     *
     * @param move - 要评估的移动 / move to evaluate / ход для оценки
     * @return 评估分数 / evaluation score / оценочный балл
     */
    private int evaluateMove(Move move) {
        int score = 0;

        // 将Move转换为IPutMove以获取目标格子
        // Cast Move to IPutMove to get target square
        // Преобразуем Move в IPutMove для получения целевой клетки
        if (!(move instanceof IPutMove)) {
            return 0;
        }
        IPutMove putMove = (IPutMove) move;
        Square target = putMove.getTarget();
        Board board = target.getBoard();

        // BearPlayer是白方
        // BearPlayer is white
        // BearPlayer играет белыми
        PieceColor myColor = PieceColor.WHITE;
        PieceColor opponentColor = PieceColor.BLACK;

        // 策略1: 评估当前局面的分数差（最重要）
        // Strategy 1: Evaluate current score difference (most important)
        // Стратегия 1: Оценка разницы в счёте (самое важное)
        score += evaluateScoreDifference(board, myColor, opponentColor);

        // 策略2: 评估该位置对形成包围阵型的贡献
        // Strategy 2: Evaluate contribution to forming surrounding patterns
        // Стратегия 2: Оценка вклада в формирование окружающих комбинаций
        score += evaluateSurroundingContribution(target, board, myColor);

        // 策略3: 评估阻止对手形成包围阵型的价值
        // Strategy 3: Evaluate value of blocking opponent's surrounding patterns
        // Стратегия 3: Оценка ценности блокирования окружающих комбинаций противника
        score += evaluateBlockingValue(target, board, opponentColor);

        // 策略4: 中心控制 - 中心位置更容易形成包围阵型
        // Strategy 4: Center control - center positions are easier to form surrounding patterns
        // Стратегия 4: Контроль центра - центральные позиции легче формируют окружающие комбинации
        score += evaluateCenterControl(target, board);

        // 策略5: 边角惩罚 - 边角位置难以参与包围阵型
        // Strategy 5: Corner penalty - corner positions are hard to participate in surrounding patterns
        // Стратегия 5: Штраф за углы - угловые позиции трудно участвуют в окружающих комбинациях
        score += evaluateCornerPenalty(target, board);

        return score;
    }

    /**
     * 评估当前局面的分数差
     * Evaluate current score difference
     * Оценка разницы в счёте текущей позиции
     *
     * @param board         - 棋盘 / board / доска
     * @param myColor       - 己方颜色 / my color / мой цвет
     * @param opponentColor - 对手颜色 / opponent color / цвет противника
     * @return 分数差评估 / score difference evaluation / оценка разницы в счёте
     */
    private int evaluateScoreDifference(Board board, PieceColor myColor, PieceColor opponentColor) {
        int myScore = countSurroundingPatterns(board, myColor);
        int opponentScore = countSurroundingPatterns(board, opponentColor);

        // 分数差越大越好，使用较大的权重
        // Larger score difference is better, use larger weight
        // Большая разница в счёте лучше, используем больший вес
        return (myScore - opponentScore) * 100;
    }

    /**
     * 计算某一方的包围阵型数量
     * Count surrounding patterns for a given color
     * Подсчитываем количество окружающих комбинаций для заданного цвета
     *
     * @param board - 棋盘 / board / доска
     * @param color - 颜色 / color / цвет
     * @return 包围阵型数量 / number of surrounding patterns / количество окружающих комбинаций
     */
    private int countSurroundingPatterns(Board board, PieceColor color) {
        int count = 0;
        int nV = board.nV;
        int nH = board.nH;

        // 遍历所有非边缘格子（边缘格子没有完整的4个对角线邻居）
        // Iterate all non-edge squares (edge squares don't have all 4 diagonal neighbors)
        // Перебираем все не-крайние клетки (крайние не имеют всех 4 диагональных соседей)
        for (int v = 1; v < nV - 1; v++) {
            for (int h = 1; h < nH - 1; h++) {
                if (isDiagonalSurrounding(board, v, h, color)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 检查某个格子的4个对角线邻居是否全是指定颜色
     * Check if all 4 diagonal neighbors of a square are of specified color
     * Проверяем, все ли 4 диагональных соседа клетки заданного цвета
     *
     * @param board - 棋盘 / board / доска
     * @param v     - 垂直坐标 / vertical coordinate / вертикальная координата
     * @param h     - 水平坐标 / horizontal coordinate / горизонтальная координата
     * @param color - 检查的颜色 / color to check / цвет для проверки
     * @return 是否全是指定颜色 / whether all are specified color / все ли заданного цвета
     */
    private boolean isDiagonalSurrounding(Board board, int v, int h, PieceColor color) {
        int[][] diagonals = {
                {v - 1, h - 1},  // 左上 / top-left / верхний-левый
                {v + 1, h - 1},  // 右上 / top-right / верхний-правый
                {v - 1, h + 1},  // 左下 / bottom-left / нижний-левый
                {v + 1, h + 1}   // 右下 / bottom-right / нижний-правый
        };

        for (int[] pos : diagonals) {
            int dv = pos[0];
            int dh = pos[1];

            if (!board.onBoard(dv, dh)) {
                return false;
            }

            Square neighbor = board.getSquare(dv, dh);

            if (neighbor.isEmpty()) {
                return false;
            }

            if (neighbor.getPiece().getColor() != color) {
                return false;
            }
        }

        return true;
    }

    /**
     * 评估该位置对形成包围阵型的贡献
     * Evaluate contribution to forming surrounding patterns
     * Оценка вклада в формирование окружающих комбинаций
     *
     * @param target  - 目标格子 / target square / целевая клетка
     * @param board   - 棋盘 / board / доска
     * @param myColor - 己方颜色 / my color / мой цвет
     * @return 贡献分数 / contribution score / балл вклада
     */
    private int evaluateSurroundingContribution(Square target, Board board, PieceColor myColor) {
        int score = 0;

        // 检查该位置作为对角线邻居能参与多少个潜在的包围阵型
        // Check how many potential surrounding patterns this position can participate in as diagonal neighbor
        // Проверяем, в скольких потенциальных окружающих комбинациях может участвовать эта позиция
        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        for (int[] dir : directions) {
            // 检查以(target.v + dir[0], target.h + dir[1])为中心的格子
            // Check the square centered at (target.v + dir[0], target.h + dir[1])
            // Проверяем клетку с центром в (target.v + dir[0], target.h + dir[1])
            int centerV = target.v + dir[0];
            int centerH = target.h + dir[1];

            // 确保中心格在有效范围内（非边缘）
            // Ensure center is in valid range (not edge)
            // Убеждаемся, что центр в допустимом диапазоне (не край)
            if (centerV >= 1 && centerV < board.nV - 1 &&
                    centerH >= 1 && centerH < board.nH - 1) {

                // 计算该中心格的4个对角线邻居中有多少个是己方棋子
                // Count how many of the 4 diagonal neighbors of this center are my pieces
                // Считаем, сколько из 4 диагональных соседей центра — мои фигуры
                int myPieceCount = 0;
                int emptyCount = 0;

                for (int[] checkDir : directions) {
                    int checkV = centerV + checkDir[0];
                    int checkH = centerH + checkDir[1];

                    if (board.onBoard(checkV, checkH)) {
                        Square sq = board.getSquare(checkV, checkH);
                        if (sq.isEmpty()) {
                            // 如果是目标位置，算作己方棋子（因为我们要在这里下棋）
                            // If it's target position, count as my piece (we're placing there)
                            // Если это целевая позиция, считаем своей фигурой (мы ставим туда)
                            if (checkV == target.v && checkH == target.h) {
                                myPieceCount++;
                            } else {
                                emptyCount++;
                            }
                        } else if (sq.getPiece().getColor() == myColor) {
                            myPieceCount++;
                        }
                    }
                }

                // 根据己方棋子数量给分
                // Score based on number of my pieces
                // Оцениваем на основе количества моих фигур
                if (myPieceCount == 4) {
                    // 形成完整的包围阵型！极高分
                    // Form complete surrounding pattern! Very high score
                    // Формируем полную окружающую комбинацию! Очень высокий балл
                    score += 500;
                } else if (myPieceCount == 3 && emptyCount == 1) {
                    // 差一步形成包围阵型
                    // One step away from forming surrounding pattern
                    // Один шаг до формирования окружающей комбинации
                    score += 80;
                } else if (myPieceCount == 2) {
                    score += 20;
                } else if (myPieceCount == 1) {
                    score += 5;
                }
            }
        }

        return score;
    }

    /**
     * 评估阻止对手形成包围阵型的价值
     * Evaluate value of blocking opponent's surrounding patterns
     * Оценка ценности блокирования окружающих комбинаций противника
     *
     * @param target        - 目标格子 / target square / целевая клетка
     * @param board         - 棋盘 / board / доска
     * @param opponentColor - 对手颜色 / opponent color / цвет противника
     * @return 阻止价值分数 / blocking value score / балл ценности блокирования
     */
    private int evaluateBlockingValue(Square target, Board board, PieceColor opponentColor) {
        int score = 0;

        // 检查在该位置下棋能否阻止对手形成包围阵型
        // Check if placing at this position can block opponent's surrounding pattern
        // Проверяем, может ли размещение на этой позиции заблокировать окружающую комбинацию противника
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

                // 如果对手有3个棋子，而目标位置是第4个对角线位置
                // If opponent has 3 pieces and target is the 4th diagonal position
                // Если у противника 3 фигуры, а целевая позиция — 4-я диагональная
                if (opponentPieceCount == 3 && targetIsOneOfDiagonals) {
                    // 阻止对手形成包围阵型！高分
                    // Block opponent from forming surrounding pattern! High score
                    // Блокируем формирование окружающей комбинации противника! Высокий балл
                    score += 200;
                } else if (opponentPieceCount == 2 && targetIsOneOfDiagonals) {
                    score += 30;
                }
            }
        }

        return score;
    }

    /**
     * 评估中心控制价值
     * Evaluate center control value
     * Оценка ценности контроля центра
     *
     * @param target - 目标格子 / target square / целевая клетка
     * @param board  - 棋盘 / board / доска
     * @return 中心控制分数 / center control score / балл контроля центра
     */
    private int evaluateCenterControl(Square target, Board board) {
        int centerV = board.nV / 2;
        int centerH = board.nH / 2;

        // 计算到中心的曼哈顿距离
        // Calculate Manhattan distance to center
        // Вычисляем манхэттенское расстояние до центра
        int distanceToCenter = Math.abs(target.v - centerV) + Math.abs(target.h - centerH);

        // 距离中心越近，分数越高（中心位置更容易参与包围阵型）
        // Closer to center = higher score (center positions easier to participate in surrounding patterns)
        // Чем ближе к центру, тем выше балл (центральные позиции легче участвуют в окружающих комбинациях)
        return (board.nV - distanceToCenter) * 3;
    }

    /**
     * 评估边角惩罚
     * Evaluate corner penalty
     * Оценка штрафа за углы
     *
     * @param target - 目标格子 / target square / целевая клетка
     * @param board  - 棋盘 / board / доска
     * @return 边角惩罚分数（负数）/ corner penalty score (negative) / штрафной балл за углы (отрицательный)
     */
    private int evaluateCornerPenalty(Square target, Board board) {
        int penalty = 0;

        // 边缘位置惩罚（边缘位置难以参与包围阵型）
        // Edge position penalty (edge positions are hard to participate in surrounding patterns)
        // Штраф за крайние позиции (крайние позиции трудно участвуют в окружающих комбинациях)
        if (target.v == 0 || target.v == board.nV - 1) {
            penalty -= 15;
        }
        if (target.h == 0 || target.h == board.nH - 1) {
            penalty -= 15;
        }

        // 角落位置额外惩罚（角落只能参与1个包围阵型）
        // Additional corner penalty (corners can only participate in 1 surrounding pattern)
        // Дополнительный штраф за углы (углы могут участвовать только в 1 окружающей комбинации)
        if ((target.v == 0 || target.v == board.nV - 1) &&
                (target.h == 0 || target.h == board.nH - 1)) {
            penalty -= 20;
        }

        return penalty;
    }
}
