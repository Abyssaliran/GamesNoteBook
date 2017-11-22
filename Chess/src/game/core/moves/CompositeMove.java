package game.core.moves;

import java.util.ArrayList;

import game.core.GameOver;
import game.core.Move;
import game.core.Piece;

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
	
	/**
	 * фигура которая делает ход.
	 */
	private Piece piece;

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
			try {
				move.doMove();
			} catch (GameOver e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void undoMove() {
		for (Move move : moves)
			move.undoMove();
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
}
