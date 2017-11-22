package checkers.players;

import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.moves.ICaptureMove;
import game.players.MovePiecePlayer;

/**
 * Знайка - не пускает фигуры противника в дамки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Znaika extends MovePiecePlayer{
	private Comparator<? super Move> movesSorter = new ZnaikaBrain();

	@Override
	public String getName() {
		return "Знайка";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
		
//		if (correctMoves.isEmpty()) // Пат.
//			throw new GameOver(GameResult.DRAWN);
		
		if (correctMoves.isEmpty())
			return;

		correctMoves.sort(movesSorter);
		Move bestMove = correctMoves.get(0);
		
		try { bestMove.doMove(); } 
		catch (GameOver e) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.addMove(bestMove);
			board.history.setResult(e.result);
			
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			
			// Распространяем инфрмацию об окончании игры.
			throw new GameOver(e.result);
		}
		
		// Сохраняем ход в истории игры.
		board.history.addMove(bestMove);

		// Просим обозревателей доски показать 
		// положение на доске, сделанный ход и 
		// результат игры.
		board.setBoardChanged();
	
		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > 80) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
			
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	@Override
	public String toString() {
		return getName();
	}
}


/**
 * Алгоритм определения лучших ходов у турецкого султана Баязида.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class ZnaikaBrain implements Comparator<Move> {
	@Override
	public int compare(Move m1, Move m2) {
		int w1 = getMoveWeight(m1);
		int w2 = getMoveWeight(m2);
		
		return w2 - w1;
	}

	/**
	 * Задать вес для хода.
	 * @param move - ход
	 * @return оценка хода.
	 */
	private int getMoveWeight(Move move) {
		if (move instanceof ICaptureMove)
			return 1; 

		Piece p = move.getPiece();
			
		int firstLine = (p.getColor() == PieceColor.BLACK ? 0 : 7);
		return p.square.h == firstLine ? -1 : 1;			
	}
}

