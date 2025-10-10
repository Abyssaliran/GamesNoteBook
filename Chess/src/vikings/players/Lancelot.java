package vikings.players;

import game.core.Move;

public class Lancelot extends VikingsPlayer {
	@Override
	public String getName() {
		return "Ланселот";
	}
	
	@Override
	public String getInfo() {
		return "Непобедимый рыцарь. Убил огнедышащего дракона";
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