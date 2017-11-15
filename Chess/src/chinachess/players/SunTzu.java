package chinachess.players;

import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.PieceColor;
import game.players.MovePiecePlayer;

/**
 * Сунь-Цзы - автор знаменитого трактата о военной стратегии «Искусство войны».<br>
 * <a href="http://militera.lib.ru/science/sun-tszy/01.html">Сунь-Цзы. Искусство войны</a>
 */
public class SunTzu extends MovePiecePlayer {
	@Override
	public String getName() {
		return "Сунь-Цзы";
	}

	@Override
	public String getAuthorName() {
		return "Дмитрив Ярослав";
	}
	
	/**
	 * Выдать случайную фигуру из списка фигур.
	 * @param moves - список фигур.
	 * @return фигура выбранная случайным образом.
	 */
	private Move getRandomMove(List<Move> moves) {
		int random = (int) (Math.random() * moves.size());
		return moves.get(random);
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);
		
//		if (correctMoves.isEmpty()) // Пат.
//			throw new GameOver(GameResult.DRAWN);
		
		if (correctMoves.isEmpty())
			return;

		// Пока делает случайный ход.
		Move randomMove = getRandomMove(correctMoves);
		
		try { randomMove.doMove(); } 
		catch (GameOver e) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.addMove(randomMove);
			board.history.setResult(e.result);
			
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			
			throw new GameOver(e.result);
		}
		
		// Сохраняем ход в истории игры.
		board.history.addMove(randomMove);

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
