package vikings.players;

import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Arthur - Легендарный вождь бриттов V—VI веков.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Arthur extends VikingsPlayer {
	@Override
	public String getName() {
		return "Артур";
	}
	
	@Override
	public String getInfo() {
		return "Легендарный вождь бриттов V—VI веков.\n"
				+ "Самый знаменитый из кельтских героев, "
				+ "центральный герой британского эпоса "
				+ "и многочисленных рыцарских романов. \n"
				+ "Согласно легенде, Артур стал королём во исполнение пророчества, \n"
				+ "защитил Британию от набегов саксов и собрал при своём дворе в Камелоте \n"
				+ "доблестнейших и благороднейших рыцарей Круглого стола, ";
	}

	@Override
	public String getAuthorName() {
		return "";
	}
	
	@Override
	public boolean isBlackPlayer() {
		return false;
	}
	
	/**
	 * Задать вес для хода.
	 * 
	 * @param move
	 *            - ход
	 * @return оценка хода.
	 */
	@Override
	public int getWeight(Move move) {
		return 0;
	}

	/**
	 * Это атакующий ход.
	 * 
	 * @param piece
	 *            - какая фигура идет.
	 * @param target
	 *            - куда фигура идет.
	 * @return
	 */
	private boolean isAttackMove(Piece piece, Square target) {
		// 1. Наша фигура становится рядом с фигурой противника.
		// 2. Есть ли другая наша фигура, которая следующим ходом 
		//    может встать с другой стороны вражеской фигуры.
		return false;
	}

	/**
	 * Не приведет ли ход фигурой на поле target к потере фигур.
	 * 
	 * @param piece
	 *            - какая фигура идет.
	 * @param target
	 *            - куда фигура идет.
	 * @return
	 */
	private boolean isSafeMove(Piece piece, Square target) {
		// Мы не подставляем свою фигуру.
		// 1. Фигура становится рядом с фигурой противника.
		// 2. Нет ли вражеской фигуры, которая следующим ходом 
		//    может встать с другой стороны нашей фигуры.
		return true;
	}
}