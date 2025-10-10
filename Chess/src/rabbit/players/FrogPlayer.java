package rabbit.players;

import game.core.Move;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class FrogPlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Лягушка";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    @Override
    public Image getImage() {
        return WolfRabbitImages.frogImage;
    }

    @Override
	public boolean isBlackPlayer() { return false; }

    @Override
    int getWeight(Move m) {
        return 0;
    }
}
