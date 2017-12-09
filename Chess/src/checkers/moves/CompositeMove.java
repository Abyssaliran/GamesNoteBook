package checkers.moves;

import java.util.ArrayList;

import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

/**
 * Составной ход - последовательность ходов-захватов фигур.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CompositeMove implements Move {
	/**
	 * Последовательность захватов фигур противника.
	 */
	private ArrayList<Capture> captures;
	
	/**
	 * Фигура которая делает ход. 
	 */
	private Piece piece;

	private CompositeMove() {
		captures = new ArrayList<>();
	}
	
	public CompositeMove(Capture capture) {
		captures = new ArrayList<>();

		this.piece = capture.piece;
		addCapture(capture);
	}
	
	public CompositeMove(Piece piece) {
		captures = new ArrayList<>();

		this.piece = piece;
	}

	/**
	 * Добавить ход-захват фигуры к последовательности ходов.
	 * @param capture - ход-захват фигуры
	 */
	public void addCapture(Capture capture) {
		captures.add(capture);
	}

	@Override
	public void doMove() throws GameOver {
		for (Capture capture : captures)
			capture.doMove();
	}

	@Override
	public void undoMove() {
		for (int k = captures.size()-1; k >= 0; k--)
			captures.get(k).undoMove();
	}

	@Override
	public Piece getPiece() {
		return piece;
	}
	
	@Override
	public String toString() {
		if (captures.isEmpty())
			return "????";
		
		String s = "" + captures.get(0).source;
		
		for (Capture c : captures)  
			s += "x" + c.target;
		 
		return s;
	}

	/**
	 * @param square - Допустим ли ход на эту клетку 
	 * @return
	 */
	public boolean isAcceptable(Square square) {
		// Если фигура уже была на этой клетке, то ход недопустим.
		return !captures
				.stream()
				.anyMatch(c -> c.getSource() == square);
	}
	
	public CompositeMove getClone() {
		CompositeMove clone = new CompositeMove();
		clone.piece = piece;
		
		clone.captures.addAll(captures); 
		
		return clone;
	}

	public boolean isEmpty() {
		return captures.isEmpty();
	}
}
