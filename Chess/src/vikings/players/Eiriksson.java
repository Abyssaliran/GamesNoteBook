package vikings.players;

import game.core.Move;

/**
 * Eiriksson - Основал скандинавское поселение Винланд в Америке
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Eiriksson extends VikingsPlayer {
	@Override
	public String getName() {
		return "Э́рикссон";
	}
	
	@Override
	public String getInfo() {
		return "В Исландии около 1020 года.\n"
				+ "Основал скандинавское поселение Винланд в Америке";
	}

	@Override
	public String getAuthorName() {
		return "";
	}
	
	@Override
	public boolean isWhitePlayer() {
		return false;
	}
	
	/**
	 * Задать вес для хода.
	 * @param move - ход
	 * @return оценка хода.
	 */
	public int getWeight(Move move) {
		return 0; 
	}
}