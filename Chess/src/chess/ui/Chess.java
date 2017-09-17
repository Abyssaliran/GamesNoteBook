package chess.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import chess.ChessImages;

/**
 * 
 * @author Romanov
 * Шахматный блокнот.
 * Зпись партии и сохранение ее в файл.
 */
public class Chess {
	public static void main(String[] args) {
		final Display display = new Display();
		ChessImages.load(display);
		
		final Shell shell = new Shell(display);
		
		shell.setSize(600, 600);
		shell.setText("Chess Notepad");
		shell.setImage(ChessImages.iconChessNotebook);
		
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		
		TabFolder gamesFolder = new TabFolder(shell, SWT.TOP);
		
		Image chessImage = new Image(display, ChessImages.imageKnightBlack
				.getImageData().scaledTo(20, 20));
		
		TabItem chessItem = new TabItem(gamesFolder, SWT.NONE);
		chessItem.setText("Шахматы");
		chessItem.setControl(new ChessBoard(gamesFolder, SWT.NONE));
		chessItem.setImage(chessImage);
		
	    shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	} // main
} // class Chess