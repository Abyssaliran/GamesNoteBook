package game.core.moves;

import java.util.ArrayList;

import game.core.Move;

/**
 * Составной ход - последовательность простых ходов фигурой одного цвета.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CompositeMove implements Move {
	/**
	 * Последовательность простых ходов.
	 */
	private ArrayList<Move> moves;

	CompositeMove() {
		moves = new ArrayList<>();
	}
	
	/**
	 * Вернуть последовательность простых ходов.
	 * @return
	 */
	public ArrayList<Move> getMoves() {
		return moves;
	}

	/**
	 * Добавить простой ход фигурой фигуры к последовательности ходов.
	 * 
	 * @param move
	 *            - простой ход фигурой
	 */
	public void addMove(Move move) {
		moves.add(move);
	}

	@Override
	public void doMove() {
		for (Move move : moves)
			move.doMove();
	}

	@Override
	public void undoMove() {
		for (Move move : moves)
			move.undoMove();
	}
}
