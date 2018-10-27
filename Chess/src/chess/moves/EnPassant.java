package chess.moves;

import java.util.List;

import chess.pieces.Pawn;
import game.core.Board;
import game.core.Piece;
import game.core.Square;

/**
 * Ход европейских шахмат - взятие пешки на проходе.
 * TODO Zhdanov
 *https://ru.wikipedia.org/wiki/Взятие_на_проходе
 *  
 * @author <a href="mailto:ramzes.zhdanov@mail.ru">Zhdanov R.A.</a>
 */
public class EnPassant extends Capture implements ICapture {
	
	public Square m_EnpassantCapturedSquare;
	public EnPassant(Square[] squares, Square enemy_square) {
		super(squares);		
		capturedSquare = enemy_square;
		capturedPiece = enemy_square.getPiece();
	}

	@Override
	//Сделать ход
	public void doMove() {
		capturedPiece.remove();
		super.doMove();
	}

	@Override
	//Отменить ход
	public void undoMove() {
		super.undoMove();		
		capturedSquare.setPiece(capturedPiece);		
	}

	@Override
	//Удалить фигуру
	public void removePiece() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	//Восстановить фигуру
	public void restorePiece() {
		// TODO Auto-generated method stub
		
		
	}
}
