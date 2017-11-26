package reversi.players;

import game.core.Board;
import game.core.IPieceProvider;
import game.core.Square;
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
}