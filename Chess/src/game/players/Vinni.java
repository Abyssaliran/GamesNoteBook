package game.players;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Винни - простой игрок для игр в которых ставятся фигуры на доску.
 * Он случайным образом выбирает клетку на которую можно поставить фигуру.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Vinni implements IPlayer {
	private IPieceProvider pieceProvider;

	public Vinni(IPieceProvider pieceProvider) {
		this.pieceProvider = pieceProvider;
	}

	@Override
	public String getName() {
		return "Vinni";
	}

	@Override
	public void doMove(Board board, PieceColor color) {
		List<Square> squares = board.getEmptySquares();
		if (squares.isEmpty())
			return;
		
		PieceColor moveColor = board.getMoveColor();

		// Получим фигуру НЕ стоящую на клетке.
		Square square = squares.get(0);
		Piece piece = pieceProvider.getPiece(square, moveColor);
		piece.remove(); // Уберем фигуру с клетки доски.
		
		// Соберем клетки, на которые можно поставить новую фигуру.
		List<Square> targets = new ArrayList<>();

		for (int v = 0; v < board.nV; v++)
			for (int h = 0; h < board.nH; h++) {
				Square target = board.getSquare(v, h);
				
				if (piece.isCorrectMove(target))
					targets.add(target);
			}
		
		if (targets.isEmpty())
			return;
		
		// Выбираем случайную клетку из возможных клеток.
		Square randomTarget = getRandomSquare(targets);
		
		// Делаем ход на случайную возможную клетку.
		Move randomMove = piece.makeMove(randomTarget);
		board.history.addMove(randomMove);
		randomMove.doMove();
		
		// Передаем ход противнику.
		board.changeMoveColor();
	}

	/**
	 * Выбрать из списка клеток случайную клетку.
	 * 
	 * @param targets - список клеток.
	 * @return случайная клетка.
	 */
	private Square getRandomSquare(List<Square> targets) {
		int nTargets = targets.size();
		int randomK = (int) (Math.random() * nTargets);
		return targets.get(randomK);
	}
}
