package chess.ui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ChessBoard extends Canvas implements PaintListener {
	public ChessBoard(Composite composite, int style) {
		super(composite, style);
		
		addPaintListener(this);
	}

	@Override
	public void paintControl(PaintEvent e) {
		Rectangle clientArea = getClientArea();
		
		e.gc.drawRectangle(0, 0, clientArea.width - 1, clientArea.height - 1);	}
}
