package checkers.moves;

import java.util.ArrayList;

import game.core.Move;

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

	CompositeMove() {
		captures = new ArrayList<>();
	}
	
	/**
	 * Добавить ход-захват фигуры к последовательности ходов.
	 * @param capture - ход-захват фигуры
	 */
	public void addCapture(Capture capture) {
		captures.add(capture);
	}

	@Override
	public void doMove() {
		for (Capture capture : captures)
			capture.doMove();
	}

	@Override
	public void undoMove() {
		for (Capture capture : captures)
			capture.undoMove();
	}
}
