package attract.moves;

import attract.pieces.AttracPiece;
import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.IPutMove;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 吸引棋的移动类，实现"吸引"机制
 * Move class for Attract game, implementing the "attraction" mechanism.
 * Класс хода для игры Attract, реализующий механизм "притяжения".
 */
public class AttractMove implements IPutMove {
    /**
     * 被放置的棋子
     * The piece being placed.
     * Размещаемая фигура.
     */
    private final AttracPiece piece;

    /**
     * 棋子放置的目标格子
     * The target square where the piece is placed.
     * Целевая клетка для размещения фигуры.
     */
    private final Square target;

    /**
     * 被吸引棋子的移动列表
     * List of moves for attracted pieces.
     * Список ходов для притягиваемых фигур.
     */
    private final ArrayList<SimpleMove> attractMoves = new ArrayList<>();

    /**
     * 构造函数：创建一个吸引移动并计算所有被吸引的棋子移动
     * Constructor: creates an attract move and calculates all attracted piece moves.
     * Конструктор: создает ход притяжения и вычисляет все ходы притягиваемых фигур.
     *
     * @param piece   - 被放置的棋子 / the piece being placed / размещаемая фигура
     * @param squares - 目标格子数组 / array of target squares / массив целевых клеток
     */
    public AttractMove(AttracPiece piece, Square[] squares) {
        this.target = squares[0];
        this.piece = piece;

        // 计算被吸引的棋子移动
        // Calculate attracted piece moves.
        // Вычисляем ходы притягиваемых фигур.
        calculateAttractMoves();
    }

    /**
     * 计算所有被吸引棋子的移动
     * Calculate moves for all attracted pieces.
     * Вычисляем ходы для всех притягиваемых фигур.
     *
     * 规则：所有在同一水平行和垂直列的棋子必须向新棋子移动一格（如果可能）。
     * Rule: All pieces in the same horizontal row and vertical column must move one square toward the new piece (if possible).
     * Правило: Все фигуры в той же горизонтали и вертикали должны переместиться на одну клетку к новой фигуре (если возможно).
     */
    private void calculateAttractMoves() {
        Board board = target.getBoard();
        int targetV = target.v;  // 目标格子的垂直坐标 / vertical coordinate / вертикальная координата
        int targetH = target.h;  // 目标格子的水平坐标 / horizontal coordinate / горизонтальная координата

        // 处理左侧的棋子（向右移动，朝向目标）
        // Process pieces on the left (move right, toward target).
        // Обрабатываем фигуры слева (двигаются вправо, к цели).
        processLineAttract(board, targetV, targetH, -1, 0);

        // 处理右侧的棋子（向左移动，朝向目标）
        // Process pieces on the right (move left, toward target).
        // Обрабатываем фигуры справа (двигаются влево, к цели).
        processLineAttract(board, targetV, targetH, 1, 0);

        // 处理上方的棋子（向下移动，朝向目标）
        // Process pieces above (move down, toward target).
        // Обрабатываем фигуры сверху (двигаются вниз, к цели).
        processLineAttract(board, targetV, targetH, 0, -1);

        // 处理下方的棋子（向上移动，朝向目标）
        // Process pieces below (move up, toward target).
        // Обрабатываем фигуры снизу (двигаются вверх, к цели).
        processLineAttract(board, targetV, targetH, 0, 1);
    }

