package game.core.moves;

import java.util.ArrayList;

import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Составной ход - последовательность простых ходов фигурой одного цвета.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CompositeMove<T extends ITransferMove> implements ITransferMove {
	/**
	 * Последовательность простых ходов.
	 */
	private ArrayList<T> moves;
	
	/**
	 * фигура которая делает ход.
	 */
	private Piece piece;

	CompositeMove() {
		moves = new ArrayList<>();
	}
	
	public CompositeMove(T move) {
		moves = new ArrayList<>();

		this.piece = move.getPiece();
		addMove(move);
	}
	
	public <P> CompositeMove(Piece p) {
		moves = new ArrayList<>();

		this.piece = p;
	}

	/**
	 * Вернуть последовательность простых ходов.
	 * @return
	 */
	public ArrayList<T> getMoves() {
		return moves;
	}

	/**
	 * Добавить простой ход фигурой фигуры к последовательности ходов.
	 * 
	 * @param capture
	 *            - простой ход фигурой
	 */
	public void addMove(T capture) {
		moves.add(capture);
	}

	@Override
	public void doMove() throws GameOver {
		for (Move move : moves)
			try {
				move.doMove();
			} catch (GameOver e) {
				throw new GameOver(e.result);
			}
	}

	@Override
	public void undoMove() {
		for (int k = moves.size()-1; k >= 0; k--)
			moves.get(k).undoMove();
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public Square getSource() {
		return moves.get(0).getSource();
	}

	@Override
	public Square getTarget() {
		return moves.get(moves.size()-1).getSource();
	}
	
	public CompositeMove<T> getClone() {
		CompositeMove<T> clone = new CompositeMove<>();
		clone.piece = piece;
		
		clone.moves.addAll(moves); 
		
		return clone;
	}

	public boolean isEmpty() {
		return moves.isEmpty();
	}
	
	/**
	 * @param square - Допустим ли ход на эту клетку 
	 * @return
	 */
	public boolean isAcceptable(Square square) {
		// Если фигура уже была на этой клетке, то ход недопустим.
		return !moves
				.stream()
				.anyMatch(c -> c.getSource() == square);
	}
	
	@Override
	public String toString() {
		if (moves.isEmpty())
			return "????";
		
		String s = "" + moves.get(0).getSource();
		
		for (T c : moves)  
			s += "x" + c.getTarget();
		 
		return s;
	}
}
