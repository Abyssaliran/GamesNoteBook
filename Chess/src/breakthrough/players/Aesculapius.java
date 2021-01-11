package breakthrough.players;

import game.core.Move;

/**
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%90%D1%81%D0%BA%D0%BB%D0%B5%D0%BF%D0%B8%D0%B9">
 * Аскле́пий </a>
 */
public class Aesculapius extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Аскле́пий";
	}

	@Override
	public String getAuthorName() {
		return "Тимур Антипин";
	}

	protected int getWeight(Move m2) {
		// TODO  
		return 0;
	}
}
