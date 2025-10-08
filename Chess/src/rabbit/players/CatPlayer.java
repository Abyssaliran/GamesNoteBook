package rabbit.players;

import game.core.Move;
import rabbit.ui.images.WolfRabbitImages;

public class CatPlayer extends WolfRabbitPlayer {
	
	{ image = WolfRabbitImages.catImage; }
	
    @Override
    public String getName() {
        return "Кот";
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
	public boolean isWhitePlayer() { return false; }
}
