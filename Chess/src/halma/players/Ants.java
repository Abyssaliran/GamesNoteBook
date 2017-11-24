package halma.players;

import java.util.Collections;
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
	 * в диапазоне от -0.5 до +0.5.
	 * 
	 */
	Comparator<? super ITransferMove> random = 
		(move1, move2) -> (int)(Math.random() - 0.5);

	/**
	 * Приоритет у хода делающего больший шаг к противоположному углу доски. 
	 */
	Comparator<ITransferMove> maxStep = 
		(move1, move2) -> {
			// Направление шага.
			int dir = move1.getPiece().isWhite() ? 1 : -1;
			
			int step1 = shift(move1.getSource(), move1.getTarget());
			int step2 = shift(move2.getSource(), move2.getTarget());
			
			return dir * (step2 - step1);
		};

	/**
	 * Приоритет у хода с более дальней позиции (отстающими фигурами).</br>
	 * Тогда будет больше фигур пригодных для "перепрыгивания".
	 */
	Comparator<ITransferMove> fromBack = 
		(move1, move2) -> {
			Square goal = getPieceGoal(move1.getPiece());
	
			// Расстояние до клетки - цели.
			int distance1 = distance(move1.getSource(), goal);
			int distance2 = distance(move2.getSource(), goal);
			
			return Math.abs(distance2) - Math.abs(distance1);
	};
	
	/**
	 * В сторону какой клетки должна двигаться заданная фигура.
	 * 
	 * @param piece
	 *            - задання фигура.
	 * @return клетка в сторону которой она должна двигаться.
	 */
	private Square getPieceGoal(Piece piece) {
		Board board = piece.square.getBoard();

		return piece.isBlack()
				? board.getSquare(0, 0)
				: board.getSquare(board.nV-1, board.nH-1);
	}

	/**
	 * Выдать смещение между клетками - сумма смещений по вертикали и горизонтали. 
	 * Смещение между клетками может быть отрицательным.
	 * 
	 * @param source
	 *            - клетка откуда идет фигура.
	 * @param target
	 *            - клетка куда идет фигура.
	 * @return смещение между клетками target и source.
	 */
	public int shift(Square source, Square target) {
		int dv = target.v - source.v;
		int dh = target.h - source.h;
		
		return dv + dh;
	}
	
	/**
	 * Выдать расстояние между клетками доски - 
	 * сумма расстояний по вертикали и горизонтали.
	 * 
	 * @param source
	 *            - клетка откуда идет фигура.
	 * @param target
	 *            - клетка куда идет фигура.
	 * @return Расстояние между клетками <b>target</b> и <b>source</b>.
	 */
	public int distance(Square source, Square target) {
		int dv = target.v - source.v;
		int dh = target.h - source.h;
		
		return Math.abs(dv) + Math.abs(dh);
	}
}