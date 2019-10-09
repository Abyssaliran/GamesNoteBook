package game.db.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Панель для задания параметров поиска в базе игр.
 */
public class GamesSearchPanel extends Composite {
	private static final Color COLOR_BLACK = new Color(null, 0, 0, 0);
	private static final Color COLOR_GREEN = new Color(null, 0, 100, 0);
	private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);

	private List<String> players;
	private List<String> years;

	private CCombo whitePlayers;
	private CCombo blackPlayers;
	private CCombo year;

	public GamesSearchPanel(Composite parent) {
		super(parent, SWT.DM_FILL_NONE);
		setBackground(COLOR_GREEN);
		setLayout(new RowLayout(SWT.HORIZONTAL));

		players = getPlayers();
		years = getYears();

		whitePlayers = addSelectionList(this, "Белые", players);
		whitePlayers.addListener(SWT.DefaultSelection, e -> {
		});

		blackPlayers = addSelectionList(this, "Черные", players);
		blackPlayers.addListener(SWT.DefaultSelection, e -> {
		});

		year = addSelectionList(this, "Год", years);
		year.addListener(SWT.DefaultSelection, e -> {
		});
	}

	private CCombo addSelectionList(Composite parent, String title, List<String> list) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setForeground(COLOR_WHITE);
		group.setLayout(new GridLayout());
		group.setText(title);

		CCombo combo = new CCombo(group, SWT.READ_ONLY | SWT.FLAT | SWT.BORDER);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		combo.setText(list.get(0));
		combo.setForeground(COLOR_BLACK);
		combo.setBackground(COLOR_WHITE);

		list.forEach(p -> combo.add(p));

		return combo;
	}

	private List<String> getPlayers() {
		return Arrays.asList("Ласкер", "Капабланка", "Алехин");
	}

	private List<String> getYears() {
		List<String> list = new ArrayList<>();

		for (int k = 1914; k <= 2019; k++)
			list.add("" + k);

		return list;
	}
}