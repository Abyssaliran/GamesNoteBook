package rabbit.players;

import game.core.Move;
import rabbit.ui.images.WolfRabbitImages;

public class WolfPlayer extends WolfRabbitPlayer {

	public WolfPlayer() {
		image = WolfRabbitImages.wolfImage;
	}

	@Override
	public String getName() {
		return "Волк";
	}

	@Override
	public String getAuthorName() {
		return "";
	}

	@Override
	int getWeight(Move m2) {
		return 0;
	}

	/**
	 * @return может ли играть белыми фигурами.
	 */
	public boolean isWhitePlayer() {
		return false;
	}
}
