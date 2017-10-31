package game.ui.listeners;

import java.util.List;

import game.core.Board;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;

/**
 * Слушатель для игр в которых фигуры ставятся на доску.
 * Слушатель PutPiecePromptListener определяет клетки на которые можно поставить фигуру.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class PutPiecePromptListener implements IMouseMoveListener {
	/**
	 * Панель на которой отрисовывается доска.
	 */
	private GameBoard boardPanel;

	/**
	 * @param boardPanel 
	 */
	public PutPiecePromptListener(GameBoard boardPanel) {
		this.boardPanel = boardPanel;
	}
	
	@Override
	public void mouseMove(Square mouseSquare) {
		boardPanel.prompted.clear();
		
		Piece underMousePiece = mouseSquare.getPiece();
		if (underMousePiece != null) {
			// Под мышкой уже есть фигура. Клетка не пустая.
			// Для игр у которых фигуры ставяться на доску,
			// ход на занятую клетку невозможен.
			// Перерисуем панель доски без подсказок.
			boardPanel.update();
			boardPanel.redraw();
			return;
		}
		
		// Доска на которой расположены фигуры.
		Board board = boardPanel.board;

		// Клетки, на которые можно поставить новую фигуру.
		List<Square> prompted = boardPanel.prompted;

		// Получим фигуру НЕ стоящую на клетке.
		Piece piece = getPiece(mouseSquare, board.moveColor);
		piece.remove(); // Уберем с доски.
		
		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++) {
				Square target = board.getSquare(v, h);
				
				if (piece.isCorrectMove(target))
					prompted.add(target);
			}
		
		// Перерисуем панель доски c подсказками для
		// клеток на которые допустима постановка фигуры.
		boardPanel.update();
		boardPanel.redraw();
	}
	
	/**
	 * Выдать фигуру заданного цвета.
	 * 
	 * @param square - клетка для фигуры.
	 * @param color - цвет фигуры.
	 * @return - фигура заданного цвета.
	 */
	abstract public Piece getPiece(Square square, PieceColor color);
}
