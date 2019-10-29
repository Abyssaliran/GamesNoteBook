package game.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Иа ходит всеми фигурами независимо от цвета.
 * Ход выбирает случайно из всех возможных.
 */
public class Ia implements IPlayer {
	private int maxMoves = 80;
	
	public Ia() {}

	public Ia(int maxMoves) {
		this.maxMoves = maxMoves;
	}

	@Override
	public String getName() {
		return "Иа";
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

		// Иа делает случайный ход.
		Collections.shuffle(correctMoves);
		Move randomMove = correctMoves.get(0);
		
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
		if (board.history.getMoves().size() > maxMoves ) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
			
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	/**
	 * Фигура перемещается по доске.
	 * Выдать все корректные ходы на доске фигурой заданного цвета.
	 * 
	 * @param board - доска на которой идет игра.
	 * @param color - цвет фигуры которая должна сделать ход.
	 * @return список допустимых ходов.
	 */
	public List<Move> getCorrectMoves(Board board, PieceColor color) {
		List<Move> correctMoves = new ArrayList<>();
				
		for (Piece p : board.getAllPieces()) {
			// Собрали все клетки-цели на которые допустим ход фигуры р.
			List<Square> targets = board.getSquares()
					.stream()
					.filter(p::isCorrectMove)
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
	public String toString() {
		return getName();
	}
}
