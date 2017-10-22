package game.ui.listners;

import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.ui.GameBoard;

/**
 * Слушатель постановки перемещения фигуры на доске.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class MovePieceListener implements IGameListner {
	private Piece selectedPiece;
	private Square selectedSquare;
	private Cursor savedCursor;

	/**
	 * Цвет текущего хода.
	 */
	private PieceColor moveColor = PieceColor.WHITE;
	
	/**
	 * Доска на которой присходят изменения.
	 */
	private Board board;
	
	private GameBoard panel;

	public MovePieceListener(GameBoard panel) {
		this.board = panel.board;
		this.panel = panel;
	}
	
	/**
	 * @param сolor 
	 * @return
	 */
	public PieceColor getOponentColor(PieceColor сolor) {
		return сolor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
	}
	
	@Override
	public void mouseDown(Square mouseSquare, int button) {
		if (mouseSquare.isEmpty())
			return;
		
		selectedPiece = mouseSquare.getPiece();
		if (selectedPiece.getColor() != moveColor)
			return;
		
		savedCursor = panel.getCursor();
		
		selectedSquare = mouseSquare;
		mouseSquare.removePiece();
		
		panel.imageToCursor( getPieceImage(selectedPiece, moveColor) );
	    
		board.setBoardChanged();
		panel.redraw();
	}
	
	@Override
	public void mouseUp(Square mouseSquare, int button) {
		if (selectedSquare == null)
			return;
		
		// Возвращаем фигуру на начальную клетку. 
		selectedSquare.setPiece(selectedPiece);
		
		if (selectedPiece.isCorrectMove(mouseSquare)) {
			Move move = selectedPiece.makeMove(selectedSquare, mouseSquare);
			move.doMove();
			
			board.history.addMove(move);
			
			// TODO Реализовать запрос фигуры для превращения пешки.

			moveColor = (moveColor == PieceColor.WHITE 
							? PieceColor.BLACK 
							: PieceColor.WHITE);
		}

		selectedPiece = null;
		selectedSquare = null;
		
		panel.setCursor(savedCursor);

		board.setBoardChanged();
		panel.redraw();
	}
	
	/**
	 * Дать изображение для фигуры заданного цвета.
	 * 
	 * @param piece - фигура.
	 * @param color - цвет фигуры.
	 * @return - изображение для фигуры заданного цвета.
	 */
	abstract public Image getPieceImage(Piece piece, PieceColor color);
}
