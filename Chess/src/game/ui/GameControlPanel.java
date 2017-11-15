package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import game.core.Game;
import game.players.IPlayer;

/**
 * Управляющая панель для игр.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameControlPanel extends Composite {
	private static final Color CONTROL_COLOR = new Color(null,   0, 192,  80);
	private static final Color LIST_COLOR    = new Color(null, 255, 255, 255);
	private static final Color TITLE_COLOR   = new Color(null, 255, 255,   0);
	private static final Color BLACK_COLOR   = new Color(null,   0,   0,   0);
	
	private Label bTitle;
	private Label wTitle;
	private Button startButton;

	public GameControlPanel(Composite parent, Game game) {
		super(parent, SWT.NONE);
		setBackground(CONTROL_COLOR);
		setLayout( new GridLayout(1, true) );
		
		Class<? extends Game> gameClass = game.getClass();
		java.util.List<IPlayer> players = game.getPlayers(gameClass);
		
		IPlayer whitePlayer = game.board.getWhitePlayer();
		int whitePlayerNumber = players.indexOf(whitePlayer);
		
		IPlayer blackPlayer = game.board.getBlackPlayer();
		int blackPlayerNumber = players.indexOf(blackPlayer);
		
		// Список для выбора игроков белыми фигурами.
		//
		GridData data;
		
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		wTitle = new Label(this, SWT.CENTER);
		wTitle.setText("White");
		wTitle.setForeground(TITLE_COLOR);
		wTitle.setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		List wList = new List(this, SWT.BORDER);
		wList.setForeground(BLACK_COLOR);
		wList.setBackground(LIST_COLOR);
		wList.setLayoutData(data);
		wList.select(whitePlayerNumber);
		wList.addListener(SWT.Selection, e -> {
			int selection = wList.getSelectionIndices()[0];
			game.board.setWhitePlayer(players.get(selection));
		});
		for (IPlayer p : players) wList.add(p.getName());
		
		// Список для выбора игроков черными фигурами.
		//
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		bTitle = new Label(this, SWT.CENTER);
		bTitle.setText("Black");
		bTitle.setForeground(TITLE_COLOR);
		bTitle.setLayoutData(data);
		
		data = new GridData(SWT.FILL, SWT.TOP, true, false);

		List bList = new List(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		bList.setForeground(BLACK_COLOR);
		bList.setBackground(LIST_COLOR);
		bList.setLayoutData(data);
		bList.select(blackPlayerNumber);
		bList.addListener(SWT.Selection, e -> {
			int selection = bList.getSelectionIndices()[0];
			IPlayer player = players.get(selection);
			game.board.setBlackPlayer(player);
		});
		for (IPlayer p : players) bList.add(p.getName());

		// Кнопка запуска игры.
		//
		data = new GridData(SWT.CENTER, SWT.TOP, true, false);

		startButton = new Button(this, SWT.NONE);
		startButton.setText("Старт");
		startButton.setLayoutData(data);
		startButton.addListener(SWT.Selection, e -> {
			game.initBoardDefault();
			game.board.startGame();
		});
	}
}
