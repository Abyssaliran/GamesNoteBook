package vikings.players;

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
import vikings.moves.Capture;
import vikings.pieces.VikingsPiece;
import vikings.pieces.Сyning;

/**
 * William - викинг в Нормандии 1066 год.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class William extends MovePiecePlayer{
	private Comparator<? super Move> movesSorter = new WilliamBrain();

	@Override
	public String getName() {
		return "Вильгельм I (Нормандия)";
	}

	@Override
	public String getAuthorName() {
		return "Заблоцкий";
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
 * Алгоритм определения лучших ходов для Вильгельма.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class WilliamBrain implements Comparator<Move> {
	private static final int MAX_DISTANCE = 20;

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

		int nCaptured = 0;
		
		if (move instanceof ICaptureMove) {
			// Ход - взятие фигур врага.
			Capture capture = (Capture) move;
			
			// Есть ли среди захваченых фигур белвй король?
			List<Piece> captured = capture.getCapturedPieces();
			boolean isKingCapture = captured.stream().anyMatch(p -> p instanceof Сyning);
			
			// Захват вражеского короля получает наибольший приоритет.
			if (isKingCapture)
				return 1000;
			
			// Приоритет у хода с бОльшим количеством взятых фигур.
			nCaptured = captured.size();
		}

		if (thePiece instanceof Сyning) {
			// Ход белым королем.
			List<Square> exits = VikingsPiece.getExits(thePiece);
			
			// Поиск ближайшего выхода.
			Square nearsExit = exits
					.stream()
					.min((s1, s2) -> distance(s1, target) - distance(s2, target))
					.get();
			
			// Ход королем к ближайшему выходу получает наибольший приоритет.
			int minDistance = distance(nearsExit, target);
			
			if (minDistance == 0)
				return 1000; // Выход короля - наибольший приоритет.
			
			return (MAX_DISTANCE - minDistance);
		}
		
		return nCaptured; 
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