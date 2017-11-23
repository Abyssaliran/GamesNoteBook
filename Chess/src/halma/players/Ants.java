package halma.players;

import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;
import game.players.MovePiecePlayer;

/**
 * Ants - муравьи.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Ants extends MovePiecePlayer{
	private Comparator<? super Move> movesSorter = new AntsBrain();

	@Override
	public String getName() {
		return "Муравьи";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
		
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
 * Алгоритм определения лучших ходов у Ants.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class AntsBrain implements Comparator<Move> {
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
		ITransferMove transfer = (ITransferMove) move;
		
		Square source = transfer.getSource();
		Square target = transfer.getTarget();
		Piece thePiece = source.getPiece();

		Square goal = getPieceGoal(thePiece);
		
		return getMaxDistance(target) - distance(goal, target);
	}
	
	/**
	 * Максимальное расстояние между клетками на доске.
	 *  
	 * @param square - одна из клеток доски.
	 * @return
	 */
	private int getMaxDistance(Square square) {
		Board board = square.getBoard();
		
		return board.nH + board.nV + 1;
	}

	/**
	 * Направление движения для заданной фигуры.
	 * 
	 * @param piece - заданная фигура.
	 * @return в сторону какой клетки двигаться.
	 */
	private Square getPieceGoal(Piece piece) {
		Board board = piece.square.getBoard();

		return piece.getColor() == PieceColor.BLACK
				? board.getSquare(0, 0)
				: board.getSquare(board.nV-1, board.nH-1);
	}

	/**
	 * Выдать расстояние между клетками.
	 * @param s1 
	 * @param s2
	 * @return
	 */
	protected int distance(Square s1, Square s2) {
		final double dv = Math.abs(s1.v - s2.v);
		final double dh = Math.abs(s1.h - s2.h);
		return (int) (dv + dh);
	}
}