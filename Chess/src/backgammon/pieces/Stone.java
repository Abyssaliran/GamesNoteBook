package backgammon.pieces;

import backgammon.BackgammonBoard;
import backgammon.moves.SimpleMove;
import game.core.ITrackPiece;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура для игры в нарды.
 */
public class Stone extends Piece implements ITrackPiece {
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		BackgammonBoard board = (BackgammonBoard) square.getBoard();
		
		int step1 = board.cube1.getValue();
		int step2 = board.cube2.getValue();
		
		PieceColor color = getColor();
		
		//
		// Проверяем клетку куда идем.
		//
		Square target = squares[0];
		
		if (target == square)
			return false;
		
		// Сама фигура пойти на клетку для хранения захваченных фигур 
		// (сдаться в плен) не может.
		if (board.isBar(target))
			return false;
		
		// Пока все фигуры такого же цвета не дома,
		// сбрасывать фигуру с доски нельзя.
		if (board.isForBearing(target) && !board.allInHome(color))
			return false;
		
		// На пустую клетку пойти можно.
		if (target.isEmpty())
			return true;	

		BackgammonGroup targetPiece = (BackgammonGroup)target.getPiece();

		// На клетку со своими фигурами пойти можно.
		if (targetPiece.isFriend(this))
			return true;	
		
		// Врага-одночку можно захватить в плен.
//        if (targetPiece.pieces.size() == 1)
//        	return true;
		
		return false;	
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		return new SimpleMove(this, square, target);
	}

	@Override
	public boolean hasCorrectMoveFrom(Square square) {
		return true;
	}
	
	@Override
	public String toString() {
		return "";
	}
}