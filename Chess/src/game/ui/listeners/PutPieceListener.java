package game.ui.listeners;

import org.eclipse.swt.graphics.Image;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;

/**
 * Слушатель постановки новой фигуры на доску.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class PutPieceListener implements IGameListner {
	/**
	 * Доска на которой присходят изменения.
	 */
	private Board board;
	
	/**
	 * Панель для отрисовки доски.
	 */
	private GameBoard panel;

	public PutPieceListener(GameBoard panel) {
		this.board = panel.board;
		this.panel = panel;
	}
	
	@Override
	public void mouseUp(Square s, int button) {}
	
	@Override
	public void mouseDown(Square mouseSquare, int button) {
		if (!mouseSquare.isEmpty())
			return;
		
		// Получим фигуру НЕ стоящую на клетке.
		PieceColor moveColor = board.getMoveColor();
		Piece piece = getPiece(mouseSquare, moveColor);
		piece.remove();
		
		if (!piece.isCorrectMove(mouseSquare)) 
			return;

		Move move = piece.makeMove(mouseSquare);
		board.history.addMove(move);
		move.doMove();
		
		PieceColor oponentColor = board.getOponentColor(moveColor);
		Image pieceImage = getPieceImage(piece, oponentColor);
		panel.imageToCursor(pieceImage);
		
		board.setBoardChanged();
		panel.redraw();
		
		board.changeMoveColor();
	}
	
	/**
	 * Дать изображение для фигуры заданного цвета.
	 * 
	 * @param piece - фигура.
	 * @param color - цвет фигуры.
	 * @return
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
