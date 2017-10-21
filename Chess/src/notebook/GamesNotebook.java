package notebook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import checkers.ui.CheckersBoardPanel;
import checkers.ui.images.CheckersImages;
import chess.ui.ChessBoardPanel;
import chess.ui.images.ChessImages;
import chinachess.ui.ChinaChessBoardPanel;
import chinachess.ui.images.ChinaChessImages;
import notebook.ui.images.NotebookImages;
import tamerlan.ui.TamerlanChessBoardPanel;
import tamerlan.ui.images.TamerlanChessImages;

/**
 * <b>Блокнот настольных игр.</b></br></br>
 * 
 * Запись и просмотр партии в настольной игре.
 * Сохранение партии в файл и чтение из файла.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GamesNotebook {
	public static void main(String[] args) {
		final Display display = new Display();
		
		final Shell shell = new Shell(display);
		
		shell.setSize(600, 600);
		shell.setText("Games Notebook");
		shell.setImage(NotebookImages.iconNotebook);
		
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		
		TabFolder gamesFolder = new TabFolder(shell, SWT.TOP);
		
		addChessTab(display, gamesFolder);
		addCheckersTab(display, gamesFolder);
		addChinaChessTab(display, gamesFolder);
		addTamerlanChessTab(display, gamesFolder);
		
	    shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	} 

	private static void addTamerlanChessTab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, TamerlanChessImages.iconTamerlanChess
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Тамерлан");
		tabItem.setControl( new TamerlanChessBoardPanel(gamesFolder) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для шашек.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addCheckersTab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, CheckersImages.iconCheсkers
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Шашки");
		tabItem.setControl( new CheckersBoardPanel(gamesFolder) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для китайских шахмат.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addChinaChessTab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, ChinaChessImages.iconChinaChess
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Сянци");
		tabItem.setControl( new ChinaChessBoardPanel(gamesFolder) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для европейских шахмат.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addChessTab(final Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, ChessImages.icoChess
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Шахматы");
		tabItem.setControl( new ChessBoardPanel(gamesFolder) );
		tabItem.setImage(tabImage);
	}
} 