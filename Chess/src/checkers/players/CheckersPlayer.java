package checkers.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.MovePiecePlayer;

/**
 * Базовый класс для всех программ-игроков в шашки.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class CheckersPlayer extends MovePiecePlayer {
	
	@Override
	public List<Move> getCorrectMoves(Board board, PieceColor color) {
		List<Move> correctMoves = new ArrayList<>();
				
		for (Piece p : board.getPieces(color)) {
			// Собрали все клетки-цели на которые допустим ход фигуры р.
			List<Square> targets = board.getSquares()
					.stream()
					.filter(s -> p.isCorrectMove(s))
					.collect( Collectors.toList() );
			
			for (Square target : targets) {
				Square source = p.square;
				Move correctMove = p.makeMove(source, target);
				correctMoves.add( correctMove );
			}				
		}
		
		return correctMoves;
	}
	
	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
		
		if (correctMoves.isEmpty()) {
			PieceColor enemyColor = Board.getOponentColor(color);
			
			// Все враги наши убиты. Мы выиграли.
			if (board.getPieces(enemyColor).isEmpty())
				throw new GameOver( GameResult.win(color) );

			// Все наши фигуры убиты. Мы проиграли.
			if (board.getPieces(color).isEmpty())
				throw new GameOver( GameResult.lost(color) );

			// Все наши фигуры заперты. Мы проиграли.
			throw new GameOver( GameResult.lost(color) );
		}

		// Что бы ходы в играх не повторялись.
		Collections.shuffle(correctMoves);
		
		correctMoves.sort( getComparator() );
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
			
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();

			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	/**
	 * Метод возвращяющий алгоритм для сравнения ходов и выбора лучшего хода.
	 * 
	 * @return алгоритм сравнения ходов.
	 */
	abstract 
	protected Comparator<? super Move> getComparator();
}