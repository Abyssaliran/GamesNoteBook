package vikings.pieces;

import java.util.List;
import java.util.stream.Collectors;

import game.core.Dirs;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для фигур игры Викинги.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class VikingsPiece extends Piece {
	public VikingsPiece(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		Square target = squares[0];
		
		// Допустим ход только на пустую клетку.
		return target.isEmpty();
	}

	/**
	 * Трон - клетка в центре доски. На троне в начальнй позиции расположен
	 * король белых. Проверить это клетка трон или нет?
	 * 
	 * @param square
	 *            - проверяемая клетка.
	 * @return трон или нет?
	 */
	static 
	public boolean isTron(Square square) {
		if (square.v != square.getBoard().nV/2)
			return false;
		
		if (square.h != square.getBoard().nH/2)
			return false;
		
		return true;
	}
	
	/**
	 * Выход - клетка в углу доски. Король белых должен прорваться к выходу.
	 * Проверить это клетка выход для короля или нет?
	 * 
	 * @param square
	 *            - проверяемая клетка.
	 * @return выход или нет?
	 */
	static 
	public boolean isExit(Square square) {
		int nv = square.getBoard().nV-1;
		int nh = square.getBoard().nH-1;

		if ((square.v == 0) && (square.h == 0)) 
			return true;
		
		if ((square.v == 0) && (square.h == nh)) 
			return true;
		
		if ((square.v == nv) && (square.h == 0)) 
			return true;
		
		if ((square.v == nv) && (square.h == nh)) 
			return true;

		return false;
	}

	/**
	 * Вернуть захваченные фигуры.
	 * 
	 * @param source
	 *            - откуда пошла вражеская фигура.
	 * @param target
	 *            - куда пошла вражеская фигура.
	 * @return список клеток с клетками где стоят захваченные фигуры противника.
	 */
	protected List<Piece> collectCaptured(Square source, Square target) {
		return getEnemies()
		  .stream()
		  .filter(p -> isCaptured(p, source, target))
		  .collect( Collectors.toList() );
	}
	
	/**
	 * Захвачена ли заданная фигура <b>piece</b> фигурами противника?
	 * 
	 * @param piece
	 *            - заданная фигура.
	 * @param source
	 *            - откуда пошла вражеская фигура.
	 * @param target
	 *            - куда пошла вражеская фигура.
	 * @return захвачена заданая фигура или нет.
	 */
	private boolean isCaptured(Piece piece, Square source, Square target) {
		return piece instanceof Сyning 
			   ? isKingCaptured(piece, source, target)
			   : isPieceCaptured(piece, source, target);
	}

	/**
	 * Захвачен ли простой викинг фигурами противника с 2-х сторон?
	 * 
	 * @param piece
	 *            - фигура - простой викинг.
	 * @param source
	 *            - откуда пошла вражеская фигура.
	 * @param target
	 *            - куда пошла вражеская фигура.
	 * @return захватывается ли фигура вражескими фигурами.
	 */
	public boolean isPieceCaptured(Piece piece, Square source, Square target) {
//		if (isNotBorder(piece))
//		    return false;

		// Есть ли окружение фигуры с 2-х сторон по горизонтали?
		if (isCaptureSide(piece, source, target, Dirs.LEFT) && 
			isCaptureSide(piece, source, target, Dirs.RIGHT) )
			return true;

		// Есть ли окружение фигуры с 2-х сторон по вертикали?
		if (isCaptureSide(piece, source, target, Dirs.UP) && 
		    isCaptureSide(piece, source, target, Dirs.DOWN))
			return true;
		
		return false;
	}

	/**
	 * Захвачен ли король фигурами противника с 4-х сторон?
	 * 
	 * @param king
	 *            - король
	 * @param source
	 *            - откуда пошла вражеская фигура.
	 * @param target
	 *            - куда пошла вражеская фигура.
	 * @return захватывается ли король вражескими фигурами.
	 */
	public boolean isKingCaptured(Piece king, Square source, Square target) {
//		if (isNotBorder(king))
//			return false;

		// Есть окружение короля с 4-х сторон.
		if (isCaptureSide(king, source, target, Dirs.LEFT)  && 
			isCaptureSide(king, source, target, Dirs.RIGHT) &&
		    isCaptureSide(king, source, target, Dirs.UP)    && 
		    isCaptureSide(king, source, target, Dirs.DOWN))
			return true;
		
		return false;
	}

	/**
	 * С одной из сторон фигуры край доски?
	 * 
	 * @param piece
	 *            - проверяемая фигура.
	 * @return стоит ли фигура на краю доски.
	 */
	public boolean isNotBorder(Piece piece) {
		return 
			!piece.hasNext(Dirs.UP)   || 
			!piece.hasNext(Dirs.DOWN) ||
			!piece.hasNext(Dirs.LEFT) ||
			!piece.hasNext(Dirs.RIGHT);
	}

	/**
	 * В заданном направлении от фигуры <b>piece</b> могут находится:
	 * <ul>
	 * <li>трон,</li>
	 * <li>выход,</li>
	 * <li>вражеская фигура</li>
	 * </ul>
	 * которые используются для окружения.
	 * 
	 * @param piece
	 *            - задананая фигура.
	 * @param dir
	 *            - заданное направление.
	 * @return может ли данная сторона фигуры быть использована для окружения
	 *         этой фигуры.
	 */
	private boolean isCaptureSide(Piece piece, Square source, Square target, Dirs dir) {
		// В этом направлении край доски. Окружение возможно.
		if (!piece.square.hasNext(dir)) return true;
		
		Square next = piece.next(dir);

		// В этом направлении трон. Окружение возможно.
		if (VikingsPiece.isTron(next)) return true;
		
		// В этом направлении выход. Окружение возможно.
		if (VikingsPiece.isExit(next)) return true;
		
		// В этом направлении клетка с которой ушла вражеская фигура.
		// Окружение невозможно.
		if (next == source) return false;
		
		// В этом направлении клетка на которую пришла вражеская фигура.
		// Окружение возможно.
		if (next == target) return true;
		
		// В этом направлении пустая клетка.
		// Окружение невозможно.
		if (next.isEmpty()) return false;
		
		// Если в этом направлении вражеская фигура,
		// то окружение фигуры возможно.
		return next.getPiece().isEnemy(piece);
	}
}
