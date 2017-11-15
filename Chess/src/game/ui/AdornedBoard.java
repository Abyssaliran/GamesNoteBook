/**
 * 
 */
package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import game.ui.images.GameImages;

/**
 * Доска с обозначениями для горизонталей и вертикалей (номер или буква).
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class AdornedBoard extends Canvas {
	private static final Font fontSmall = new Font(Display.getCurrent(), "mono", 10,	SWT.BOLD);
	private static final Font fontLarge = new Font(Display.getCurrent(), "mono", 12,	SWT.BOLD);

	private static Font font = fontSmall;

	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private int nV, nH; 

	/**
	 * Нумерация вертикалей и горизонталей доски.
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	private class BoardAdorns extends Canvas {

		/**
		 * Поле с обозначениями для горизонталей и вертикалей (номер или буква)
		 * 
		 * @param parent
		 *            родительский управляющий элемент.
		 * @param n
		 *            сколько колонок или строк
		 * @param isVertical
		 *            вертикально или горизотельно расположены надписи?
		 * @param isInverted
		 *            в порядке убывания или возрастания идут обозначения?
		 * @param isNumbers
		 *            обозначения цифры или буквы?
		 */
		public BoardAdorns(Composite parent, int n, boolean isVertical,
				boolean isInverted, boolean isNumbers) {
			super(parent, SWT.TRANSPARENT);

			setBackgroundMode(SWT.INHERIT_DEFAULT);
			
			GridLayout layout = isVertical 
					? new GridLayout( 1, true)
					: new GridLayout(nV, true);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.marginTop = 0;
			layout.marginLeft = 0;
			layout.marginRight = 0;
			layout.marginBottom = 0;
			setLayout(layout);

			GridData data = new GridData(SWT.CENTER, SWT.CENTER, true, true);

			int style = SWT.CENTER | SWT.TRANSPARENT;

			for (int k = 1; k <= n; k++) {
				int start = isInverted ? n + 1 : 0;
				int delta = isInverted ? -1 : 1;

				int i = start + delta * k;
				String text = ""
						+ (isNumbers ? i : alphabet.substring(i - 1, i));

				Label adorn = new Label(this, style);
				adorn.setFont(font);
				adorn.setText(text);
				adorn.setBackground(null);
				adorn.setLayoutData(data);
			}
		}
	}

	/**
	 * Пустое прозрачное поле.
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	private class EmptyAdorn extends Canvas {
		public EmptyAdorn(Composite parent) {
			super(parent, SWT.TRANSPARENT);
			setLayout(new FillLayout(SWT.HORIZONTAL));
		}
	}

	private Canvas owner;
	
	/**
	 * Создать доску с обозначениями для горизонталей и вертикалей (номер или
	 * буква).
	 * 
	 * @param parent
	 */
	public AdornedBoard(Composite parent) {
		super(parent, SWT.BORDER_SOLID);
	}

	public AdornedBoard(Composite parent, Color color) {
		super(parent, SWT.BORDER_SOLID);
		setBackground(color);
	}

	/**
	 * Встроить в доску с нумераций вертикалей и горизонталей доску с клетками
	 * на доске. Для этих клеток будет выполняться нумерация.
	 * 
	 * @param boardPanel
	 *            - встраиваемая доска.
	 */
	public void insertSquares(GameBoard boardPanel) {
		nV = boardPanel.board.nV;
		nH = boardPanel.board.nH;
		
		font = ((nV > 8) || (nH > 8)) ? fontSmall : fontLarge;
				
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		
		owner = new Canvas(this, SWT.BORDER);
		owner.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		initMainPanel(owner, boardPanel);
	}

	/**
	 * @param owner
	 * @param adornedControl
	 */
	private void initMainPanel(Canvas owner, GameBoard adornedControl) {
		// Сетевая планировка доски 3х3 ячейки.
		GridLayout layout = new GridLayout(3, false);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginBottom = 0;
		owner.setLayout(layout);

		owner.setBackgroundImage(GameImages.woodLight);

		GridData data;

		//
		// 1-я строка сетки.
		//
		data = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
		new EmptyAdorn(owner).setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.BOTTOM, false, false);
		new BoardAdorns(owner, nV, false, false, false).setLayoutData(data);

		data = new GridData(SWT.LEFT, SWT.BOTTOM, false, false);
		new EmptyAdorn(owner).setLayoutData(data);

		//
		// 2-я строка сетки.
		//
		data = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		new BoardAdorns(owner, nH, true, true, true).setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		adornedControl.setParent(owner);
		adornedControl.setLayoutData(data);

		data = new GridData(SWT.LEFT, SWT.FILL, false, false);
		new BoardAdorns(owner, nH, true, true, true).setLayoutData(data);

		//
		// 3-я строка сетки.
		//
		data = new GridData(SWT.RIGHT, SWT.TOP, false, false);
		new EmptyAdorn(owner).setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.TOP, false, false);
		new BoardAdorns(owner, nV, false, false, false).setLayoutData(data);

		data = new GridData(SWT.LEFT, SWT.TOP, false, false);
		new EmptyAdorn(owner).setLayoutData(data);
	}
}
