package halma;

import game.core.Board;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;
import halma.pieces.Stone;

/**
 * Игра <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Halma extends Game {
	
	static {
		addPlayer(Halma.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(Halma.class, new Neznaika());
	}

	private static final short   allowableBoardSizeNumb = 3;
	private static final short[] allowableBoardSize = {8, 10, 16};
	
	/**
	 * Creates game board with proper sizes allowable for the Game.
	 * @return Board with allocated figures
	 */
	public Halma(int boardSize) {
		super.initBoard(boardSize, boardSize);
		initializeParticularBoard(boardSize);
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}	
	
	/**
	 * is used when wrong board size is passed on input
	 * @param boardSize
	 * @return  empty non-initialized board
	 */
	private static Board emptyBoard(int boardSize) {
		
		// TO DO:
		// Throw an exception or say about wrong board sizes
		
		return new Board(boardSize, boardSize);	// null	
	} 
	
	public Board getInitBoard(int boardSize) {
		
		// Initialize board of the proper format
		for (short ind_sz = 0; ind_sz < Halma.allowableBoardSizeNumb; ++ind_sz) {
			if (allowableBoardSize[ind_sz] == boardSize) {
				return initializeParticularBoard(allowableBoardSize[ind_sz]);
			}
		}
		return Halma.emptyBoard(boardSize);
	}

	public Board initializeParticularBoard(int boardSize) {
		// Add Common Corner
		for (short i = 0; i < 4; ++i) {
			for (short j = 0; j < 4 - i; ++j) {
				new Stone( board.getSquare(i, j), PieceColor.WHITE);
				new Stone( board.getSquare(boardSize - i - 1, boardSize - j - 1), PieceColor.BLACK);				
			}
		}
		// Add extra diagonal
		if (allowableBoardSize[1] <= boardSize) {
			for (short i = 0; i < 5; ++i) {
				new Stone( board.getSquare(i, 4 - i), PieceColor.WHITE);
				new Stone( board.getSquare(boardSize - i - 1, boardSize - 5 + i), PieceColor.BLACK);
			}			
		}
		// Add short diagonal
		if (allowableBoardSize[2] == boardSize) {
			for (short i = 0; i < 4; ++i) {
				new Stone( board.getSquare(i + 1, 4 - i), PieceColor.WHITE);
				new Stone( board.getSquare(boardSize - i - 2, boardSize - 5 + i), PieceColor.BLACK);				
			}						
		}
		
		return board;
	}
}

