package reversi.moves;

import java.util.List;

import game.core.Move;
import game.core.Square;

/**
 * Фигура-камень для 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiMove implements Move {
	/**
	 * Клетка куда поставлена фигура.
	 */
	Square target;
	
	/**
	 * Клетки на которых стоят захваченные в плен вражеские фигуры.
	 * Эти фигуры меняют цвет и воюют на нашей стороне.
	 */
	List<Square> captured;

	/**
	 * Создать ход игры в реверси.
	 * 
	 * @param target - клетка на которую идет фигура
	 * @param captured - клетки на которых стоят захваченные в плен вражеские фигуры.
	 * Эти фигуры меняют цвет и воюют на нашей стороне.
	 */
	public ReversiMove(Square target, List<Square> captured) {
		this.target = target;
		this.captured = captured;
	}

	@Override
	public void doMove() {
		// TODO Auto-generated method stub
	}

	@Override
	public void undoMove() {
		// TODO Auto-generated method stub
	}
}
