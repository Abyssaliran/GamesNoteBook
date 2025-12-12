package attract.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class SimpleMove implements ITransferMove {
	/**
	 * 被移动的棋子
	 * The piece being moved.
	 * Какая фигура перемещается.
	 */
	protected final Piece piece;

	/**
	 * 移动起点
	 * Where it moves from.
	 * Откуда перемещается.
	 */
	protected final Square source;

	/**
	 * 移动终点
	 * Where it moves to.
	 * Куда перемещается.
	 */
	protected final Square target;

	/**
	 * 从棋盘格子数组构造移动
	 * Constructor from array of squares.
	 * Конструктор из массива клеток.
	 *
	 * @param squares - 包含source和target的数组 / array containing source and target / массив с source и target
	 */
	public SimpleMove(Square[] squares) {
		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	/**
	 * 指定棋子、起点和终点的构造函数
	 * Constructor specifying piece, source and target.
	 * Конструктор с указанием фигуры, начальной и конечной клетки.
	 *
	 * @param piece  - 被移动的棋子 / the piece being moved / перемещаемая фигура
	 * @param source - 起点 / the source square / начальная клетка
	 * @param target - 终点 / the target square / конечная клетка
	 */
	public SimpleMove(Piece piece, Square source, Square target) {
		this.piece = piece;
		this.source = source;
		this.target = target;
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}

	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}

	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

}
