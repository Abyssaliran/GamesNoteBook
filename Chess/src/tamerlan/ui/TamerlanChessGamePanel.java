package tamerlan.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.AdornedBoard;
import game.ui.EuropeBoard;
import game.ui.images.GameImages;
import game.ui.listeners.MovePieceListener;
import tamerlan.TamerlanChess;
import tamerlan.pieces.Bishop;
import tamerlan.pieces.Giraffe;
import tamerlan.pieces.King;
import tamerlan.pieces.Knight;
import tamerlan.pieces.Pawn;
import tamerlan.pieces.Queen;
import tamerlan.pieces.Rook;
import tamerlan.pieces.Vizir;
import tamerlan.pieces.WarMachine;
import tamerlan.ui.images.TamerlanChessImages;

/**
 * Панель для игры шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChessGamePanel extends AdornedBoard {

	public TamerlanChessGamePanel(Composite parent) {
		super(parent);
		
		insertSquares( new TamerlanChessBoardPanel(this) );
	}
}
/**
 * Панель для игры в 
 * <a href="https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">Шахматы Тамерлана</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
class TamerlanChessBoardPanel extends EuropeBoard {
	private static Map<Class<? extends Piece>, Image> whites;
	private static Map<Class<? extends Piece>, Image> blacks;
	
	private static Map<PieceColor, Map<Class<? extends Piece>, Image>> pieceImages;

	static {
		whites = new HashMap<>();
		blacks = new HashMap<>();
		
		pieceImages = new HashMap<>();
		pieceImages.put(PieceColor.WHITE, whites);
		pieceImages.put(PieceColor.BLACK, blacks);

		// Инициализируем карту изображений белых фигур.
		//
		whites.put(Pawn.class,   		TamerlanChessImages.imagePawnWhite);
		whites.put(Rook.class,   		TamerlanChessImages.imageRookWhite);
		whites.put(Knight.class, 		TamerlanChessImages.imageKnightWhite);
		whites.put(Bishop.class, 		TamerlanChessImages.imageBishopWhite);
		whites.put(Queen.class,  		TamerlanChessImages.imageQueenWhite);
		whites.put(King.class,   		TamerlanChessImages.imageKingWhite);
		whites.put(Vizir.class,   		TamerlanChessImages.imageVizirWhite);
		whites.put(WarMachine.class,    TamerlanChessImages.imageWarMachineWhite);
		whites.put(Giraffe.class,    	TamerlanChessImages.imageGiraffeWhite);
		
		// Инициализируем карту изображений черных фигур.
		//
		blacks.put(Pawn.class,   		TamerlanChessImages.imagePawnBlack);
		blacks.put(Rook.class,   		TamerlanChessImages.imageRookBlack);
		blacks.put(Knight.class, 		TamerlanChessImages.imageKnightBlack);
		blacks.put(Bishop.class, 		TamerlanChessImages.imageBishopBlack);
		blacks.put(Queen.class,  		TamerlanChessImages.imageQueenBlack);
		blacks.put(King.class,   		TamerlanChessImages.imageKingBlack);
		blacks.put(Vizir.class,   		TamerlanChessImages.imageVizirBlack);
		blacks.put(WarMachine.class,    TamerlanChessImages.imageWarMachineBlack);
		blacks.put(Giraffe.class,    	TamerlanChessImages.imageGiraffeBlack);	
	}

	public TamerlanChessBoardPanel(Composite composite) {
		super(composite, TamerlanChess.getInitBoard());
		
		listener = new MovePieceListener(this) {
			@Override
			public Image getPieceImage(Piece piece, PieceColor color) {
				return TamerlanChessBoardPanel.this.getPieceImage(piece, color);
			}
		};
	}

	public Image getPieceImage(Piece piece) {
		return getPieceImage(piece, piece.getColor());
	}

	private Image getPieceImage(Piece piece, PieceColor color) {
		return pieceImages
				.get(color)
				.get( piece.getClass() );
	}
	
	@Override
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		boolean isWhiteSquare = ((v + h) % 2 == 0);

		Rectangle bounds = GameImages.woodDark.getBounds();
		
		if (h == 0 || h == 1 || h == 12 || h == 13) { // выделяем клетки для отображения захваченных фигур
			gc.drawImage(GameImages.woodLight, 
		             0, 0, bounds.width, bounds.height, 
			         v * squareWidth, h * squareHeight, 
			         squareWidth, squareHeight);
		}
		else if (!isWhiteSquare) {
			gc.drawImage(GameImages.woodDark, 
			             0, 0, bounds.width, bounds.height, 
				         v * squareWidth, h * squareHeight, 
				         squareWidth, squareHeight);
		}
		gc.setForeground(new Color(null, 0, 0, 0));
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	}
}
