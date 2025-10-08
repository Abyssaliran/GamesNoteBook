package vikings.players;

import game.core.Move;

/**
 * Зигфрид - В ноябре 885 года Зигфрид осадил Париж
 */
public class Sigfrid extends VikingsPlayer {
	@Override
	public String getName() {
		return "Зи́гфрид";
	}
	
	@Override
	public String getInfo() {
		return "В ноябре 885 года Зигфрид осадил Париж";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
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