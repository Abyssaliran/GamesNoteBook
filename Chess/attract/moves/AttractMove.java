package attract.moves;

import attract.pieces.AttracPiece;
import game.core.Board;
import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.IPutMove;

import java.util.ArrayList;
import java.util.Collections;

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
        // 收集这条线上的所有棋子
        // Collect all pieces on this line.
        // Собираем все фигуры на этой линии.
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

        // 从最靠近目标的棋子开始处理（吸引效果）
        // Process from the piece closest to target (attraction effect).
        // Обрабатываем с фигуры, ближайшей к цели (эффект притяжения).
        // 每个棋子向目标方向移动一格，如果目标位置为空
        // Each piece moves one square toward target if the destination is empty.
        // Каждая фигура двигается на одну клетку к цели, если место назначения пустое.

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

                // 检查目标位置是否为空（不是target本身，也不是其他棋子）
                // Check if destination is empty (not target itself, not other pieces).
                // Проверяем, пусто ли место назначения (не сама цель, не другие фигуры).
                if (dest.isEmpty() && !dest.equals(target)) {
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
     * 执行移动：先放置棋子，再执行所有吸引移动
     * Execute move: first place the piece, then execute all attract moves.
     * Выполняем ход: сначала размещаем фигуру, затем выполняем все ходы притяжения.
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
