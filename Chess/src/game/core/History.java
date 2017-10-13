package game.core;

import java.util.ArrayList;
import java.util.List;

/**
 * История игры (список ходов сделанных фигурами на доске).
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class History {
	/**
	 * Номер текущего хода на доске.
	 */
	int currentMove = -1;
	
	/**
	 * Список ходов сделанных на доске.
	 */
	List<Move> moves = new ArrayList<>();
}
