package game.ui.listeners;

import java.util.List;

import org.eclipse.swt.graphics.Image;

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
		
		// Получим фигуру НЕ стоящую на клетке.
		PieceColor moveColor = board.getMoveColor();
		Piece piece = getPiece(mouseSquare, moveColor);
		piece.remove(); // Уберем фигуру с доски.
		
		// Зададим изображение курсора такое как избражение у фигуры.
		Image pieceImage = getPieceImage(piece, moveColor);
		boardPanel.imageToCursor(pieceImage);

		// Клетки, на которые можно поставить новую фигуру.
		// Выдадим для этих клеток подсказки игроку.
		List<Square> prompted = boardPanel.prompted;

		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++) {
				Square target = board.getSquare(v, h);
				
				if (piece.isCorrectMove(target))
					prompted.add(target);
			}
		
		// Перерисуем панель доски c подсказками для тех
		// клеток на которые допустима постановка фигуры.
		boardPanel.update();
		boardPanel.redraw();
	}
	
	/**
	 * Выдать изображение фигуры заданного цвета.
	 * 
	 * @param piece - фигура.
	 * @param color - цвет фигуры.
	 * @return - изображение фигуры заданного цвета.
	 */
	abstract public Image getPieceImage(Piece piece, PieceColor color);

	/**
	 * Выдать фигуру заданного цвета.
	 * 
	 * @param square - клетка для фигуры.
	 * @param color - цвет фигуры.
	 * @return - фигура заданного цвета.
	 */
	abstract public Piece getPiece(Square square, PieceColor color);
}
