package attract.players;

import attract.ui.images.AttractImages;
import game.core.*;
import org.eclipse.swt.graphics.Image;

/**
 * TODO Фань Чжаньхун
 */
public class BearPlayer extends AttractPlayer {

    public BearPlayer(IPieceProvider pieceProvider) {
        super(pieceProvider);
    }

    @Override
    public String getName() {
        return "Медведь";
    }

    @Override
    public String getAuthorName() {
        return "Фань Чжаньхун";
    }

    @Override
    public boolean isBlackPlayer() {
        return false;
    }

    @Override
    public Image getImage() {
        return AttractImages.bearImage;
    }

    @Override
    public String getInfo() {
        return "живет в России";
    }

    @Override
    protected int getWeight(Move move) {
        return 0;
    }
}
