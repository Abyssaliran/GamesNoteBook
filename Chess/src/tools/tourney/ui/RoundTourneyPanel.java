package tools.tourney.ui;

import game.players.IPlayer;
import tools.tourney.RoundTourney;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.List;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

/**
 * Панель турнира для игры по круговой системе.
 */
class RoundTourneyPanel extends Composite {
    private static final Color COLOR_BLACK = new Color(null, 0, 0, 0);
    private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);
    private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);

    private final RoundTourney tournay;
    private static final int CELL_SIZE = 30;

    RoundTourneyPanel(Composite parent, RoundTourney tourney) {
        super(parent, SWT.BORDER);
        this.tournay = tourney;

        setBackground(COLOR_GREEN);
        setLayout(new RowLayout(SWT.VERTICAL));

        new GamesTable(this, tourney.players);

        Button start = new Button(this, SWT.PUSH | SWT.CENTER);
        start.setText("Старт");
        start.addSelectionListener(widgetSelectedAdapter(e ->
                System.out.println("Старт")
        ));

        pack();
    }

    /**
     * Пустая ячейка на диагонали таблицы.
     */
    class EmptyCell extends Composite {
        public EmptyCell(Composite parent) {
            super(parent, SWT.BORDER);

            setForeground(COLOR_BLACK);
            setBackground(COLOR_BLACK);

            GridLayout layout = new GridLayout(1, false);
            setLayout(layout);

            GridData gridData = new GridData();
            gridData.widthHint = CELL_SIZE;
            gridData.heightHint = CELL_SIZE;
            setLayoutData(gridData);
        }
    }

    /**
     * Ячейка для отображения двух игр для каждого игрока: белыми и черными.
     */
    class GameCell extends Composite {
        private  Label game1;
        private  Label game2;

        GameCell(Composite parent, IPlayer player1, IPlayer player2) {
            super(parent, SWT.BORDER);
            setForeground(COLOR_BLACK);
            setBackground(COLOR_WHITE);

            GridLayout layout = new GridLayout(1, true);
            layout.verticalSpacing = 0;
            setLayout(layout);

            GridData gridData = new GridData();
            gridData.widthHint = CELL_SIZE;
            gridData.heightHint = CELL_SIZE;
            setLayoutData(gridData);

            game1 = new Label(this, SWT.CENTER);
            game1.setText("*");
            game1.setToolTipText(player1.getName() + " - " + player2.getName());

            game2 = new Label(this, SWT.CENTER);
            game2.setText("*");
            game2.setToolTipText(player2.getName() + " - " + player1.getName());
        }
      }
    
    
    /**
     * Панель для отображения количества очков набранных игроком.
     */
    class ResultCell extends Composite {
    	private Label result;
    	
		ResultCell(Composite parent, IPlayer player) {
            super(parent, SWT.BORDER);
            setForeground(COLOR_BLACK);
            setBackground(COLOR_WHITE);

            GridLayout layout = new GridLayout(1, true);
            layout.verticalSpacing = 0;
            setLayout(layout);

            GridData gridData = new GridData();
            gridData.widthHint = CELL_SIZE;
            gridData.heightHint = CELL_SIZE;
            setLayoutData(gridData);

            result = new Label(this, SWT.CENTER);
            result.setText("*");
            result.setToolTipText(player.getName());
        }
    }   

    /**
     * Таблица для отображения соревнования проводимого по круговой системе.
     */
    public class GamesTable extends Composite {
        GamesTable(Composite parent, List<IPlayer> players) {
            super(parent, SWT.NONE);

            int nPlayers = players.size();

            GridLayout layout = new GridLayout(1 + nPlayers + 1, false);
            layout.horizontalSpacing = 0;
            layout.verticalSpacing = 0;
            setLayout(layout);

            // Верхний левый угол таблицы.
            new Label(this, SWT.NONE);

            // Номера колонок - номера оппонентов игрока.
            for (int k = 0; k < nPlayers; k++) {
                IPlayer player = players.get(k);

                Label playerNumber = new Label(this, SWT.CENTER);
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
            for (int k = 0; k < nPlayers; k++) {
                IPlayer player = players.get(k);

                // Номер игрока в таблице и имя игрока.
                String txt = String.format("%2d. %s", 1 + k, player.getName());

                Label name = new Label(this, SWT.LEFT);
                name.setForeground(COLOR_WHITE);
                name.setText(txt + " ");
                name.setToolTipText("Автор алгоритма: " + player.getAuthorName());

                // Результаты игры этого игрока белыми и черными
                // с каждым из игроков в этой таблице.
                for (IPlayer opponent : players) {
                    boolean isDiagonal = (player == opponent);

                    if (isDiagonal) new EmptyCell(this);
                    else new GameCell(this, player, opponent);
                }
                
                // Количество очков набранных игроком.
                new ResultCell(this, player);
            }
        }
    }
}

 