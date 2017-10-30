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
import go.ui.GoBoardPanel;
import go.ui.images.GoImages;
import halma.ui.HalmaBoardPanel;
import halma.ui.images.HalmaImages;
import notebook.ui.images.NotebookImages;
import reversi.ui.ReversiBoardPanel;
import reversi.ui.images.ReversiImages;
import vikings.ui.VikingsBoardPanel;
import vikings.ui.images.VikingImages;
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
		addViking9Tab(display, gamesFolder);
		addViking11Tab(display, gamesFolder);
		addTamerlanChessTab(display, gamesFolder);
		addReversiTab(display, gamesFolder);
		addReversiHoleTab(display, gamesFolder);
		addGoTab(display, gamesFolder);
		addHalma8x8Tab(display, gamesFolder);
		addHalma10x10Tab(display, gamesFolder);
		addHalma16x16Tab(display, gamesFolder);
		
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

	/**
	 * Инициализируем закладку для игры Викинги на доске 9х9.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addViking9Tab(final Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, VikingImages.icoVikings9
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Викинги-9");
		tabItem.setControl( new VikingsBoardPanel(gamesFolder, 9) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для игры Викинги на доске 11х11.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addViking11Tab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, VikingImages.icoVikings11
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Викинги-11");
		tabItem.setControl( new VikingsBoardPanel(gamesFolder, 11) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для игры Реверси.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addReversiTab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, ReversiImages.icoReversi
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Реверси");
		tabItem.setControl( new ReversiBoardPanel(gamesFolder, 0) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для игры Реверси 
	 * со случайными отверсиями на доске.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addReversiHoleTab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, ReversiImages.icoReversiX
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Реверси Х");
		tabItem.setControl( new ReversiBoardPanel(gamesFolder, 1) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для игры Го. 
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addGoTab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, GoImages.icoGo
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Го");
		tabItem.setControl( new GoBoardPanel(gamesFolder, 10) );
		tabItem.setImage(tabImage);
	}

	/**
	 * Инициализируем закладку для игры Халма 
	 * со случайными отверсиями на доске.
	 * 
	 * @param display - монитор на котором рисуется закладки.
	 * @param gamesFolder - контейнер для добавления закладки.
	 */
	private static void addHalma8x8Tab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, HalmaImages.icoHalma
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Халма 8x8");
		tabItem.setControl( new HalmaBoardPanel(gamesFolder, 8) );
		tabItem.setImage(tabImage);
	}
	private static void addHalma10x10Tab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, HalmaImages.icoHalma
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Халма 10x10");
		tabItem.setControl( new HalmaBoardPanel(gamesFolder, 10) );
		tabItem.setImage(tabImage);
	}
	private static void addHalma16x16Tab(Display display, TabFolder gamesFolder) {
		Image tabImage = new Image(display, HalmaImages.icoHalma
				.getImageData().scaledTo(20, 20));
		
		TabItem tabItem = new TabItem(gamesFolder, SWT.NONE);
		tabItem.setText("Халма 16x16");
		tabItem.setControl( new HalmaBoardPanel(gamesFolder, 16) );
		tabItem.setImage(tabImage);
	}
	
} 