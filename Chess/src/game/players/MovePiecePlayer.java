package game.players;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для программ-игроков передвигающих фигуры на доске.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class MovePiecePlayer implements IPlayer {
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
				
		for (Piece p : board.getPieces(color)) {
			List<Square> targets = board.getPieceTargets(p);
			
			for (Square target : targets) {
				Square source = p.square;
				Move correctMove = p.makeMove(source, target);
				correctMoves.add( correctMove );
			}				
		}
		
		return correctMoves;
	}
}