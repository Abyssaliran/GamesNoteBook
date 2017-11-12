package vikings;

import game.core.Board;
import game.core.Game;
import game.core.LineDirs;
import game.core.PieceColor;
import game.core.Square;
import game.players.IPlayer;
import game.players.Neznaika;
import vikings.pieces.Viking;
import vikings.pieces.Сyning;

/**
 * Игра 
 * <a href="https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB">Викинги (Хнефатафл, Тавлеи) </a>.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Vikings extends Game {
	
	static {
		addPlayer(Vikings.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(Vikings.class, new Neznaika());
	}
	
	/**
	 * Создание доски заданного размера
	 * и расстановка фигур для этого размера доски.
	 * 
	 * @param boardSize - размер доски.
	 * @return доска с расставленными фигурами.
	 */
	public Vikings(int boardSize) {
		super.initBoard(boardSize, boardSize);
		
		switch (boardSize) {
			case  9: initBoard9();  break;
			case 11: initBoard11(); break;
		}
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	/** 
	 * Создание доски размером 11х11
	 * и расстановка фигур для этого размера доски.
	 * 
	 * @return доска с расставленными фигурами.
	 */
	private void initBoard11() {
		new Сyning(board.getSquare(5, 5), PieceColor.WHITE);
	}

	/** 
	 * Создание доски размером 9х9
	 * и расстановка фигур для этого размера доски.
	 * 
	 * @return доска с расставленными фигурами.
	 */
	private void initBoard9() {
		int c = 4;
		
		new Сyning(board.getSquare(c, c), PieceColor.WHITE);
		
		for(LineDirs dir : LineDirs.ALL) 
			for (int k = 1; k < 3; k++) {
				Square square = board.getSquare(c + k * dir.dv, c + k * dir.dh);
				new Viking(square, PieceColor.WHITE);
			}

		for(LineDirs dir : LineDirs.ALL)  
			setBlack(board, c + 4 * dir.dv, c + 4 * dir.dh);
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	private static void setBlack(Board board, int v, int h) {
		Square square = board.getSquare(v, h);
		new Viking(square, PieceColor.BLACK);
		
		for(LineDirs dir : LineDirs.ALL) {
			if (board.onBoard(v + dir.dv, h + dir.dh)) {
				square = board.getSquare(v + dir.dv, h + dir.dh);
				new Viking(square, PieceColor.BLACK);
			}
		}
	}
}