    /**
     * 处理一个方向上的棋子吸引
     * Process piece attraction in one direction.
     * Обрабатываем притяжение фигур в одном направлении.
     *
     * @param board   - 棋盘 / the board / доска
     * @param targetV - 目标格子垂直坐标 / target vertical coordinate / вертикальная координата цели
     * @param targetH - 目标格子水平坐标 / target horizontal coordinate / горизонтальная координата цели
     * @param dv      - 垂直搜索方向 / vertical search direction / направление поиска по вертикали
     * @param dh      - 水平搜索方向 / horizontal search direction / направление поиска по горизонтали
     */
    private void processLineAttract(Board board, int targetV, int targetH, int dv, int dh) {
        // 收集这条线上的所有棋子（按照距离目标从近到远的顺序）
        // Collect all pieces on this line (ordered from closest to farthest from target).
        // Собираем все фигуры на этой линии (в порядке от ближайшей к цели до самой дальней).
        ArrayList<Piece> piecesOnLine = new ArrayList<>();
        ArrayList<Square> squaresOnLine = new ArrayList<>();

        int v = targetV + dv;
        int h = targetH + dh;

        while (board.onBoard(v, h)) {
            Square square = board.getSquare(v, h);
            if (!square.isEmpty()) {
                piecesOnLine.add(square.getPiece());
                squaresOnLine.add(square);
            }
            v += dv;
            h += dh;
        }

        // 如果没有棋子，直接返回
        // If no pieces, return directly.
        // Если нет фигур, сразу возвращаемся.
        if (piecesOnLine.isEmpty()) {
            return;
        }

        // 找到第一个空位（紧挨着目标的位置）
        // Find the first empty position (right next to target).
        // Находим первую пустую позицию (рядом с целью).
        int firstEmptyV = targetV + dv;
        int firstEmptyH = targetH + dh;

        // 检查紧挨目标的位置是否为空
        // Check if the position next to target is empty.
        // Проверяем, пуста ли позиция рядом с целью.
        if (!board.onBoard(firstEmptyV, firstEmptyH)) {
            return;
        }

        Square firstSquare = board.getSquare(firstEmptyV, firstEmptyH);

        // 如果紧挨目标的位置已经有棋子，说明不能进行吸引
        // If the position next to target already has a piece, attraction cannot happen.
        // Если позиция рядом с целью уже занята фигурой, притяжение невозможно.
        if (!firstSquare.isEmpty()) {
            // 所有棋子都紧挨着，无法移动
            // All pieces are adjacent, cannot move.
            // Все фигуры рядом, двигаться некуда.
            return;
        }

        // 从最靠近目标的棋子开始处理（吸引效果，链式移动）
        // Process from the piece closest to target (attraction effect, chain movement).
        // Обрабатываем с фигуры, ближайшей к цели (эффект притяжения, цепное движение).
        //
        // 关键逻辑：每个棋子都向目标移动一格
        // Key logic: Each piece moves one square toward target.
        // Ключевая логика: Каждая фигура перемещается на одну клетку к цели.
        //
        // 由于我们按顺序执行移动，前一个棋子移走后会腾出空位给下一个棋子
        // Since we execute moves in order, the previous piece vacates a position for the next one.
        // Поскольку мы выполняем ходы по порядку, предыдущая фигура освобождает место для следующей.

        for (int i = 0; i < piecesOnLine.size(); i++) {
            Piece p = piecesOnLine.get(i);
            Square source = squaresOnLine.get(i);

            // 计算目标位置（向目标移动一格）
            // Calculate destination (move one square toward target).
            // Вычисляем место назначения (сдвиг на одну клетку к цели).
            int destV = source.v - dv;  // 反方向移动，朝向target / move in opposite direction, toward target
            int destH = source.h - dh;

            if (board.onBoard(destV, destH)) {
                Square dest = board.getSquare(destV, destH);

                // 检查目标位置：
                // 1. 不是target本身（那里已经放置了新棋子）
                // 2. 是空的，或者是前一个棋子的位置（在doMove时会先移走）
                // Check destination:
                // 1. Not the target itself (new piece is placed there).
                // 2. Is empty, or is the previous piece's position (will be vacated during doMove).
                // Проверяем место назначения:
                // 1. Не сама цель (там уже размещена новая фигура).
                // 2. Пусто, или это позиция предыдущей фигуры (освободится при doMove).

                if (dest.equals(target)) {
                    // 目标位置是放置新棋子的位置，不能移动到那里
                    // Destination is where new piece is placed, cannot move there.
                    // Место назначения — где размещается новая фигура, туда нельзя.
                    continue;
                }

                // 检查目标位置是空的，或者是链条中前一个棋子的原位置
                // Check if destination is empty or is the previous piece's original position in the chain.
                // Проверяем, пусто ли место или это исходная позиция предыдущей фигуры в цепочке.
                boolean canMove = dest.isEmpty();

                // 如果目标位置不为空，检查是否是前一个被吸引棋子的位置
                // If destination is not empty, check if it's the previous attracted piece's position.
                // Если место не пусто, проверяем, не позиция ли это предыдущей притягиваемой фигуры.
                if (!canMove && i > 0) {
                    // 检查dest是否是前一个棋子的source（即squaresOnLine.get(i-1)）
                    // Check if dest is the previous piece's source (i.e., squaresOnLine.get(i-1)).
                    // Проверяем, является ли dest исходной позицией предыдущей фигуры.
                    if (dest.equals(squaresOnLine.get(i - 1))) {
                        canMove = true;
                    }
                }

                if (canMove) {
                    SimpleMove move = new SimpleMove(p, source, dest);
                    attractMoves.add(move);
                }
            }
        }
    }

