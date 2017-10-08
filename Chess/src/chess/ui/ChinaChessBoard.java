package chess.ui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

public class ChinaChessBoard extends GameBoard {

	public ChinaChessBoard(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	void drawSquare(int squareWidth, int squareHeight, GC gc, int v, int h) {
	}
}
