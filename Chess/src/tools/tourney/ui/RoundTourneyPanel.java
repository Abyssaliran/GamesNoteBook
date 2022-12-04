package tools.tourney.ui;

import chinachess.ui.ChinaChessBoardPanel;
import game.core.Game;
import game.core.GameResult;
import game.players.IPlayer;
import game.ui.AdornedBoard;
import game.ui.MovesJornal;
import game.ui.images.GameImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import tools.tourney.RoundTourney;

import static java.awt.Label.CENTER;
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;
import static tools.tourney.Competition.getResult;

/**
 * Панель турнира для игры по круговой системе.
 */
class RoundTourneyPanel extends Composite {
	private static final Color COLOR_BLACK = new Color(null, 0, 0, 0);
	private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);
	private static final Color COLOR_GRAY = new Color(null, 100, 100, 100);
	private static final Color COLOR_RED = new Color(null, 255, 0, 0);
	private static final Color COLOR_BLUE = new Color(null, 0, 0, 255);
	private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);

	private static final Color COLOR_SELECT = new Color(null, 255, 215, 0);

	private final RoundTourney tournay;
	private static final int CELL_HEIGHT = 25;
	private static final int CELL_WIDTH = CELL_HEIGHT * 2;

	GamesTable gamesTable;

	private Composite westPanel;
	private Composite centerPanel;
	private Composite eastPanel;

	static private Game currentGame;
	static private Label currentGameCell;

	RoundTourneyPanel(Composite parent, RoundTourney tourney) {
		super(parent, SWT.BORDER);

		this.tournay = tourney;
		tourney.run();
		currentGame = tourney.get(0, 1);

		setBackgroundImage(GameImages.woodDark);
		Layout layout = new GridLayout(3, false);
		setLayout(layout);

		//
		// Левая панель
		//
		GridData data = new GridData(SWT.FILL, SWT.TOP, false, true);

		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.center = true;
		westPanel = new Composite(this, SWT.BORDER);
		westPanel.setLayout(rowLayout);
		westPanel.setLayoutData(data);
		westPanel.setBackground(COLOR_GREEN);

		gamesTable = new GamesTable(westPanel, tourney);

		Button start = new Button(westPanel, SWT.PUSH | SWT.CENTER);
		start.setAlignment(SWT.CENTER);
		start.setText("Старт");
		start.addSelectionListener(widgetSelectedAdapter(e -> System.out.println("Старт")));

		centerPanel = new Composite(this, SWT.BORDER);
		eastPanel = new Composite(this, SWT.BORDER);

		gameSelected(currentGame);
	}

	public void clear(Composite c) {
		Control[] children = c.getChildren();
		for (int i = children.length - 1; i >= 0; i--)
			children[i].dispose();
	}

	@SuppressWarnings("deprecation")
	private void gameSelected(Game game) {
		clear(centerPanel);
		clear(eastPanel);
		game.board.deleteObservers();

		//
		// Центральная панель
		//
		GridData centerData = new GridData(SWT.FILL, SWT.FILL, true, true);
		centerData.widthHint = 550;
		centerPanel.setLayout(new FillLayout());
		centerPanel.setLayoutData(centerData);

		ChinaChessBoardPanel boardPanel = new ChinaChessBoardPanel(centerPanel, currentGame);
		AdornedBoard adorned = new AdornedBoard(centerPanel);
		adorned.insertSquares(boardPanel);

		//
		// Правая панель
		//
		GridData eastData = new GridData(SWT.LEFT, SWT.FILL, false, true);
		eastData.widthHint = 380;
		eastPanel.setLayout(new FillLayout());
		eastPanel.setLayoutData(eastData);
		new MovesJornal(eastPanel, currentGame.board.history);

		currentGame.board.setBoardChanged();

		pack(true);
	}

	/**
	 * Пустая ячейка на диагонали таблицы.
	 */
	static class EmptyCell extends Composite {
		public EmptyCell(Composite parent) {
			super(parent, SWT.BORDER);

			setForeground(COLOR_BLACK);
			setBackground(COLOR_GRAY);

			GridLayout layout = new GridLayout(1, false);
			setLayout(layout);

			GridData gridData = new GridData();
			gridData.widthHint = CELL_WIDTH;
			gridData.heightHint = CELL_HEIGHT;
			setLayoutData(gridData);
		}
	}

	/**
	 * Ячейка для отображения двух игр для каждого игрока: белыми и черными.
	 */
	class GameCell extends Composite {
		private final RoundTourney tourney;
		private final int row;
		private final int col;
		private final Game whiteGame;
		private final Game blackGame;
		private Label whiteGameCell;
		private Label blackGameCell;

		GameCell(Composite parent, RoundTourney tourney, int row, int col) {
			super(parent, SWT.BORDER);

			this.tourney = tourney;
			this.row = row;
			this.col = col;

			whiteGame = tourney.get(row, col);
			blackGame = tourney.get(col, row);
			GameResult whiteResult = getResult(whiteGame);
			GameResult blackResult = getResult(blackGame);

			setForeground(COLOR_BLACK);
			setBackground(COLOR_WHITE);

			GridLayout layout = new GridLayout(2, true);
			layout.verticalSpacing = 0;
			setLayout(layout);

			GridData gridData = new GridData();
			gridData.widthHint = CELL_WIDTH;
			gridData.heightHint = CELL_HEIGHT;
			setLayoutData(gridData);

			whiteGameCell = new Label(this, SWT.CENTER);
			whiteGameCell.setForeground(resultColor(whiteResult, true));
			whiteGameCell.setText(resultText(whiteResult, true));
			whiteGameCell.setToolTipText(tooltipText(whiteGame));
			whiteGameCell.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					if (currentGameCell != null) {
						currentGameCell.setBackground(COLOR_WHITE);
						currentGameCell.update();
						currentGameCell.redraw();
					}
					whiteGameCell.setBackground(COLOR_SELECT);
					whiteGameCell.update();

					currentGameCell = whiteGameCell;
					currentGame = tourney.get(row, col);
//                    currentGame.board.setBoardChanged();
					System.out.format(" Event white: %s %n", whiteGameCell.getToolTipText());
					RoundTourneyPanel.this.gameSelected(currentGame);
				}
			});

			blackGameCell = new Label(this, SWT.CENTER);
			blackGameCell.setForeground(resultColor(blackResult, false));
			blackGameCell.setText(resultText(blackResult, false));
			blackGameCell.setToolTipText(tooltipText(blackGame));
			blackGameCell.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					if (currentGameCell != null) {
						currentGameCell.setBackground(COLOR_WHITE);
						currentGameCell.update();
						currentGameCell.redraw();
					}
					blackGameCell.setBackground(COLOR_SELECT);
					blackGameCell.update();

					currentGameCell = blackGameCell;
					currentGame = tourney.get(col, row);
					System.out.format(" Event black: %s %n", blackGameCell.getToolTipText());
					RoundTourneyPanel.this.gameSelected(currentGame);
				}
			});

			if (whiteGame == currentGame) {
				currentGameCell = whiteGameCell;
				whiteGameCell.setBackground(COLOR_SELECT);
			}
		}
	}

	/**
	 * Панель для отображения количества очков набранных игроком.
	 */
	static class ResultCell extends Composite {
		ResultCell(Composite parent, RoundTourney tourney, int row) {
			super(parent, SWT.BORDER);
			setForeground(COLOR_BLACK);
			setBackground(COLOR_WHITE);

			IPlayer player = tourney.get(row);

			GridLayout layout = new GridLayout(1, true);
			layout.verticalSpacing = 0;
			setLayout(layout);

			GridData gridData = new GridData();
			gridData.widthHint = CELL_WIDTH;
			gridData.heightHint = CELL_HEIGHT;
			setLayoutData(gridData);

			Label result = new Label(this, SWT.CENTER);
			result.setText("" + tourney.score(row));
			result.setToolTipText(player.getName());
		}
	}

	/**
	 * Таблица для отображения соревнования проводимого по круговой системе.
	 */
	public class GamesTable extends Composite {
		GamesTable(Composite parent, RoundTourney tourney) {
			super(parent, SWT.NONE);

			int nPlayers = tourney.size();

			GridLayout layout = new GridLayout(1 + nPlayers + 1, false);
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			setLayout(layout);

			// Верхний левый угол таблицы.
			new Label(this, SWT.NONE);

			// Номера колонок - номера оппонентов игрока.
			for (int k = 0; k < nPlayers; k++) {
				IPlayer player = tourney.get(k);

				Label playerNumber = new Label(this, SWT.CENTER);
				playerNumber.setAlignment(CENTER);
				playerNumber.setForeground(COLOR_WHITE);
				playerNumber.setText("" + (1 + k));
				playerNumber.setToolTipText(player.getName());
			}

			// Колонка для отображения очков набранных игроком.
			Label playersScore = new Label(this, SWT.CENTER);
			playersScore.setForeground(COLOR_WHITE);
			playersScore.setText("Очки");

			// Строки таблицы с результатами игры
			// для игрока записанного в начале строки.
			for (int row = 0; row < nPlayers; row++) {
				IPlayer player = tourney.get(row);

				// Номер игрока в таблице и имя игрока.
				String txt = String.format("%2d. %s ", 1 + row, player.getName());

				Label name = new Label(this, SWT.LEFT);
				name.setForeground(COLOR_WHITE);
				name.setText(txt);
				name.setToolTipText("Автор алгоритма: " + player.getAuthorName());

				// Результаты игры этого игрока белыми и черными
				// с каждым из игроков в этой таблице.
				for (int col = 0; col < nPlayers; col++) {
					IPlayer opponent = tourney.get(col);
					boolean isDiagonal = (player == opponent);

					if (isDiagonal)
						new EmptyCell(this);
					else
						new GameCell(this, tourney, row, col);
				}

				// Количество очков набранных игроком.
				new ResultCell(this, tourney, row);
			}
		}
	}

	static private String resultText(GameResult gemRes, boolean whitePlay) {
		switch (gemRes) {
		case WHITE_WIN:
			return whitePlay ? " 1 " : " 0 ";
		case BLACK_WIN:
			return whitePlay ? " 0 " : " 1 ";
		case DRAWN:
			return " \u00BD ";
		case UNKNOWN:
			return " Х ";
		}
		return null;
	}

	static private Color resultColor(GameResult gemRes, boolean whitePlay) {
		switch (gemRes) {
		case WHITE_WIN:
			return whitePlay ? COLOR_RED : COLOR_BLUE;
		case BLACK_WIN:
			return whitePlay ? COLOR_BLUE : COLOR_RED;
		case DRAWN:
			return COLOR_RED;
		case UNKNOWN:
			return COLOR_GREEN;
		}
		return null;
	}

	static private String tooltipText(Game game) {
		IPlayer whitePlayer = game.board.getWhitePlayer();
		IPlayer blackPlayer = game.board.getBlackPlayer();

		String players = whitePlayer.getName() + " - " + blackPlayer.getName();
		String authors = whitePlayer.getAuthorName() + " - " + blackPlayer.getAuthorName();

		return String.format("%s %s (%s)", players, getResult(game), authors);
	}
}
