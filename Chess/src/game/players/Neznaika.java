package game.players;

import java.util.List;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Незнайка - простой игрок для игр в которых передвигают фигуры. 
 * Он случайным образом выбирает фигуру нужного цвета из стоящих на доске.
 * Потом случайным образом делает ход из всех возможных ходов для этой фигуры.
 * Если у этой фигуры нет допустимого хода, случайным образом выбирает фигуру еще раз.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Neznaika implements IPlayer {
	@Override
	public String getName() {
		return "Neznaika";
	}

	@Override
	public void doMove(Board board, PieceColor color) {
		List<Piece> pieces = board.getPieces(color);
		
		if (pieces.isEmpty())
			return;

		int nPieces = pieces.size();

		for (int k = 0; k < nPieces; k++) {
			// Получаем случайную фигуру.
			int randomN = (int) (Math.random() * nPieces);
			Piece randomPiece = pieces.get(randomN);

			// Получаем список допустимых для хода клеток.
			List<Square> targets = board.getPieceTargets(randomPiece);
			if (targets.isEmpty())
				continue; // Допустимых ходов у фигуры нет.

			// Получаем случайную клетку.
			int nTargets = targets.size();
			int randomK = (int) (Math.random() * nTargets);
			Square randomTarget = targets.get(randomK);
			
			// Делаем ход на случайную клетку.
			Square source = randomPiece.square;
			
			Move randomMove = randomPiece.makeMove(source, randomTarget);
			board.history.addMove(randomMove);
			randomMove.doMove();
			
			break;
		}
		
		// Передаем ход противнику.
		board.changeMoveColor();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
