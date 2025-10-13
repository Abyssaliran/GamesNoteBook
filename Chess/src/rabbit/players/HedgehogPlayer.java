package rabbit.players;

import game.core.Move;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class HedgehogPlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Ежик";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    @Override
    public Image getImage() {
        return WolfRabbitImages.hedgehogImage;
    }

    /**
     * @return может ли играть черными фигурами.
     */
    public boolean isBlackPlayer() { return false; }

    @Override
    int getWeight(Move m) {
        return 0;
    }
}
