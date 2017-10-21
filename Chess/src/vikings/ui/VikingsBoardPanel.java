package vikings.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.GreenBoard;
import game.ui.listners.MovePieceListener;

import vikings.Vikings;
import vikings.pieces.Viking;
import vikings.pieces.Сyning;
import vikings.ui.images.VikingImages;

/**
 * Доска для игры 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB">Викинги (Хнефатафл, Тавлеи) </a>.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class VikingsBoardPanel extends GreenBoard {
	public VikingsBoardPanel(Composite parent, int boardSize) {
		super(parent, Vikings.getInitBoard(boardSize));
		
		listener = new MovePieceListener(this) {
			@Override
			public Image getPieceImage(Piece piece, PieceColor color) {
				return VikingsBoardPanel.this.getPieceImage(piece, color);
			}
		};
	}

	@Override
	public Image getPieceImage(Piece piece) {
		return getPieceImage(piece, piece.getColor());
	}

	private Image getPieceImage(Piece piece, PieceColor color) {
		if (piece instanceof Viking)
			return color == PieceColor.WHITE 
				? VikingImages.imageVikingWhite
				: VikingImages.imageVikingBlack;
		
		if (piece instanceof Сyning)
			return color == PieceColor.WHITE 
				? VikingImages.imageСyningWhite
				: VikingImages.imageСyningBlack;
		
		return null;
	}
}
