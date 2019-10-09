package backgammon.moves;

import backgammon.BackgammonBoard;
import backgammon.pieces.BackgammonGroup;
import backgammon.pieces.Stone;
import game.core.Piece;
import game.core.Square;

/**
 * Ход с захватом одинокой вражеской фигуры в плен.
 */
public class Capture extends SimpleMove {
	/**
	 * Фигура которая делает ход.
	 */
	private BackgammonGroup piece;
	
	/**
	 * Вражеская фигура захваченная в плен.
	 */
	private BackgammonGroup enemy;
	
	public Capture(Square source, Square target) {
		super(source, target);
		
		piece = (BackgammonGroup) source.getPiece();
		enemy = (BackgammonGroup) target.getPiece();
	}
	

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void doMove() {
		// TODO Поставить захваченную фигуру на клетку для пленных (bar).
		BackgammonBoard board = (BackgammonBoard) source.getBoard();
		
		Square square4Enemy = board.getBar4Piece(enemy);
		
		enemy.remove();
		square4Enemy.setPiece(enemy);
		
		// TODO Поставить свою фигуру на место захваченной в плен.
		super.doMove();
	}
	
	@Override
	public void undoMove() {
		// TODO Вернуть свою фигуру на место.
		super.undoMove();
		
		// TODO Вернуть захваченную фигуру из клетки для пленных (bar).
	}

	@Override
	public String toString() {
		return "" + source + "x" + target;
	}
}