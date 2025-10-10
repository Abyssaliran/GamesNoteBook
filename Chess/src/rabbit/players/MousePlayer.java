package rabbit.players;

import game.core.Move;
import game.core.moves.ITransferMove;
import org.eclipse.swt.graphics.Image;
import rabbit.ui.images.WolfRabbitImages;

public class MousePlayer extends WolfRabbitPlayer {
	
	@Override
    public Image getImage() { return WolfRabbitImages.mouseImage; }

    @Override
    public String getName() {
        return "Мышь";
    }

    @Override
    public String getAuthorName() {
        return "?";
    }

    /**
     * @return может ли играть черными фигурами.
     */
    public boolean isBlackPlayer() { return false; }

    @Override
    int getWeight(Move m) {
    	ITransferMove transfer = (ITransferMove) m;
    	
        return 8 - transfer.getTarget().h;
    }
}
