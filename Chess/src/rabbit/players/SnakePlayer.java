package rabbit.players;

import game.core.Move;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class SnakePlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Змея";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    @Override
	public boolean isWhitePlayer() { return false; }

    @Override
    public Image getImage() {
        return WolfRabbitImages.snakeImage;
    }

    @Override
    int getWeight(Move m) {
        return 0;
    }

}
