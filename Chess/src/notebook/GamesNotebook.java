package notebook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import chess.ui.images.ChessImages;
import game.core.Board;
import game.ui.AsiaBoardWithCastle;
import game.ui.EuropeBoard;

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
		ChessImages.load(display);
		
		final Shell shell = new Shell(display);
		
		shell.setSize(600, 600);
		shell.setText("Games Notebook");
		shell.setImage(ChessImages.iconChessNotebook);
		
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		
		TabFolder gamesFolder = new TabFolder(shell, SWT.TOP);
		
		addChessTab(display, gamesFolder);
		addChinaChessTab(display, gamesFolder);
		
	    shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	} 

	/**
	 * Инициализируем закладку для китайских шахмат.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addChinaChessTab(Display display, TabFolder gamesFolder) {
		Board board = new Board(9, 8);
		
		Image tabImage = new Image(display, ChessImages.iconChinaChess
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Сянци");
		tabItem.setControl( new AsiaBoardWithCastle(gamesFolder, board) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для европейских шахмат.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addChessTab(final Display display, TabFolder gamesFolder) {
		Board board = new Board(8, 8);

		Image tabImage = new Image(display, ChessImages.imageKnightBlack
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Шахматы");
		tabItem.setControl( new EuropeBoard(gamesFolder, board) );
		tabItem.setImage(tabImage);
	}
} 