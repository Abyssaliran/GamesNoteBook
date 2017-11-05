package game.ui;

import game.core.Board;
import game.core.History;
import game.core.Move;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Журнал для хранения истории ходов игры.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class MovesJornal extends Composite implements Observer {
	private static final Font font = new Font(Display.getCurrent(), "mono", 10, SWT.BOLD);
	
	private static final Color SELECT_COLOR = new Color(Display.getCurrent(), 217, 173, 124);
	private static final Color HEADER_COLOR = new Color(Display.getCurrent(), 217, 173, 124);
	private static final Color BLACK_COLOR  = new Color(Display.getCurrent(),   0,   0,   0);
	private static final Color PAPER_COLOR  = new Color(Display.getCurrent(), 255, 255,   0);

	/**
	 * Текст для представления хода в истории игры.
	 * 
	 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
	 */
	private class MoveLabel {
		private Move move;
		private Label label;

		/**
		 * @param parent
		 * @param style
		 */
		public MoveLabel(Composite parent, Move move) {
			init(parent, move);

			label.setText( move.toString() );
		}

		/**
		 * @param parent
		 * @param kMove
		 * @param move
		 */
		public MoveLabel(Composite parent, int kMove, Move move) {
			init(parent, move);

			int n = 1 + kMove / 2;
			label.setText("" + n + ". " + move);
		}
		
		/**
		 * @param parent
		 * @param move
		 */
		private void init(Composite parent, Move move) {
			this.move = move;

			label = new Label(parent, SWT.TRANSPARENT);
			label.setFont(font);

			MouseTrackListener listener = new MouseTrackListener() {
				private Cursor oldCursor;
				private Cursor hand = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);

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
		}

		/**
		 * Задать фон для текущего рассматриваемого хода в истории игры.
		 * 
		 * @param color
		 * 		цвет фона
		 */
		public void setBackground(Color color) {
			label.setBackground(color);
		}
	}

	private History history;
	
	private Composite movesPanel;
	private Label headerPanel;

	/**
	 * @param parent
	 * @param history
	 */
	public MovesJornal(Composite parent, History history) {
		super(parent, SWT.BORDER);
		setForeground(BLACK_COLOR);
		setBackground(PAPER_COLOR);
		
		this.history = history;
		Board board = history.getBoard();
	
		GridLayout layout = new GridLayout(1, true);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginBottom = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		
		// 
		// Панель для показа игроков партии.
		//
		String white = board.getWhitePlayer().getName();
		String black = board.getBlackPlayer().getName();
		String title = String.format("%s - %s", white, black);

		headerPanel = new Label(this, SWT.CENTER);
		headerPanel.setText(title);
		headerPanel.setBackground(HEADER_COLOR);
		headerPanel.setForeground(BLACK_COLOR);
		headerPanel.setFont(font);

		GridData headerData = new GridData(SWT.FILL, SWT.TOP, false, true);
		headerData.widthHint  = 200;
		headerData.heightHint =  30;
		headerPanel.setLayoutData(headerData);
		
		// 
		// Панель для показа ходов в партии.
		//
		movesPanel = new Composite(this, SWT.NONE);
		movesPanel.setLayout( new RowLayout() );
		movesPanel.setBackground(PAPER_COLOR);
		
		GridData movesData = new GridData(SWT.FILL, SWT.FILL, true, true);
		movesData.widthHint  = 200;
		movesPanel.setLayoutData(movesData);

		// Добавляем к доске еще одного обозревателя - панель ходов.
		// При изменении положения фигур на доске панель ходов уведомят, 
		// что нужно перерисовать историю партии (список ходов партии). 
		board.addObserver(this);
		pack();
	}

	@Override
	public void update(Observable board, Object arg) {
		update(movesPanel, history);
		
		setSize(getSize().x, getSize().y+1);
		pack();
	}

	/**
	 * Обновить изображение истории игры.
	 * 
	 * @param parent
	 *            составной элемент в котором показываются ходы.
	 * @param history
	 *            история игры
	 */
	void update(Composite parent, History history) {
		for (Control control : parent.getChildren())
			control.dispose();
		
		int kMove = 0;
		int curMove = history.getCurMoveNumber();
		
		for (Move move : history.getMoves()) {
			MoveLabel label = (kMove % 2 == 0) 
								? new MoveLabel(parent, kMove, move)
								: new MoveLabel(parent, move);
								
			if (kMove == curMove)
				label.setBackground(SELECT_COLOR);
				
			kMove++;
		}
		
		parent.changed(parent.getChildren());
	}
}
