package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Составная панель для настольной игры:
 * 	<ul><li>
 * 		доска с клетками
 * 	 <li></li>
 * 		панель истории партии (список ходов)
 *	 <li></li>
 *		управляющая панель (выбор игроков, размера доски, счет, ...)
 * </li></ul>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GamePanel extends Composite {
	protected AdornedBoard adorned;
	protected MovesJornal jornal;

	public GamePanel(Composite parent) {
		super(parent, SWT.TRANSPARENT);
		setLayout( new GridLayout(2, false) );
		
		adorned = new AdornedBoard(this);
		adorned.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public GamePanel(Composite parent, Color color) {
		super(parent, SWT.TRANSPARENT);
	}

	protected void insertSquares(GameBoard gameBoard) {
		adorned.insertSquares(gameBoard);
		
		jornal = new MovesJornal(this, gameBoard.board.history);
		jornal.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
	}
}