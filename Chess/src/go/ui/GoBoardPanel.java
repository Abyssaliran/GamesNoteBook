package go.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.IPieceProvider;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.players.Vinni;
import game.ui.AsiaBoard;
import game.ui.listeners.NoPromptListener;
import game.ui.listeners.PutPieceListener;
import game.ui.listeners.PutPiecePromptListener;
import go.Go;
import go.pieces.GoPiece;
import go.ui.images.GoImages;

/**
 * Панель для игры <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GoBoardPanel extends AsiaBoard implements IPieceProvider {
	public GoBoardPanel(Composite parent, int boardSize) {
		super(parent, Go.getInitBoard(boardSize, boardSize));
		
		listener = new PutPieceListener(this);
		mouseMoveListener = new NoPromptListener(this);
		
		// Слушатель мыши для выдачи подсказки для клеток - 
		// можно ли ставить фигуру на клетку на доски.
		mouseMoveListener = new PutPiecePromptListener(this);
		
//		board.setBlackPlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Vinni(this) );
		
		setPromptColor( new Color(null, 0, 100, 0));
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
