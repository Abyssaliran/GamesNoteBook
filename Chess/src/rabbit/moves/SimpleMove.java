package rabbit.moves;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import rabbit.pieces.Rabbit;
import rabbit.pieces.Wolf;

public class SimpleMove implements ITransferMove {
	/**
	 * Какая фигура перемещается.
	 */
	protected final Piece piece;

	/**
	 * Откуда перемещается.
	 */
	protected final Square source;

	/**
	 * Куда перемещается.
	 */
	protected final Square target;

	public SimpleMove(Square[] squares) {
		source = squares[0];
		target = squares[1];

		piece = source.getPiece();
	}

	@Override
	public void doMove() throws GameOver {
		piece.moveTo(target);

		if (piece instanceof Rabbit)
			if (target.h == 0) 
				// Заяц пришел на последнюю горизонталь.
				// Заяц выиграл.
				throw new GameOver(GameResult.win(piece));

		if (piece instanceof Wolf) {
			Piece rabbit = piece.getEnemies().get(0);

			for (Square s : rabbit.square.near())
				if (s.isEmpty())
					return; // Зайцу есть куда пойти.
			
			// Зайцу пойти некуда. Волки выиграли.
			throw new GameOver(GameResult.win(piece));
		}
	}

	@Override
	public void undoMove() {
		piece.moveTo(source);
	}

	@Override
	public String toString() {
		return "" + piece + source + "-" + target;
	}

	@Override
	public Square getSource() {
		return source;
	}

	@Override
	public Square getTarget() {
		return target;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

}
