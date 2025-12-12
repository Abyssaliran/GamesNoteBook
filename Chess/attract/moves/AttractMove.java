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
     * 规则：如果某个格子的所有对角线相邻位置（共4个）都有对手的棋子，
     *       则形成包围的一方获得一分。拥有更多此类获胜阵型的玩家获胜。
     * Rule: If all 4 diagonal neighbors of a square have opponent's pieces,
     *       the player forming the surrounding scores one point.
     *       The player with more such winning patterns wins.
     * Правило: Если все 4 диагональных соседа клетки заняты фигурами противника,
     *          игрок, создавший окружение, получает очко.
     *          Побеждает игрок с большим количеством таких выигрышных комбинаций.
     */
    private void checkGameOver() throws GameOver {
        Board board = target.getBoard();
        List<Square> emptySquares = board.getEmptySquares();

        // 只有当棋盘满了才判定胜负
        // Only determine winner when board is full.
        // Определяем победителя только когда доска заполнена.
        if (!emptySquares.isEmpty()) {
            return;
        }

        // 统计双方的获胜阵型数量
        // Count winning patterns for both sides.
        // Подсчитываем количество выигрышных комбинаций для обеих сторон.
        int whiteScore = countWinningPatterns(board, PieceColor.WHITE);
        int blackScore = countWinningPatterns(board, PieceColor.BLACK);

        // 显示获胜弹窗
        // Show winning dialog.
        // Показываем диалог победы.
        GameResult result;
        String winnerMessage;

        if (whiteScore > blackScore) {
            result = GameResult.WHITE_WIN;
            winnerMessage = "White wins!\nScore: White " + whiteScore + " - Black " + blackScore;
        } else if (blackScore > whiteScore) {
            result = GameResult.BLACK_WIN;
            winnerMessage = "Black wins!\nScore: White " + whiteScore + " - Black " + blackScore;
        } else {
            result = GameResult.DRAWN;
            winnerMessage = "Draw!\nScore: White " + whiteScore + " - Black " + blackScore;
        }

        // 显示结果弹窗
        // Show result dialog.
        // Показываем диалог результата.
        showGameOverDialog(winnerMessage);

        throw new GameOver(result);
    }

    /**
     * 统计某一方的获胜阵型数量
     * Count winning patterns for a given color.
     * Подсчитываем количество выигрышных комбинаций для заданного цвета.
     *
     * 获胜阵型：某个格子的4个对角线邻居全部是该颜色的棋子
     * Winning pattern: All 4 diagonal neighbors of a square are pieces of this color.
     * Выигрышная комбинация: Все 4 диагональных соседа клетки — фигуры этого цвета.
     *
     * @param board - 棋盘 / the board / доска
     * @param color - 要统计的颜色 / color to count / цвет для подсчета
     * @return 获胜阵型数量 / number of winning patterns / количество выигрышных комбинаций
     */
    private int countWinningPatterns(Board board, PieceColor color) {
        int count = 0;
        int nV = board.nV;
        int nH = board.nH;

        // 遍历所有非边缘格子（因为边缘格子没有完整的4个对角线邻居）
        // Iterate all non-edge squares (edge squares don't have all 4 diagonal neighbors).
        // Перебираем все не-крайние клетки (крайние не имеют всех 4 диагональных соседей).
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
     * 检查某个格子是否被指定颜色的棋子对角线包围
     * Check if a square is diagonally surrounded by pieces of the specified color.
     * Проверяем, окружена ли клетка по диагонали фигурами заданного цвета.
     *
     * @param board - 棋盘 / the board / доска
     * @param v     - 格子的垂直坐标 / vertical coordinate of the square / вертикальная координата клетки
     * @param h     - 格子的水平坐标 / horizontal coordinate of the square / горизонтальная координата клетки
     * @param color - 检查的颜色 / color to check / цвет для проверки
     * @return 是否被包围 / whether surrounded / окружена ли
     */
    private boolean isDiagonalSurrounding(Board board, int v, int h, PieceColor color) {
        // 检查4个对角线邻居: 左上、右上、左下、右下
        // Check 4 diagonal neighbors: top-left, top-right, bottom-left, bottom-right.
        // Проверяем 4 диагональных соседа: верхний-левый, верхний-правый, нижний-левый, нижний-правый.
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
            // Check if on board.
            // Проверяем, находится ли на доске.
            if (!board.onBoard(dv, dh)) {
                return false;
            }

            Square neighbor = board.getSquare(dv, dh);

            // 检查是否有棋子
            // Check if there is a piece.
            // Проверяем, есть ли фигура.
            if (neighbor.isEmpty()) {
                return false;
            }

            // 检查棋子颜色是否匹配
            // Check if piece color matches.
            // Проверяем, совпадает ли цвет фигуры.
            if (neighbor.getPiece().getColor() != color) {
                return false;
            }
        }

        // 所有4个对角线邻居都是指定颜色的棋子
        // All 4 diagonal neighbors are pieces of the specified color.
        // Все 4 диагональных соседа — фигуры заданного цвета.
        return true;
    }

    /**
     * 显示游戏结束弹窗
     * Show game over dialog.
     * Показываем диалог окончания игры.
     *
     * @param message - 显示的消息 / message to display / сообщение для отображения
     */
    private void showGameOverDialog(String message) {
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
