package reversi.players;

import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.PassMove;
import game.players.PutPiecePlayer;

/**
 * Базовый класс для всех игроков в реверси.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class ReversiPlayer extends PutPiecePlayer {

	public ReversiPlayer(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	/**
	 * Находится ли клетка на границе доски.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	protected boolean isBorder(Square s) {
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
	protected boolean isCorner(Square s) {
		Board b = s.getBoard();
		
		if ((s.v == 0) && (s.h == 0)) return true;
		if ((s.v == 0) && (s.h == b.nH-1)) return true;
		if ((s.v == b.nV-1) && (s.h == 0)) return true;
		if ((s.v == b.nV-1) && (s.h == b.nH-1)) return true;
	
		return false;
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		PieceColor enemyColor = Board.getOponentColor(color);
		List<Piece> enemies = board.getPieces(enemyColor);
		
		if (enemies.isEmpty()) {
			// Врагов уже нет. Мы выиграли.
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.win(color));
			
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			
			throw new GameOver( GameResult.win(color) );
		}
		
		List<Move> correctMoves = getCorrectMoves(board, color);
	
		if (correctMoves.isEmpty()){
			// Пропускаем ход.
			Move bestMove = new PassMove();
			
			// Сохраняем ход в истории игры.
			board.history.addMove(bestMove );
	
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			return;
		}
		
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

	abstract public Comparator<? super Move> getComparator();
}