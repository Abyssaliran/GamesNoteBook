package chinachess.players;

import game.core.Board;
import game.core.Piece;
import game.core.Square;
import chinachess.pieces.Bishop;
import chinachess.pieces.Guardian;
import chinachess.pieces.Gun;
import chinachess.pieces.King;
import chinachess.pieces.Knight;
import chinachess.pieces.Pawn;
import chinachess.pieces.Rook;

/**
 * Базовый класс для алгоритмов выбора лучшего хода китайских шахмат.
 * 
 * @author 
 */
public class PlayerBrain {
	/**
	 * Максимальное расстояние между клетками доски.
	 */
	protected static final int MAX_DISTANCE = 20;

	/**
	 * Ценность (вес) поля на доске.
	 * Чем ближе поле к центру, том оно лучше. 
	 * @param s - поле
	 * @return вес поля.
	 */
	static	public  int getSquareWeight(final Square s) {
		Board board = s.getBoard();
		final int tv = s.v;
		final int th = s.h;
		
		final double dv = Math.abs(tv - 0.5 * (board.nV-1) );
		final double dh = Math.abs(th - 0.5 * (board.nH-1) );
		int distance = (int) (dv + dh);
		
		return MAX_DISTANCE - distance;
	}

	/**
	 * Найти короля у фигур - врагов для фигуры piece. 
	 * @param piece - фигура для которой ищем врага-короля.
	 * @return вражеский король.
	 */
	protected King getEnemyKing(Piece piece) {
		King enemyKing = null;
		for (Piece p : piece.getEnemies())
			if (p instanceof King) {
				enemyKing = (King) p;
				break;
			}
		return enemyKing;
	}

	/**
	 * Выдать расстояние между клетками.
	 * @param s1 
	 * @param s2
	 * @return
	 */
	protected int distance(Square s1, Square s2) {
		final double dv = Math.abs(s1.v - s2.v);
		final double dh = Math.abs(s1.h - s2.h);
		return (int) (dv + dh);
	}

	/**
	 * Получить вес фигуры в китайских шахматах.
	 * @param piece фигура в китайских шахматах.
	 * @return вес фигуры.
	 */
	public int getPieceWeight(Piece piece) {
		if (piece instanceof Pawn)      return 30+1;
		if (piece instanceof Knight)    return 30+2;
		if (piece instanceof Rook)      return 30+3;
		if (piece instanceof Gun)       return 30+4;
		if (piece instanceof Bishop)    return 30+5;
		if (piece instanceof Guardian)  return 30+6;
			
		return 0;
	}
}