    @Override
    public Square getTarget() {
        return target;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    /**
     * 执行移动：先放置棋子，再执行所有吸引移动，最后检查胜负
     * Execute move: first place the piece, then execute all attract moves, finally check win/lose.
     * Выполняем ход: сначала размещаем фигуру, затем выполняем все ходы притяжения, наконец проверяем победу/поражение.
     */
    @Override
    public void doMove() throws GameOver {
        // 首先放置新棋子
        // First place the new piece.
        // Сначала размещаем новую фигуру.
        target.setPiece(piece);

        // 然后执行所有被吸引棋子的移动
        // Then execute all attracted piece moves.
        // Затем выполняем все ходы притягиваемых фигур.
        for (SimpleMove move : attractMoves) {
            move.doMove();
        }

        // 检查游戏是否结束
        // Check if the game is over.
        // Проверяем, закончилась ли игра.
        checkGameOver();
    }

    /**
     * 检查游戏是否结束，并计算胜负
     * Check if the game is over and determine the winner.
     * Проверяем, закончилась ли игра, и определяем победителя.
     *
     * 核心规则 / Core rules / Основные правила:
     * 1. 遍历棋盘上所有有棋子的格子
     *    Iterate all squares that have pieces on them
     *    Перебираем все клетки, на которых есть фигуры
     *
     * 2. 对于每个有棋子的格子，检查该棋子的4个对角邻居是否全是对方棋子
     *    For each piece, check if all 4 diagonal neighbors are opponent's pieces
     *    Для каждой фигуры проверяем, все ли 4 диагональных соседа — фигуры противника
     *
     * 3. 如果白棋的4个对角全是黑棋，白方得1分（白棋形成"获胜棋子"）
     *    If a white piece has all 4 diagonals as black pieces, white scores 1 point (white piece is a "winning piece")
     *    Если у белой фигуры все 4 диагонали заняты черными, белые получают 1 очко (белая "выигрышная фигура")
     *
     * 4. 如果黑棋的4个对角全是白棋，黑方得1分（黑棋形成"获胜棋子"）
     *    If a black piece has all 4 diagonals as white pieces, black scores 1 point (black piece is a "winning piece")
     *    Если у черной фигуры все 4 диагонали заняты белыми, черные получают 1 очко (черная "выигрышная фигура")
     *
     * 5. whiteScore > blackScore → 白胜；blackScore > whiteScore → 黑胜；相等 → 游戏继续
     *    whiteScore > blackScore → White wins; blackScore > whiteScore → Black wins; equal → game continues
     *    whiteScore > blackScore → Белые побеждают; blackScore > whiteScore → Черные побеждают; равны → игра продолжается
     */
    private void checkGameOver() throws GameOver {
        Board board = target.getBoard();

        // 获取双方的获胜棋子位置列表
        // Get winning piece position lists for both sides
        // Получаем списки позиций выигрышных фигур для обеих сторон
        List<String> whitePositions = getWinningPiecePositions(board, PieceColor.WHITE);
        List<String> blackPositions = getWinningPiecePositions(board, PieceColor.BLACK);

        int whiteScore = whitePositions.size();
        int blackScore = blackPositions.size();

        // 判断胜负
        // Determine winner
        // Определяем победителя
        if (whiteScore > blackScore) {
            // 白方获胜
            // White wins
            // Белые побеждают
            String message = buildWinMessage("White", whiteScore, blackScore, whitePositions, blackPositions);
            showScoreDialog(message);
            throw new GameOver(GameResult.WHITE_WIN);
        } else if (blackScore > whiteScore) {
            // 黑方获胜
            // Black wins
            // Черные побеждают
            String message = buildWinMessage("Black", whiteScore, blackScore, whitePositions, blackPositions);
            showScoreDialog(message);
            throw new GameOver(GameResult.BLACK_WIN);
        }

        // 分数相等，游戏继续（不处理）
        // Scores are equal, game continues (no action)
        // Счёт равный, игра продолжается (без действий)
    }

    /**
     * 构建获胜消息，包含具体得分位置
     * Build win message with specific scoring positions
     * Формирование сообщения о победе с конкретными позициями очков
     *
     * @param winner         - 获胜方 / winner / победитель
     * @param whiteScore     - 白方得分 / white score / очки белых
     * @param blackScore     - 黑方得分 / black score / очки черных
     * @param whitePositions - 白方获胜棋子位置 / white winning piece positions / позиции выигрышных белых фигур
     * @param blackPositions - 黑方获胜棋子位置 / black winning piece positions / позиции выигрышных черных фигур
     * @return 格式化的消息 / formatted message / форматированное сообщение
     */
    private String buildWinMessage(String winner, int whiteScore, int blackScore,
                                   List<String> whitePositions, List<String> blackPositions) {
        StringBuilder sb = new StringBuilder();

        // 标题
        // Title
        // Заголовок
        sb.append(winner).append(" wins!\n\n");

        // 总分
        // Total score
        // Общий счёт
        sb.append("Score: White ").append(whiteScore).append(" - Black ").append(blackScore).append("\n\n");

        // 白方获胜棋子位置
        // White winning piece positions
        // Позиции выигрышных белых фигур
        if (!whitePositions.isEmpty()) {
            sb.append("White winning pieces at: ");
            sb.append(String.join(", ", whitePositions));
            sb.append("\n");
        }

        // 黑方获胜棋子位置
        // Black winning piece positions
        // Позиции выигрышных черных фигур
        if (!blackPositions.isEmpty()) {
            sb.append("Black winning pieces at: ");
            sb.append(String.join(", ", blackPositions));
        }

        return sb.toString();
    }

    /**
     * 显示计分板弹窗（只显示分数，不询问是否继续游戏）
     * Show score dialog (only shows score, does not ask to play again)
     * Показать диалог счёта (только показывает счёт, не спрашивает о продолжении)
     *
     * @param message - 显示的消息 / message to display / сообщение для отображения
     */
    private void showScoreDialog(String message) {
        Display display = Display.getCurrent();
        if (display == null) {
            return;
        }

        Shell shell = new Shell(display);
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
        messageBox.setText("Game Over");
        messageBox.setMessage(message);
        messageBox.open();
        shell.dispose();
    }

    /**
     * 获取某一方的获胜棋子位置列表
     * Get list of winning piece positions for a given color
     * Получение списка позиций выигрышных фигур для заданного цвета
     *
     * 获胜棋子定义：一个棋子的4个对角邻居全是对方棋子
     * Winning piece definition: A piece whose all 4 diagonal neighbors are opponent's pieces
     * Определение выигрышной фигуры: фигура, у которой все 4 диагональных соседа — фигуры противника
     *
     * @param board - 棋盘 / the board / доска
     * @param color - 要检查的棋子颜色 / color of pieces to check / цвет проверяемых фигур
     * @return 获胜棋子位置列表 / list of winning piece positions / список позиций выигрышных фигур
     */
    private List<String> getWinningPiecePositions(Board board, PieceColor color) {
        List<String> positions = new ArrayList<>();
        int nV = board.nV;
        int nH = board.nH;

        // 获取对方颜色
        // Get opponent color
        // Получаем цвет противника
        PieceColor opponentColor = (color == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        // 遍历所有非边缘格子（边缘格子的棋子不可能有完整的4个对角邻居）
        // Iterate all non-edge squares (pieces on edge can't have all 4 diagonal neighbors)
        // Перебираем все не-крайние клетки (фигуры на краю не могут иметь все 4 диагональных соседа)
        for (int v = 1; v < nV - 1; v++) {
            for (int h = 1; h < nH - 1; h++) {
                Square square = board.getSquare(v, h);

                // 检查该格子是否有指定颜色的棋子
                // Check if this square has a piece of the specified color
                // Проверяем, есть ли на этой клетке фигура указанного цвета
                if (square.isEmpty()) {
                    continue;
                }

                Piece piece = square.getPiece();
                if (piece.getColor() != color) {
                    continue;
                }

                // 检查该棋子的4个对角邻居是否全是对方棋子
                // Check if all 4 diagonal neighbors of this piece are opponent's pieces
                // Проверяем, все ли 4 диагональных соседа этой фигуры — фигуры противника
                if (isDiagonalSurroundedByOpponent(board, v, h, opponentColor)) {
                    // 使用棋盘坐标表示法（列用字母a-h/a-p，行用数字1-8/1-16）
                    // Use chess coordinate notation (columns a-h/a-p, rows 1-8/1-16)
                    // Используем шахматную нотацию (столбцы a-h/a-p, ряды 1-8/1-16)
                    //
                    // 注意：棋盘显示是从下往上数行号（底部是1，顶部是8/16）
                    // Note: Board displays row numbers from bottom to top (bottom is 1, top is 8/16)
                    // Примечание: Доска отображает номера рядов снизу вверх (низ — 1, верх — 8/16)
                    char col = (char) ('a' + v);
                    int row = nH - h;  // 转换为显示坐标 / convert to display coordinate / преобразуем в отображаемую координату
                    positions.add("" + col + row);
                }
            }
        }

        return positions;
    }

    /**
     * 检查某个格子的棋子是否被对方棋子对角线包围（4个对角邻居全是对方棋子）
     * Check if a piece at given position is diagonally surrounded by opponent's pieces
     * Проверяем, окружена ли фигура на данной позиции по диагонали фигурами противника
     *
     * @param board         - 棋盘 / the board / доска
     * @param v             - 格子的垂直坐标 / vertical coordinate / вертикальная координата
     * @param h             - 格子的水平坐标 / horizontal coordinate / горизонтальная координата
     * @param opponentColor - 对方颜色 / opponent's color / цвет противника
     * @return 是否被对方包围 / whether surrounded by opponent / окружена ли противником
     */
    private boolean isDiagonalSurroundedByOpponent(Board board, int v, int h, PieceColor opponentColor) {
        // 检查4个对角线邻居: 左上、右上、左下、右下
        // Check 4 diagonal neighbors: top-left, top-right, bottom-left, bottom-right
        // Проверяем 4 диагональных соседа: верхний-левый, верхний-правый, нижний-левый, нижний-правый
        int[][] diagonals = {
            {v - 1, h - 1},  // 左上 / top-left / верхний-левый
            {v + 1, h - 1},  // 右上 / top-right / верхний-правый
            {v - 1, h + 1},  // 左下 / bottom-left / нижний-левый
            {v + 1, h + 1}   // 右下 / bottom-right / нижний-правый
        };

        for (int[] pos : diagonals) {
            int dv = pos[0];
            int dh = pos[1];

            // 检查是否在棋盘内
            // Check if on board
            // Проверяем, находится ли на доске
            if (!board.onBoard(dv, dh)) {
                return false;
            }

            Square neighbor = board.getSquare(dv, dh);

            // 检查是否有棋子
            // Check if there is a piece
            // Проверяем, есть ли фигура
            if (neighbor.isEmpty()) {
                return false;
            }

            // 检查棋子颜色是否是对方颜色
            // Check if piece color is opponent's color
            // Проверяем, является ли цвет фигуры цветом противника
            if (neighbor.getPiece().getColor() != opponentColor) {
                return false;
            }
        }

        // 所有4个对角线邻居都是对方棋子
        // All 4 diagonal neighbors are opponent's pieces
        // Все 4 диагональных соседа — фигуры противника
        return true;
    }

    /**
     * 撤销移动：先逆序撤销所有吸引移动，再移除放置的棋子
     * Undo move: first undo all attract moves in reverse order, then remove the placed piece.
     * Отменяем ход: сначала отменяем все ходы притяжения в обратном порядке, затем убираем размещенную фигуру.
     */
    @Override
    public void undoMove() {
        // 逆序撤销所有被吸引棋子的移动
        // Undo all attracted piece moves in reverse order.
        // Отменяем все ходы притягиваемых фигур в обратном порядке.
        ArrayList<SimpleMove> reversedMoves = new ArrayList<>(attractMoves);
        Collections.reverse(reversedMoves);
        for (SimpleMove move : reversedMoves) {
            move.undoMove();
        }

        // 最后移除放置的棋子
        // Finally remove the placed piece.
        // В конце убираем размещенную фигуру.
        target.removePiece();
    }

    @Override
    public String toString() {
        return "" + target;
    }
}
