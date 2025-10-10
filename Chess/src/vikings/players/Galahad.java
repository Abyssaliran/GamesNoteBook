package vikings.players;

import game.core.Move;

public class Galahad extends VikingsPlayer {
	@Override
	public String getName() {
		return "Галахад";
	}
	
	@Override
	public String getInfo() {
		return "Cын Ланселота, святой рыцарь, "
				+ "воспитанный монахами.\n"
				+ "Искатель Святого Грааля.";
	}

	@Override
	public String getAuthorName() {
		return "?";
	}
	
	@Override
	public boolean isBlackPlayer() {
		return false;
	}
	
	/**
	 * Задать вес для хода.
	 * 
	 * @param move
	 *            - ход
	 * @return оценка хода.
	 */
	@Override
	public int getWeight(Move move) {
		return 0;
	}
}
