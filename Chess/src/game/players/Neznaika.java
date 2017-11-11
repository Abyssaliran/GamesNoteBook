package game.players;

import java.util.List;

import game.core.Board;
import game.core.GameOver;
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
		// Берем все фигуры заданного цвета. 
		List<Piece> pieces = board.getPieces(color);
		
		if (pieces.isEmpty())
			return;

		while ( !pieces.isEmpty() ) {
			// Берем случайную фигуру из списка фигур.
			Piece randomPiece = getRandomPiece(pieces);

			// Получаем список допустимых клеток для хода этой фигурой.
			List<Square> targets = board.getPieceTargets(randomPiece);
			if (targets.isEmpty()) {
				// Допустимых ходов у этой фигуры нет.
				// Исключим фигуру из списка возможных фигур
				// и попробуем выбрать фигуру из оставшихся.
				pieces.remove(randomPiece);
				continue; 
			}

			// Откуда идем - клетка где стоит эта фигура.
			Square source = randomPiece.square;
			
			// Куда идем - получаем случайную клетку 
			// из всех возможных клеток для этой фигуры.
			Square target = getRandomSquare(targets);
			
			// Создаем ход на случайную клетку и делаем его.
			Move randomMove = randomPiece.makeMove(source, target);
			try {
				randomMove.doMove();
			} catch (GameOver e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Сохраняем ход в истории партии.
			board.history.addMove(randomMove);
			
			// Выходим из цикла случайного выбора фигуры
			// которая может сделать правильный ход.
			break;
		}
		
		// Передаем ход противнику.
		board.changeMoveColor();
	}

	/**
	 * Выдать случайную клетку из списка клеток.
	 * @param squares - список клеток.
	 * @return клетка выбранная случайным образом.
	 */
	private Square getRandomSquare(List<Square> squares) {
		return squares.get((int) (Math.random() * squares.size()));
	}

	/**
	 * Выдать случайную фигуру из списка фигур.
	 * @param pieces - список фигур.
	 * @return фигура выбранная случайным образом.
	 */
	private Piece getRandomPiece(List<Piece> pieces) {
		int random = (int) (Math.random() * pieces.size());
		return pieces.get(random);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
