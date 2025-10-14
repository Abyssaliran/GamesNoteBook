package attract.players;

import game.core.*;
import game.players.PutPiecePlayer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AttractPlayer extends PutPiecePlayer {
	private static final int MAX_MOVES = 180;

	private final Comparator<? super Move> brain = (m1, m2) -> getWeight(m2) - getWeight(m1);

	public AttractPlayer(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
	
		if (correctMoves.isEmpty())
			throw new GameOver(GameResult.DRAWN);
	
		Collections.shuffle(correctMoves);
	
		// Буратино выбирает лучший ход.
		correctMoves.sort(brain);
		
		Move bestMove = correctMoves.get(0);
	
		try {
			bestMove.doMove();
		} catch (GameOver e) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.addMove(bestMove);
			board.history.setResult(e.result);
	
			// Просим обозревателей доски показать
			// положение на доске, сделанный ход и
			// результат игры.
			board.setBoardChanged();
	
			throw new GameOver(GameResult.DRAWN);
		}
	
		// Сохраняем ход в истории игры.
		board.history.addMove(bestMove);
	
		// Просим обозревателей доски показать
		// положение на доске, сделанный ход и
		// результат игры.
		board.setBoardChanged();
	
		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > MAX_MOVES) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
	
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	protected abstract int getWeight(Move m2);
}
