package go.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.IPlayer;
import game.players.Vinni;
import game.ui.AsiaBoard;
import game.ui.GamePanel;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import go.Go;
import go.pieces.GoPiece;
import go.ui.images.GoImages;

/**
 * Панель для игры в Го.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class  GoGamePanel extends GamePanel {
	

	public GoGamePanel(Composite parent, int boardSize) {
		super(parent);
		
		insertSquares( new GoBoardPanel(this, boardSize) );
	}
}
/**
 * Панель для игры <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class GoBoardPanel extends AsiaBoard implements IPieceProvider {
	private static final Color DARK_GREEN = new Color(null, 0, 100, 0);

	public GoBoardPanel(Composite parent, int boardSize) {
		super(parent, Go.getInitBoard(boardSize, boardSize));
		
		Game.addPlayer(Go.class, IPlayer.HOMO_SAPIENCE);
		Game.addPlayer(Go.class, new Vinni(this));
		
		listener = new PutPieceListener(this);
//		mouseMoveListener = new NoPromptListeneromptListener(this);
		
		// Слушатель мыши для выдачи подсказки для клеток - 
		// можно ли ставить фигуру на клетку на доски.
		 mouseMoveListener = new PutPiecePromptListener(this);
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Vinni(this) );
		
		setPromptColor(DARK_GREEN);
	}

	@Override
	public Piece getPiece(Square square, PieceColor color) {
		return new GoPiece(square, color);
	}
	
	@Override
	public Image getPieceImage(Piece piece, PieceColor color) {
		return color == PieceColor.WHITE 
				? GoImages.imageStoneWhite
				: GoImages.imageStoneBlack;
	}
}
