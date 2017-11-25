package game.ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import game.core.Board;
import game.core.History;
import game.core.Move;
import game.ui.images.GameImages;

/**
 * Журнал для хранения истории ходов игры.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class MovesJornal extends Composite implements Observer {
	private static final Font font = new Font(Display.getCurrent(), "mono", 10, SWT.BOLD);
	private static final Cursor hand = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
	
	private static final Color SELECT_COLOR = new Color(Display.getCurrent(), 217, 173, 124);
	private static final Color HEADER_COLOR = new Color(Display.getCurrent(), 217, 173, 124);
	private static final Color BLACK_COLOR  = new Color(Display.getCurrent(),   0,   0,   0);
	private static final Color PAPER_COLOR  = new Color(Display.getCurrent(), 255, 210,   0);

	/**
	 * История игры.
	 */
	private History history;
	
	/**
	 * Панель для имен игроков
	 */
	private Label headerPanel;
	private MovesPanel movesPanel;
	private Label resultPanel;
	
	/**
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	class MovesPanel extends Composite {

		public MovesPanel(Composite parent, int style) {
			super(parent, style);
		}
		
		public void clear() {
		    Control[] children = getChildren();
		    int length = children.length;
			for (int i = length -1;i>=0;i--) {
		        children[i].dispose();
		    }
		}
		
	}

	/**
	 * Класс для представлени текста хода в истории игры.
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	private class MoveLabel {
		private Move move;
		
		private Label label;

		/**
		 * Управляющий элемент представляющий ход и сам ход в игре. 
		 * 
		 * @param parent родительский элемент для текста (Label).
		 * @param kMove - номер хода.
		 * @param move - ход в игре.
		 */
		public MoveLabel(Composite parent, int kMove, Move move) {
			this.move = move;

			label = new Label(parent, SWT.TRANSPARENT);
			label.setFont(font);

			MouseTrackListener listener = new MouseTrackListener() {
				private Cursor oldCursor;

				@Override
				public void mouseEnter(MouseEvent e) {
					oldCursor = label.getCursor();
					label.setCursor(hand);
				}

				@Override
				public void mouseExit(MouseEvent e) {
					label.setCursor(oldCursor);
				}

				@Override
				public void mouseHover(MouseEvent e) {}
			};
			label.addMouseTrackListener(listener);

			MouseListener mListener = new MouseListener() {
				@Override
				public void mouseDown(MouseEvent e) {
					int n = history.getMoveNumber(MoveLabel.this.move);
					history.toMove(n);
					history.getBoard().setBoardChanged();
				}

				@Override
				public void mouseUp(MouseEvent e) {}

				@Override
				public void mouseDoubleClick(MouseEvent e) {}
			};
			label.addMouseListener(mListener);

			boolean isOdd = ((kMove % 2) == 0);
			String number = (!isOdd ? "" : "" + (1+kMove/2) + ". ");
			
			label.setText("" + number + move);
			label.update();
			label.redraw();
			label.setRedraw(true);
		}
	}

	/**
	 * @param parent - родительский элемент для журнала ходов.
	 * @param history - история игры.
	 */
	public MovesJornal(Composite parent, History history) {
		super(parent, SWT.BORDER);
		setForeground(BLACK_COLOR);
		setBackground(PAPER_COLOR);
		
		this.history = history;
		Board board = history.getBoard();
	
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginBottom = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		
		GridData data;
		
		// 
		// Панель для показа игроков партии и авторов программ.
		//
		data = new GridData(SWT.FILL, SWT.TOP, false, false);
		data.widthHint  = 260;
		data.heightHint =  25;

		headerPanel = new Label(this, SWT.CENTER | SWT.BORDER);
		headerPanel.setBackground(HEADER_COLOR);
		headerPanel.setForeground(BLACK_COLOR);
		headerPanel.setFont(font);
		headerPanel.setLayoutData(data);
		
		setHeaderText(board);
		
		// 
		// Панель для показа ходов в партии.
		//
		data = new GridData(SWT.FILL, SWT.FILL, true, true);

		ScrolledComposite sc = new ScrolledComposite(this, SWT.V_SCROLL | SWT.H_SCROLL);
		sc.setBackgroundImage( GameImages.papiro );
		sc.setLayoutData(data);
	
		movesPanel = new MovesPanel(sc, SWT.TRANSPARENT);
		movesPanel.setLayout( new GridLayout(4, false) );
		sc.setContent(movesPanel);

		//
		// Панель для выдачи результата игры.
		//
		data = new GridData(SWT.FILL, SWT.BOTTOM, false, false);
		data.widthHint  = 260;
		data.heightHint =  25;
		
		resultPanel = new Label(this, SWT.CENTER | SWT.BORDER);
		resultPanel.setFont(font);
		resultPanel.setText("" + history.getResult());
		resultPanel.setBackground(HEADER_COLOR);
		resultPanel.setForeground(BLACK_COLOR);
		resultPanel.setLayoutData(data);

		// Добавляем к доске еще одного обозревателя - панель ходов.
		// При изменении положения фигур на доске или результата партии
		// панель ходов уведомят, что нужно перерисовать историю партии
		// (список ходов партии). 
		board.addObserver(this);
	}

	private void setHeaderText(Board board) {
		String white = board.getWhitePlayer().getName();
		String black = board.getBlackPlayer().getName();
		String title = String.format("%s - %s", white, black);
		
		String aWhite = board.getWhitePlayer().getAuthorName();
		String aBlack = board.getBlackPlayer().getAuthorName();
		String aTitle = String.format("%s против %s", aWhite, aBlack);
		
		headerPanel.setText(title);
		headerPanel.setToolTipText(aTitle);
	}

	@Override
	public void update(Observable board, Object arg) {
		int nChilds = movesPanel.getChildren().length;
		
		List<Move> moves = history.getMoves();
		
		if (nChilds > moves.size()) {
			movesPanel.clear();
			nChilds = 0;
		}
			
		// Добавим новые ходы в отображаемый список ходов.
		for (int k = nChilds; k < moves.size(); k++) 
			new MoveLabel(movesPanel, k, moves.get(k));

		// Отрисуем в списке темным фоном текущий ход.
		//
		int curMove = history.getCurMoveNumber();
		Control[] children = movesPanel.getChildren();
		
		for (int k = 0; k < children.length; k++) {
			Color color = (k == curMove ? SELECT_COLOR : null);
			Control control = children[k];
			control.setBackground(color);
		}
		resultPanel.setText("" + history.getResult());
		setHeaderText( history.getBoard() );

		movesPanel.pack(true);
	}
}