package vikings.pieces;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для фигур игры Викинги.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public abstract class VikingsPiece extends Piece {
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
	 * Трон - клетка в центре доски.
	 * На троне в начальнй позиции расположен король белых.
	 * Проверить это клетка трон или нет?
	 * 
	 * @param square - проверяемая клетка.
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
	 * Выход - клетка в углу доски.
	 * Король белых должен прорваться к выходу.
	 * Проверить это клетка выход или нет?
	 * 
	 * @param square - проверяемая клетка.
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
	 * Собрать клетки фигур заданного цвета, если они окружены фигурами
	 * противника по вертикали и горизонтали.
	 * 
	 * @param board
	 *            - доска на которой собираются фигуры
	 * 
	 * @param color
	 *            - цвет фигур противника.
	 * @return список клеток с клетками где стоят захваченные фигуры противника.
	 */
	protected List<Square> collectCaptured(Board board, PieceColor color) {
		List<Square> captured = new ArrayList<>();
		
		for (Piece p : board.getPieces(color))
			if (isCaptured(p))
				captured.add(p.square);
		
		return captured;
	}
	
	/**
	 * Захвачена ли заданная фигура фигурами противника?
	 * 
	 * @param piece
	 *            - заданная фигура.
	 * @return захвачена или нет
	 */
	private boolean isCaptured(Piece piece) {
		// TODO Auto-generated method stub
		return false;
	}
}
