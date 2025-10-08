package rabbit.players;

import game.core.Move;
import game.core.moves.ITransferMove;
import rabbit.ui.images.WolfRabbitImages;

public class MousePlayer extends WolfRabbitPlayer {
	
	{ image = WolfRabbitImages.mouseImage; }

    @Override
    public String getName() {
        return "Мышь";
    }

    @Override
    public String getAuthorName() {
        return "";
    }

    @Override
    int getWeight(Move m) {
    	ITransferMove transfer = (ITransferMove) m;
    	
        return 8 - transfer.getTarget().h;
    }
    
	
	/**
	 * @return может ли играть черными фигурами.
	 */
	public boolean isBlackPlayer() { return false; }
}
