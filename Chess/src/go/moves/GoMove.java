package go.moves;

import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.IPutMove;

public abstract class GoMove implements IPutMove {
	protected void checkGameEnd(Piece piece) throws GameOver {
		// Проверим остались ли пустые клетки на доске.
		Board board = piece.square.getBoard();
		PieceColor myColor = piece.getColor();
		
		List<Square> empties = board.getEmptySquares();
		if (!empties.isEmpty()) return;
		
		// Подсчитаем количество белых и черных.
		// Выдадим результат игры.
		int enemies = piece.getEnemies().size();
		int friends = piece.getFriends().size();
	
		if (enemies == friends)
			throw new GameOver(GameResult.DRAWN);
	
		boolean iWin = (enemies < friends);
	
		GameResult result = GameResult.UNKNOWN;
	
		if (myColor == PieceColor.BLACK)
			result = iWin ? GameResult.BLACK_WIN : GameResult.WHITE_WIN;
		else
			result = iWin ? GameResult.WHITE_WIN : GameResult.BLACK_WIN;
	
		throw new GameOver(result);
	}

}