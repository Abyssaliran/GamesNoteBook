package backgammon.moves;

import backgammon.pieces.BackgammonGroup;
import backgammon.pieces.Stone;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

public class SimpleMove implements ITransferMove {
	/**
	 * Фигура которая делает ход.
	 */
	protected BackgammonGroup piece;
	
	protected Square source;
	protected Square target;
	
	public SimpleMove(Square source, Square target) {
		this.source = source;
		this.target = target;

		piece = (BackgammonGroup) source.getPiece();
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

	@Override
	public void doMove() {
		doMove(source, target);
	}
	
	@Override
	public void undoMove() {
		doMove(target, source);
	}

	static
	public void doMove(Square source, Square target) {
		BackgammonGroup sourceGroup = (BackgammonGroup) source.getPiece();
		
		Stone stone = sourceGroup.pushStone();

		if (sourceGroup.isEmpty())
			sourceGroup.remove();
		
		BackgammonGroup targetGroup;

		if (target.isEmpty())
			targetGroup = new BackgammonGroup(target, stone);
		else {
			targetGroup = (BackgammonGroup) target.getPiece();
			targetGroup.add(stone);
		}
		target.setPiece(targetGroup);
	}

	@Override
	public String toString() {
		return "" + source + "-" + target;
	}
}