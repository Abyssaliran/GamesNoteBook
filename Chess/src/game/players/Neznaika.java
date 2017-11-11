package game.players;

import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.Move;
import game.core.PieceColor;

/**
 * Незнайка - простой игрок для игр в которых передвигают фигуры. 
 * Он случайным образом выбирает ход из всех допустимых ходов.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Neznaika extends MovePiecePlayer {
	@Override
	public String getName() {
		return "Neznaika";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
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
	public void doMove(Board board, PieceColor color) {
		List<Move> correctMoves = getCorrectMoves(board, color);
		
//		if (correctMoves.isEmpty()) // Пат.
//			throw new GameOver(GameResult.DRAWN);
		
		if (correctMoves.isEmpty())
			return;

		// Незнайка делает случайный ход.
		Move randomMove = getRandomMove(correctMoves);
		
		try { randomMove.doMove(); } 
		catch (GameOver e) {
			// Сохраняем последний ход и 
			// результат игры в истории игры.
			board.history.addMove(randomMove);
			board.history.setResult(e.result);
			
			// Просим показать ход и результат игры.
			board.setBoardChanged();
			return;
		}
		
		// Сохраняем ход в истории игры.
		board.history.addMove(randomMove);
	
		// Передаем ход противнику.
		board.changeMoveColor();
	}

	@Override
	public String toString() {
		return getName();
	}
}
