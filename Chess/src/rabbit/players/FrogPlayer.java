package rabbit.players;

import game.core.Move;
import rabbit.ui.images.WolfRabbitImages;

public class FrogPlayer extends WolfRabbitPlayer {
	
	{ image = WolfRabbitImages.frogImage; }
	
    @Override
    public String getName() {
        return "Лягушка";
    }

    @Override
    public String getAuthorName() {
        return "";
    }

    @Override
    int getWeight(Move m2) {
        return 0;
    }
    
    @Override
	public boolean isBlackPlayer() { return false; }
}
