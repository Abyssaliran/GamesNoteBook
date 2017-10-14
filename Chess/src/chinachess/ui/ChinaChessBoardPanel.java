/**
 * 
 */
package chinachess.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chinachess.ChinaChess;
import game.core.Piece;
import game.ui.AsiaBoardWithCastle;

/**
 * Доска для игры в китайские шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChinaChessBoardPanel extends AsiaBoardWithCastle {

	public ChinaChessBoardPanel(Composite parent) {
		super(parent, ChinaChess.getInitBoard());
	}

	/* (non-Javadoc)
	 * @see game.ui.GameBoard#getPieceImage(game.core.Piece)
	 */
	@Override
	public Image getPieceImage(Piece piece) {
		// TODO Auto-generated method stub
		return null;
	}

}
