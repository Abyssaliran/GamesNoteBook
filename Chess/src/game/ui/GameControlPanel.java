package game.ui;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import game.core.Board;
import game.core.Game;
import game.players.IPlayer;

/**
 * Управляющая панель для игр. Запуск игры с выбранными игроками.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameControlPanel extends Composite {
	private static final Color TITLE_COLOR   = new Color(null, 255, 255,   0);
	private static final Color LIST_COLOR    = new Color(null, 255, 255, 255);
	private static final Color BORDER_COLOR  = new Color(null,   0,   0,   0);
	private static final Color CONTROL_COLOR = new Color(null,   0, 192,  80);
	
	public GameControlPanel(Composite parent, Game game) {
		super(parent, SWT.NONE);
		setBackground(CONTROL_COLOR);
		setLayout( new GridLayout(1, true) );

		final Board board = game.board;
		
		// Получим всех игроков для этой игры.
		Class<? extends Game> gameClass = game.getClass();
		java.util.List<IPlayer> players = game.getPlayers(gameClass);
		
		// Получим текущих белого и черного игроков для этой игры.
		IPlayer wPlayer = board.getWhitePlayer();
		IPlayer bPlayer = board.getBlackPlayer();
		
		// Получим индексы в списке игроков для текущих 
		// белого и черного игроков этой игры.
		int wPlayerNumber = getPlayerIndex(wPlayer, players);
		int bPlayerNumber = getPlayerIndex(bPlayer, players);
		
		// Список для выбора игроков черными фигурами.
		List bList = getPlayersList("Black", bPlayerNumber, players);
		bList.addListener(SWT.Selection, event -> 
			board.setBlackPlayer( getSelectedPlayer(event, players) )
		);

		// Список для выбора игроков белыми фигурами.
		List wList = getPlayersList("White", wPlayerNumber, players);
		wList.addListener(SWT.Selection, event ->  
			board.setWhitePlayer( getSelectedPlayer(event, players) )
		);
		
		// Кнопка запуска игры.
		//
		GridData data = new GridData(SWT.CENTER, SWT.TOP, true, false);

		Button startButton = new Button(this, SWT.NONE);
		startButton.setText("Старт");
		startButton.setLayoutData(data);
		startButton.addListener(SWT.Selection, event -> {
			game.initBoardDefault();
			board.startGame();
		});
	}

	/**
	 * Выдать игрока выбранного в списке игроков.
	 * 
	 * @param e - событие выбора в списке.
	 * @param players - список игроков.
	 * @return выбраный из списка игрок.
	 */
	private IPlayer getSelectedPlayer(Event e, java.util.List<IPlayer> players) {
		List list = (List) e.widget;
		int selection = list.getSelectionIndices()[0];
		return players.get(selection);
	}

	/**
	 * Выдать управляющий элемент - список игроков.
	 * 
	 * @param titleText - текст заголовка списка.
	 * @param playerNumber - номер выделенного в списке игрока. 
	 * @param players - список игроков.
	 * @return управляющий элемент - список игроков.
	 */
	private List getPlayersList(String titleText, int playerNumber, java.util.List<IPlayer> players) {
		GridData titleData = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		Label title = new Label(this, SWT.CENTER);
		title.setText(titleText);
		title.setForeground(TITLE_COLOR);
		title.setLayoutData(titleData);

		GridData listData = new GridData(SWT.FILL, SWT.TOP, true, false);
		
		List list = new List(this, SWT.BORDER | SWT.SINGLE);
		players.forEach(p -> list.add( p.getName() ));

		list.setForeground(BORDER_COLOR);
		list.setBackground(LIST_COLOR);
		list.setLayoutData(listData);
		list.select(playerNumber);
		
		return list;
	}

	/**
	 * Найти номер игрока в списке игроков.
	 * 
	 * @param player
	 *            - заданный игрок.
	 * @param players
	 *            - список игроков.
	 * 
	 * @return номер найденного игрока.
	 */
	private int getPlayerIndex(IPlayer player, java.util.List<IPlayer> players) {
		Optional<IPlayer> x = 
			players.stream()
				.filter(p -> p.getClass() == player.getClass())
				.findFirst();
		
		return x.isPresent() ? players.indexOf(x.get()) : 0;
	}
}
