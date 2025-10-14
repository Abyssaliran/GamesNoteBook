package attract.players;

import attract.ui.images.AttractImages;
import game.core.IPieceProvider;
import game.core.Move;
import org.eclipse.swt.graphics.Image;

/**
 * TODO Юй Сыцзэ
 */
public class PandaPlayer extends AttractPlayer {
	public PandaPlayer(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	@Override
	public String getName() {
		return "Панда";
	}

	@Override
	public String getAuthorName() {
		return "Юй Сыцзэ";
	}

	@Override
	public boolean isWhitePlayer() {
		return false;
	}

	@Override
	public Image getImage() {
		return AttractImages.pandaImage;
	}

	@Override
	public String getInfo() {
		return "живет в Китае";
	}

	protected int getWeight(Move m1) {
		return 0;
	}
}