package halma.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;
import game.players.MovePiecePlayer;
import halma.Halma;

/**
 * Ants - алгоритм "Муравьи".
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Ants extends MovePiecePlayer {
	@Override
	public String getName() {
		return "Муравьи";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
		
		if (correctMoves.isEmpty())
			return;

		// Случайным образом переставим ходы в списке
		// что бы не играть всегда одну и ту же игру.
		Collections.shuffle(correctMoves);
		
		// Сначала приоритет ходам с более длинным шагом (на большее число клеток).
		// Затем среди выбранных с длинным шагом - ход отстающими фигурами.
		Comparator<ITransferMove> comparator = 
				maxStep.thenComparing(fromBack).thenComparing(random);
		
		Move bestMove = correctMoves
			.stream()
			.map(m -> (ITransferMove) m)
			.sorted(comparator)
			.findFirst()
			.get();
		
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
	
		PieceColor moveColor = bestMove.getPiece().getColor();
		if (Halma.getScore(board, moveColor) <= 40) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			
			GameResult result = GameResult.win(moveColor);
			
			board.history.setResult(result);
			
			throw new GameOver(result);
		}
			
			
		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > 300) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
			
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	/**
	 * Выбор случайного хода по случайному числу
	 * в диапазоне от -1 до +1.
	 * 
	 */
	Comparator<? super ITransferMove> random = 
		(move1, move2) ->  (int) Math.round((2 * Math.random() - 1));

	/**
	 * Приоритет у хода делающего больший шаг к противоположному углу доски. 
	 */
	Comparator<ITransferMove> maxStep = 
		(move1, move2) -> {
			// Направление шага.
			int dir = move1.getPiece().isWhite() ? 1 : -1;
			
			int step1 = move1.getSource().shift( move1.getTarget() );
			int step2 = move2.getSource().shift( move2.getTarget() );
			
			return dir * (step2 - step1);
		};

	/**
	 * Приоритет у хода с более дальней позиции (отстающими фигурами).</br>
	 * Тогда будет больше фигур пригодных для "перепрыгивания".
	 */
	Comparator<ITransferMove> fromBack = 
		(move1, move2) -> {
			Square goal = Halma.getPieceGoal(move1.getPiece());
	
			// Расстояние до клетки - цели.
			int distance1 = goal.distance(move1.getSource());
			int distance2 = goal.distance(move2.getSource());
			
			return Math.abs(distance2) - Math.abs(distance1);
	};
}