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
//	/**
//	 * Цвет текущего хода.
//	 */
//	private PieceColor moveColor = PieceColor.WHITE;
	
	/**
	 * Доска на которой присходят изменения.
	 */
	private Board board;
	
	
	private GameBoard panel;

	public PutPieceListener(GameBoard panel) {
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
	public void mouseUp(Square s, int button) {}
	
	@Override
	public void mouseDown(Square mouseSquare, int button) {
		if (!mouseSquare.isEmpty())
			return;
		
		Piece piece = getPiece(mouseSquare, board.moveColor);
		
		if (!piece.isCorrectMove(mouseSquare)) {
			piece.remove();
			return;
		}

		Move move = piece.makeMove(mouseSquare);
		move.doMove();
		board.history.addMove(move);
		
		board.moveColor = getOponentColor(board.moveColor);
		
		panel.imageToCursor( getPieceImage(piece, board.moveColor) );
	    
		board.setBoardChanged();
		panel.redraw();
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
