package game.players;

import game.core.Board;
import game.core.PieceColor;

/**
 * Интерфейс для игроков в шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IPlayer {
	/**
	 * Простейшая реализация.
	 * Все действия на доске делает человек. 
	 */
	IPlayer HOMO_SAPIENCE = new IPlayer() {
		@Override
		public String getName() {
			return "Homo sapience";
		}

		@Override
		public void doMove(Board board, PieceColor color) {}
		
		@Override
		public String toString() {
			return getName();
		}
	};
	
	/**
	 * Выдать имя игрока для отображения имени на панели игры.
	 * 
	 * @return - имя игрока.
	 */
	String getName();
	
	
	/**
	 * Сделать ход на доске фигурой заданного цвета.
	 * 
	 * @param board
	 *            - доска для хода.
	 * @param color
	 *            - фигура какого цвета должна сделать ход.
	 */
	void doMove(Board board, PieceColor color);
}
