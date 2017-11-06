package game.players;

import java.util.ArrayList;
import java.util.List;

import game.core.Board;
import game.core.Drawn;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.Win;

/**
 * Винни - простой игрок для игр в которых ставятся фигуры на доску.
 * Он случайным образом выбирает клетку на которую можно поставить фигуру.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Vinni implements IPlayer {
	private IPieceProvider pieceProvider;

	/**
	 * Винни - простой игрок для игр в которых ставятся фигуры на доску.
	 * Он случайным образом выбирает клетку на которую можно поставить фигуру.
	 */
	public Vinni(IPieceProvider pieceProvider) {
		this.pieceProvider = pieceProvider;
	}

	@Override
	public String getName() {
		return "Vinni";
	}

	@Override
	public void doMove(Board board, PieceColor color) {
		PieceColor moveColor = board.getMoveColor();

		// Берем все пустые клетки на доске.
		List<Square> emptySquares = board.getEmptySquares();
		if (emptySquares.isEmpty())
			return; // Пустых клеток нет, ходить некуда.

		// Получим фигуру НЕ стоящую на клетке.
		Square square = emptySquares.get(0);
		Piece piece = pieceProvider.getPiece(square, moveColor);
		piece.remove(); // Уберем фигуру с клетки доски.
		
		// Соберем пустые клетки, на которые можно поставить 
		// новую фигуру заданного цвета.
		List<Square> allTargets = new ArrayList<>();

		for (Square emptySquare : emptySquares)  
			if (piece.isCorrectMove(emptySquare))
				allTargets.add(emptySquare);
		
		if (allTargets.isEmpty())
			return; // Нет правильных ходов на все пустые клетки.
		
		// Выбираем случайную клетку из возможных клеток.
		Square randomTarget = getRandomSquare(allTargets);
		
		// Создаем ход на случайную клетку и делаем его.
		Move randomMove = piece.makeMove(randomTarget);
		try {
			randomMove.doMove();
		} catch (Win e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Drawn e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Сохраняем ход в истории партии.
		board.history.addMove(randomMove);

		// Передаем ход противнику.
		board.changeMoveColor();
	}

	/**
	 * Выбрать из списка клеток случайную клетку.
	 * 
	 * @param squares - список клеток.
	 * @return случайная клетка.
	 */
	private Square getRandomSquare(List<Square> squares) {
		int nTargets = squares.size();
		int randomK = (int) (Math.random() * nTargets);
		return squares.get(randomK);
	}
}
