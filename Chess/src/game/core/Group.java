package game.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Группа фигур расположенных на одной клетке.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class Group extends Piece {
	public List<Piece> pieces = new ArrayList<>();

	public Group(Square square, PieceColor color) {
		super(square, color);
		// TODO Auto-generated constructor stub
	}
}
