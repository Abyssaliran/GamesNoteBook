package game.ui.listeners;

import javax.swing.JOptionPane;

import game.core.Board;
import game.core.Game;
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
	private final Board board;
	
	/**
	 * Панель для отрисовки доски.
	 */
	private final GameBoard boardPanel;

	private Game cur_game;
	
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
	
	public PutPieceListener(GameBoard boardPanel, Game game) {
		this(boardPanel);
		cur_game = game;
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
			// Сохраним экземпляр кода и истории партии.
			board.history.addMove(move);
			board.history.setResult(e.result);

			// Пусть слушатели изменений на доске 
			// нарисуют новое состояние доски.
			board.setBoardChanged();
			boardPanel.redraw();
			
			// 游戏结束，不再弹出继续游戏的提示框
			// Game over, no longer show "play again" prompt
			// Игра окончена, больше не показываем запрос "играть снова"
			return;
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
