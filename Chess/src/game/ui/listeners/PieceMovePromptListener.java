package game.ui.listeners;

import java.util.List;

import game.core.Board;
import game.core.Piece;
import game.core.Square;
import game.ui.GameBoard;

/**
 * Слушатель PieceMovePromptListener определяет клетки, на которые может пойти
 * фигура находящаяся под мышкой.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class PieceMovePromptListener implements IMouseMoveListener {
	/**
	 * Панель, на которой рисуется доска.
	 */
	private GameBoard boardPanel;
	
	/**
	 * Клетки, на которые может пойти фигура находящаяся под мышкой.
	 */
	private List<Square> prompted;

	/**
	 * 
	 * @param boardPanel
	 *            - панель для отрисовки доски.
	 * @param prompted
	 *            - клетки, на которые возможен ход фигурой находящейся под
	 *            мышкой. Эти клетки при перерисовке панели доски должны быть
	 *            помечены. Например, красной рамкой.
	 */
	public PieceMovePromptListener(GameBoard boardPanel, List<Square> prompted) {
		this.boardPanel = boardPanel;
		this.prompted = prompted;
	}

	@Override
	public void mouseMove(Square underMouse) {
		boardPanel.prompted.clear();

		Piece underMousePiece = underMouse.getPiece();
		if (underMousePiece == null) {
			// Под мышкой фигуры нет. Клетка пустая.
			// Перерисуем панель доски без подсказок.
			boardPanel.update();
			boardPanel.redraw();
			return;
		}

		// Доска на которой расположены фигуры.
		Board board = boardPanel.board;
		
		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++) {
				Square target = board.getSquare(v, h);
				
				if (underMousePiece.isCorrectMove(target))
					prompted.add(target);
			}

		// Перерисуем панель доски c подсказками для
		// клеток на которые допустим ход фигуры.
		boardPanel.update();
		boardPanel.redraw();
	}
}