package game.db.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import chess.Chess;
import chess.ui.ChessBoardPanel;
import game.core.Game;
import game.ui.AdornedBoard;
import game.ui.GameBoard;
import game.ui.MovesJornal;

/**
 * Панель для поиска партий в базе данных.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameDBPanel extends Composite {
	private Game game;
	private GamesListPanel searchPanel;
	private AdornedBoard board;
	private MovesJornal jornal;

	public GameDBPanel(Composite parent, Game game) {
		super(parent, SWT.TRANSPARENT);
		this.game = game;
		
		setLayout( new GridLayout(2, false) );
		
		GridData data;

		data = new GridData(SWT.LEFT, SWT.FILL, false, true);
		data.widthHint = 150;

		searchPanel = new GamesListPanel(this, SWT.NONE);
		searchPanel.setLayoutData(data);
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		board = new AdornedBoard(this);
		board.setLayoutData(data);
		
		insertSquares( new ChessBoardPanel(this, game) );
	}

	public GameDBPanel(Composite parent) {
		super(parent, SWT.TRANSPARENT);
		
		setLayout( new GridLayout(3, false) );
		
		GridData data;

		data = new GridData(SWT.LEFT, SWT.FILL, false, true);
		data.widthHint = 150;

		searchPanel = new GamesListPanel(this, SWT.NONE);
		searchPanel.setLayoutData(data);
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		board = new AdornedBoard(this);
		board.setLayoutData(data);
		
		insertSquares( new ChessBoardPanel(this, new Chess()) );
	}
	

	/**
	 * Вставить в панель игры доску с клетками.
	 * 
	 * @param gameBoard
	 *            - вставляемая доска с клетками.
	 */
	protected void insertSquares(GameBoard gameBoard) {
		board.insertSquares(gameBoard);
		
		jornal = new MovesJornal(this, gameBoard.board.history);
		jornal.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
	}
	
	/**
	 * Изменить размеры доски.
	 * 
	 * @param nV
	 *            - количество вертикалей.
	 * @param nH
	 *            - количество горизонталей.
	 */
	public void resizeBoard(int nV, int nH) {
		// Новые размеры доски и расстановка фигур.
		game.initBoard(nV, nH);
		
		board.resize(nV, nH);
	}
}

