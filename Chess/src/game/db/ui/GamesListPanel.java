package game.db.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class GamesListPanel extends Composite {
	private static final Color HEADER_COLOR = new Color(Display.getCurrent(), 217, 173, 124);
	private static final Color BLACK_COLOR  = new Color(Display.getCurrent(),   0,   0,   0);
	private static final Color PAPER_COLOR  = new Color(Display.getCurrent(), 255, 210,   0);

	private Label headerPanel;
	private Composite gamesPanel;
	private static final Font font = new Font(Display.getCurrent(), "mono", 10, SWT.BOLD);

	public GamesListPanel(Composite parent, int style) {
		super(parent, style);
		setBackground( new Color(null, 0,0, 255) );
		
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

		headerPanel = new Label(this, SWT.LEFT | SWT.BORDER);
		headerPanel.setText("Найденые игры");
		headerPanel.setBackground(HEADER_COLOR);
		headerPanel.setForeground(BLACK_COLOR);
		headerPanel.setFont(font);
		headerPanel.setLayoutData(data);
		
		GridData gamesData = new GridData(SWT.FILL, SWT.FILL, true, true);

		gamesPanel = new Composite(this, SWT.NONE);
		gamesPanel.setBackground(PAPER_COLOR);
		gamesPanel.setLayoutData(gamesData);
	}
}