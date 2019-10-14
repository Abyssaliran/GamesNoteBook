package backgammon.pieces;

import java.util.List;

import backgammon.BackgammonBoard;
import backgammon.moves.SimpleMove;
import backgammon.moves.Capture;
import game.core.Group;
import game.core.ITrackPiece;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура для игры в нарды.
 */
public class Stone extends Piece implements ITrackPiece {
	Group<Stone> group;
	
	public Stone(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		BackgammonBoard board = (BackgammonBoard) square.getBoard();
		
		int step1 = board.cube1.getValue();
		int step2 = board.cube2.getValue();
		
		List<Square> way = board.getWay(this);
		
		Square bar = board.getBar4Piece(this);
		
		if (!bar.isEmpty()) {
			// Есть пленные фигуры. Ход возможен только ими.
			BackgammonGroup barGroup = (BackgammonGroup) bar.getPiece();
			
			if (!barGroup.contains(this))
				return false;

			List<Square> wayFromBar = board.getWayFromBar(this);
		}
		
		PieceColor color = getColor();
		Square target = squares[0];
		
//		way.forEach(s -> System.out.format("%s ", s.getPiece()));
//		System.out.println();
		if (color != board.getMoveColor())
			return false;
		
		int i00 = way.indexOf(board.getSquare(0, 0));

		int iSource = way.indexOf(square);
		int iTarget = way.indexOf(target);
		
		if (iTarget <= iSource)
			// Назад фигуры не ходят.
			return false;
		
		if (target == square)
			return false; 
		
		if (iTarget != iSource + step1 && 
				iTarget != iSource + step2 &&
				iTarget != iSource + step1 + step2)
			return false;
	
		//
		// Проверяем клетку куда идем.
		//
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
		// TODO реализовать ход Capture - взятие в плен фигуры противника.
        if (targetPiece.size() == 1)
        	return true;
		
		return false;	
	}

	@Override
	public Move makeMove(Square... squares) {
		Square target = squares[1];
		BackgammonGroup targetPiece = (BackgammonGroup)target.getPiece();
		
		if (!target.isEmpty() && targetPiece.isEnemy(this) && targetPiece.size() == 1)
			return new Capture(square, target);
		
		return new SimpleMove(square, target);
	}

	@Override
	public boolean hasCorrectMoveFrom(Square square) {
		return true;
	}
	
	@Override
	public String toString() {
		return "s[" + square + "]";
	}
}