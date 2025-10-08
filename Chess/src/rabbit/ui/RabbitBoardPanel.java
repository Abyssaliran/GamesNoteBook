package rabbit.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.players.IPlayer;
import game.ui.EuropeBoard;
import game.ui.listeners.MovePieceListener;
import rabbit.players.WolfRabbitPlayer;
import rabbit.ui.images.WolfRabbitImages;

public class RabbitBoardPanel extends EuropeBoard {
	public RabbitBoardPanel(Composite composite, Game game) {
		super(composite, game.board);

		listener = new MovePieceListener(this);
	}

	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		IPlayer player = color == PieceColor.WHITE ? board.getWhitePlayer() : board.getBlackPlayer();

		if (player instanceof WolfRabbitPlayer) {
			WolfRabbitPlayer rwPlayer = (WolfRabbitPlayer) player;

			return rwPlayer.image;
		}
		
		return color == PieceColor.WHITE ? WolfRabbitImages.rabbitImage : WolfRabbitImages.wolfImage;
	}
}