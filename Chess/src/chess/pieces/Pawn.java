package chess.pieces;

import org.eclipse.swt.graphics.Image;

import chess.ui.images.ChessImages;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Класс представляющий на доске пешку европейских шахмат.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Pawn extends ChessPiece {
	public Pawn(Square square, PieceColor color) {
		super(square, color);
	}

	@Override
	public boolean isCorrectMove(Square... squares) {
		// Пока используем только умалчиваемую проверку
		// выполняемую в базовом классе.
		if (!super.isCorrectMove(squares))
			return false;
		
		return true;
	}

	@Override
	public Move makeMove(Square... squares) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage() {
		return getColor() == PieceColor.BLACK
				? ChessImages.imagePawnBlack
				: ChessImages.imagePawnWhite;
	}
}
