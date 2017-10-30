package go.moves;

import java.util.List;

import game.core.Move;
import game.core.Square;

/**
 * Ход с захватом фигуры для <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture implements Move {
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
	public Capture(Square target, List<Square> captured) {
		this.target = target;
		this.captured = captured;
	}

	@Override
	public void doMove() {
		// TODO Go реализовать выполнение хода и захват фигур.
	}

	@Override
	public void undoMove() {
		// TODO Go реализовать отмену хода и возврат фигур.
	}
}
