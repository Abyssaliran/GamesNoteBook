package chinachess.players;

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
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import game.players.MovePiecePlayer;
import chinachess.pieces.King;
import chinachess.players.PlayerBrain;

/**
 * Сунь-Цзы - автор знаменитого трактата о военной стратегии «Искусство войны».<br>
 * <a href="http://militera.lib.ru/science/sun-tszy/01.html">Сунь-Цзы. Искусство войны</a>
 */
public class SunTzu extends MovePiecePlayer {
	
	private Comparator<? super Move> moveSorter = new SunTzuBrain();
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
	@SuppressWarnings("unused")
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

		Collections.shuffle(correctMoves);
		
		
		correctMoves.sort(moveSorter);
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
* Алгоритм определения лучших ходов у создателя трактата "Искусство войны" Сунь-Тзы.
* 
* @author 
*/
class SunTzuBrain extends PlayerBrain implements Comparator<Move> {
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

		if (move instanceof ICaptureMove) {
			// Ход - взятие фигуры врага.
			ICaptureMove capture = (ICaptureMove) move;
			
			Square capturedSquare = capture.getCaptured().get(0);
			Piece  capturedPiece  = capturedSquare.getPiece();
			
			// У захвата короля врага наивысший приоритет.
			if (capturedPiece instanceof King)
				return 1000;
			
			// Пока берем любую фигуру.
			return 999;
		}
		
		// Из всех ходов без взятия фигуры врага лучший ход
		// который максимально приближает к королю врага.
		King enemyKing = getEnemyKing(thePiece);
		int stepWeight = MAX_DISTANCE - distance(target, enemyKing.square);
		
		return stepWeight; 
//		return getSquareWeight(target);
	}
}

