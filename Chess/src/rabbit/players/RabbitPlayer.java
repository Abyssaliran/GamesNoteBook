package rabbit.players;

import game.core.Move;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class RabbitPlayer extends WolfRabbitPlayer {
	@Override
    public String getName() {
        return "Заяц";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    @Override
    public Image getImage() {
        return WolfRabbitImages.rabbitImage;
    }

    @Override
	public boolean isBlackPlayer() { return false; }

    @Override
    int getWeight(Move m) {
        return 0;
    }
}
