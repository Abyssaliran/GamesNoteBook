package kalah.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.GameBoard;
import game.ui.GamePanel;
import game.ui.ScorePanel;
import game.ui.images.GameImages;
import game.ui.listeners.PutPieceListener;
import kalah.Heap;
import kalah.Kalah;
import kalah.ui.images.KalahImages;

public class KalahGamePanel extends GamePanel {

	public KalahGamePanel(Composite composite) {
		super(composite, new Kalah());

		KalahBoardPanel gameBoard = new KalahBoardPanel(this, game);
		insertSquares(gameBoard);

		GridData data = new GridData(SWT.FILL, SWT.BOTTOM, false, true);
		data.widthHint = 100;

		ScorePanel sp = new ScorePanel(control, game);
		sp.setLayoutData(data);
	}

	public class KalahBoardPanel extends GameBoard {
		public KalahBoardPanel(Composite composite, Game game) {
			super(composite, game.board);

			// Слушатель мыши для постановки новой фигуры на доску.
			listener = new PutPieceListener(this);
		}

		@Override
		protected void drawBackground(GC gc, Rectangle area) {
			Rectangle bounds = GameImages.woodLight.getBounds();

			gc.drawImage(GameImages.woodLight, 
					0, 0, bounds.width, bounds.height, 
					area.x, area.y, area.width, area.height);
		}

		@Override
		public void drawSquare(GC gc, int v, int h, int squareWidth, int squareHeight) {
			boolean isBlackKalah = (h == 0) && (v == 0);
			boolean isWhiteKalah = (h == 1) && (v == 7);
			boolean isKalah = isBlackKalah || isWhiteKalah;
			
			boolean isBlackEmpty = (h == 0) && (v == 7);
			boolean isWhiteEmpty = (h == 1) && (v == 0);
			boolean isEmpty = isBlackEmpty || isWhiteEmpty;
			
			if (isEmpty)
				return;

			Image image = isKalah ? GameImages.woodDark : GameImages.woodMedium;

			Rectangle bounds = image.getBounds();
			
			gc.drawImage(image, 
			             0, 0, bounds.width, bounds.height, 
				         v * squareWidth, h * squareHeight, 
				         squareWidth, squareHeight);

			gc.setForeground(new Color(null, 0, 0, 0));
			gc.drawRectangle(v * squareWidth, h * squareHeight, squareWidth, squareHeight);
		}

		@Override
		protected void drawPiece(GC gc, int v, int h, int squareWidth, int squareHeight) {
			Heap heap = (Heap) board.getSquare(v, h).getPiece();
			
			if (heap == null)
				return;
			
			int x = v * squareWidth;
			int y = h * squareHeight;
			int w = squareWidth/2;

			Rectangle bounds = KalahImages.ball.getBounds();

			for (int k = 0; k < heap.nBalls; k++) {
				gc.drawImage(KalahImages.ball, 
			             0, 0, bounds.width, bounds.height, 
				         x, y, w, w);
				if ((k+1) % 2 == 0) {
					y += w;
					x = v * squareWidth;
				}
				else x += w;
				
			}
		}
		
		@Override
		public Image getPieceImage(Piece piece, PieceColor color) {
			// TODO Auto-generated method stub
			return null;
		}

		public void initDefaultPosition() {
			// TODO Auto-generated method stub

		}
	}
}
