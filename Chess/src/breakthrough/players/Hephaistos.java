package breakthrough.players;

import game.core.Move;

/**
 * TODO Пришлецов Дмитрий
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%93%D0%B5%D1%84%D0%B5%D1%81%D1%82">
 * Гефест</a>
 */
public class Hephaistos extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Гефест";
	}

	@Override
	public String getAuthorName() {
		return "Пришлецов Дмитрий";
	}

	protected int getWeight(Move m2) {
		// TODO  
		return 0;
	}
}
