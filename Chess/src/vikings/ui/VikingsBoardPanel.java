package vikings.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import game.core.Piece;
import game.core.PieceColor;
import game.ui.GameBoard;
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
public class VikingsBoardPanel extends GameBoard {
	private static final Color LINE_COLOR = new Color(null, 0,   0, 0);
	private static final Color FILL_COLOR = new Color(null, 220, 134, 21);

	public VikingsBoardPanel(Composite parent, int boardSize) {
		super(parent, Vikings.getInitBoard(boardSize));
	}

	@Override
	public Image getPieceImage(Piece piece) {
		if (piece instanceof Viking)
			return piece.getColor() == PieceColor.WHITE 
				? VikingImages.imageVikingWhite
				: VikingImages.imageVikingBlack;
		
		if (piece instanceof Сyning)
			return piece.getColor() == PieceColor.WHITE 
				? VikingImages.imageСyningWhite
				: VikingImages.imageСyningBlack;

		return null;
	}

	@Override
	protected void drawBackground(GC gc, Rectangle area) {
		gc.setBackground(FILL_COLOR);
		gc.fillRectangle(area);
	}

	@Override
	
	public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
		gc.setForeground(LINE_COLOR);
		gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
	
		//TODO Vikings Дорисовать особые клетки доски: трон короля в центре и угловые клетки
		/*	Нарисовал клетки в углах и середине доски.
		 * 	Возможно это не самый оптимальный способ, но решение пришло только такое.
		 * 	Спасибо за обратную связь, уверен что существует более качественное решение.
		 * 	Какие задания можно еще выполнить?
		 */	
		
		gc.drawLine(0, 0, squareWidth, squareHeight);
		gc.drawLine(0, squareHeight, squareWidth, 0);
		
		gc.drawLine((board.nV-1)*squareWidth, 0, board.nV*squareWidth, squareHeight);
		gc.drawLine(board.nV*squareWidth, 0, (board.nV-1)*squareWidth, squareHeight);
		
		gc.drawLine(0, (board.nH-1)*squareHeight, squareWidth, board.nH*squareHeight);
		gc.drawLine(0, board.nH*squareHeight, squareWidth, (board.nH-1)*squareHeight);
		
		gc.drawLine((board.nH-1)*squareWidth,(board.nV-1)*squareHeight,board.nH*squareWidth,board.nV*squareHeight);
		gc.drawLine((board.nH-1)*squareWidth,board.nV*squareHeight,board.nH*squareWidth,(board.nV-1)*squareHeight);
		
		gc.drawLine((board.nH/2)*squareWidth,(board.nV/2)*squareHeight,(board.nH/2)*squareWidth+squareWidth,(board.nV/2)*squareHeight+squareHeight);
		gc.drawLine((board.nH/2)*squareWidth,(board.nV/2)*squareHeight+squareHeight,(board.nH/2)*squareWidth+squareWidth,(board.nV/2)*squareHeight);
		
	}
}
