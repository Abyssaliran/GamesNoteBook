package breakthrough.players;

import breakthrough.moves.Capture;
import breakthrough.moves.SimpleMove;
import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

import static java.lang.Math.abs;

/**
 * TODO Король Д.С. - реализовать алгоритм
 * <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%B5%D1%80%D0%BC%D0%B5%D1%81"></a>Гермес</a> -
 * бог торговли и счастливого случая, хитрости и воровства, юношества и красноречия.
 * Покровитель глашатаев, послов, пастухов, путников.
 */
public class Hermes extends BreakThroughPlayer {

	@Override
	public String getName() {
		return "Гермес";
	}

	@Override
	public String getAuthorName() {
		return "Король Д.С.";
	}

	@Override
	protected int getWeight(Move m) {
		SimpleMove move = (SimpleMove) m;

		// Ход с захватом вражеских фигур лучше.
		if (move instanceof Capture)
			return 100;

		Piece piece = move.getPiece();
		Board board = piece.square.getBoard();
		Square target = move.getTarget();

		// Расстояние до последней горизонтали (цели).
		int dh = abs(board.nH - 1 - target.h);
		int distance = piece.isBlack() ? dh : -dh; 

		return -distance; // Чем меньше расстояние до цели тем лучше ход.
	}
}
