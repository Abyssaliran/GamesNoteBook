package reversi.moves;

import java.util.List;

import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;

/**
 * Фигура-камень для 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiMove implements IPutMove, ICaptureMove {
	/**
	 * Клетка куда поставлена фигура.
	 */
	Square target;
	
	/**
	 * Клетки на которых стоят захваченные в плен вражеские фигуры.
	 * Эти фигуры меняют цвет и воюют на нашей стороне.
	 */
	List<Square> captured;

	private Piece piece;

	/**
	 * Создать ход игры в реверси.
	 * 
	 * @param target - клетка на которую идет фигура
	 * @param captured - клетки на которых стоят захваченные (перекрашеные).
	 */
	public ReversiMove(Piece piece, Square target, List<Square> captured) {
		this.target = target;
		this.captured = captured;
		
		this.piece = piece;
	}

	@Override
	public void doMove() {
		target.setPiece(piece);
		
		// TODO Задорожная - дополнить выполнение хода реверси:
		// перекрасить фигуры стоящие на клетках captured 
		// в противоположный цвет.
	}

	@Override
	public void undoMove() {
		piece.remove();
		
		// TODO Задорожная - дополнить выполнение хода реверси:
		// перекрасить фигуры стоящие на клетках captured 
		// в противоположный цвет.
	}

	@Override
	public String toString() {
		return "" + target;
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public List<Square> getCaptured() {
		return captured;
	}
}
