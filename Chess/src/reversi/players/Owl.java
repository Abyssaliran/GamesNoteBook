package reversi.players;

import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;
import game.players.PutPiecePlayer;

/**
 * Сова - игрок в реверси.
 * Знает что фигуры в углах доски окружить невозможно.
 * Знает что фигуры на краях окружить сложнее чем в центре доски.
 * Выбирает ход с захватом максимального количества фигур врага.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Owl extends PutPiecePlayer {
	private static final Comparator<? super Move> movesSorter = new OwlBrain();

	@Override
	public String getName() {
		return "Сова";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
	}
	
	/**
	 * Сова - игрок в реверси. Знает что фигуры в углах доски окружить
	 * невозможно. Знает что фигуры на краях окружить сложнее чем в центре
	 * доски. Выбирает ход с захватом максимального количества фигур врага.
	 */
	public Owl(IPieceProvider pieceProvider) {
		super(pieceProvider);
		this.pieceProvider = pieceProvider;
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
		if (board.history.getMoves().size() > 80) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
			
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}
}

/**
 * Алгоритм выбора Совой лучшего хода.
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class OwlBrain implements Comparator<Move> {
	@Override
	public int compare(Move m1, Move m2) {
		int w1 = getMoveWeight(m1);
		int w2 = getMoveWeight(m2);
		
		return w2 - w1;
	}

	private int getMoveWeight(Move move) {
		IPutMove putMove = (IPutMove) move;
		
		Square target = putMove.getTarget();
		
		if (isCorner(target))
			return 1000; // Встали в угол.

		if (isBorder(target))
			return 900; // Встали на край доски.
		
		if (move instanceof ICaptureMove) {
			// Ход - взятие фигур врага.
			ICaptureMove capture = (ICaptureMove) move;
			
			// Цена хода - сколько взяли фигур.
			return capture.getCaptured().size();
		}
		
		return 0; 
	}

	/**
	 * Находится ли клетка на границе доски.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	private boolean isBorder(Square s) {
		Board b = s.getBoard();
		
		return (s.v == 0) || 
			   (s.h == 0) || 
			   (s.v == b.nV-1) ||
			   (s.h == b.nH-1) ;
	}

	/**
	 * Находится ли клетка в углу доски.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	private boolean isCorner(Square s) {
		Board b = s.getBoard();
		
		if ((s.v == 0) && (s.h == 0)) return true;
		if ((s.v == 0) && (s.h == b.nH-1)) return true;
		if ((s.v == b.nV-1) && (s.h == 0)) return true;
		if ((s.v == b.nV-1) && (s.h == b.nH-1)) return true;

		return false;
	}

}
