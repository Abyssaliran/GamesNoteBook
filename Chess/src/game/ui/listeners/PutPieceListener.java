package game.ui.listeners;

import game.core.Board;
import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;

/**
 * Слушатель событий о нажатии кнопок мыши используемых 
 * для постановки новой фигуры на доску.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class PutPieceListener implements IGameListner {
	/**
	 * Доска на которой присходят изменения.
	 */
	private Board board;
	
	/**
	 * Панель для отрисовки доски.
	 */
	private GameBoard boardPanel;

	/**
	 * Создать слушателя событий от нажатий кнопок мыши 
	 * используемых для постановки новой фигуры на доску.
	 * 
	 * @param boardPanel - панель доски на которую ставятся фигуры.
	 */
	public PutPieceListener(GameBoard boardPanel) {
		this.board = boardPanel.board;
		this.boardPanel = boardPanel;
	}
	
	@Override
	public void mouseUp(Square s, int button) {}
	
	@Override
	public void mouseDown(Square mouseSquare, int button) {
		if (!mouseSquare.isEmpty())
			return;
		
		// Получим фигуру НЕ стоящую на клетке.
		PieceColor moveColor = board.getMoveColor();
		Piece piece = boardPanel.getPiece(mouseSquare, moveColor);
		piece.remove();
		
		if (!piece.isCorrectMove(mouseSquare)) 
			return; // На эту клетку ставить нельзя.

		// Постановка фигуры на заданную клетку правильная.
		// Создадим экземпляр хода и выполним его.
		Move move = piece.makeMove(mouseSquare);
		board.history.addMove(move);
		try {
			move.doMove();
		} catch (GameOver e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Зададим изображение курсора такое как избражение у фигуры.
		boardPanel.pieceToCursor(piece);
		
		// Пусть слушатели изменений на доске 
		// нарисуют новое состояние доски.
		board.setBoardChanged();
		boardPanel.redraw();
		
		// Теперь ходить должен противник. 
		board.changeMoveColor();
	}
}
