package rabbit.players;

import game.core.Move;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class WolfPlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Волк";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    /**
     * @return может ли играть белыми фигурами.
     */
    @Override
    public boolean isWhitePlayer() {
        return false;
    }

    @Override
    public Image getImage() {
        return WolfRabbitImages.wolfImage;
    }

    @Override
    int getWeight(Move m2) {
        return 0;
    }
}
