package breakthrough.players;

import game.core.Move;

/**
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%90%D1%80%D1%82%D0%B5%D0%BC%D0%B8%D0%B4%D0%B0">
 * Артемида</a>
 *
 */
public class Artemis extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Артемида";
	}

	@Override
	public String getAuthorName() {
		return "????";
	}

	protected int getWeight(Move m2) {
		// TODO  
		return 0;
	}
}
