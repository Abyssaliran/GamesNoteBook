package rabbit.players;

import game.core.Move;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class CatPlayer extends WolfRabbitPlayer {
    @Override
    public String getName() {
        return "Кот";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    @Override
    public Image getImage() {
        return WolfRabbitImages.catImage;
    }

    @Override
    public boolean isWhitePlayer() {
        return false;
    }

    @Override
    int getWeight(Move m) {
        return 0;
    }
}
