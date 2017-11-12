package game.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
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

	public GameControlPanel(Composite parent, Game game) {
		super(parent, SWT.NONE);
		setBackground(CONTROL_COLOR);
		setLayout( new GridLayout(1, true) );
		
		java.util.List<IPlayer> ps = game.getPlayers(game.getClass());
		
		wTitle = new Label(this, SWT.CENTER);
		wTitle.setText("White");
		wTitle.setForeground(TITLE_COLOR);

		List wList = new List (this, SWT.BORDER);
		wList.setForeground(BLACK_COLOR);
		wList.setBackground(LIST_COLOR);
		wList.addListener(SWT.Selection, e -> {
			int selection = wList.getSelectionIndices()[0];
			game.board.setWhitePlayer(ps.get(selection));
		});
		for (IPlayer p : ps) wList.add(p.getName());
		
		bTitle = new Label(this, SWT.CENTER);
		bTitle.setText("Black");
		bTitle.setForeground(TITLE_COLOR);
		
		List bList = new List (this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		bList.setForeground(BLACK_COLOR);
		bList.setBackground(LIST_COLOR);
		bList.addListener(SWT.Selection, e -> {
			int selection = bList.getSelectionIndices()[0];
			game.board.setBlackPlayer(ps.get(selection));
		});
		for (IPlayer p : ps) bList.add(p.getName());
	}
}
