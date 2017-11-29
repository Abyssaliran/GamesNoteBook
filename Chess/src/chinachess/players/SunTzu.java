package chinachess.players;

import java.util.Comparator;
import java.util.List;

import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import chinachess.pieces.King;

/**
 * Сунь-Цзы - автор знаменитого трактата о военной стратегии «Искусство войны».<br>
 * <a href="http://militera.lib.ru/science/sun-tszy/01.html">Сунь-Цзы. Искусство войны</a>
 */
public class SunTzu extends ChinaChessPlayer {
	private Comparator<? super Move> brain 
		= (m1, m2) -> getWeight(m2) - getWeight(m1); 
	
	@Override
	public String getName() {
		return "Сунь-Цзы";
	}

	@Override
	public String getAuthorName() {
		return "Дмитрив Ярослав";
	}
	
	/**
	 * Выдать случайную фигуру из списка фигур.
	 * @param moves - список фигур.
	 * @return фигура выбранная случайным образом.
	 */
	@SuppressWarnings("unused")
	private Move getRandomMove(List<Move> moves) {
		int random = (int) (Math.random() * moves.size());
		return moves.get(random);
	}

	Comparator<? super Move> getComparator() {
		return brain;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * Задать вес для хода.
	 * @param move - ход
	 * @return оценка хода.
	 */
	private int getWeight(Move move) {
		ITransferMove transfer = (ITransferMove) move;
		
		Square source = transfer.getSource();
		Square target = transfer.getTarget();
		Piece thePiece = source.getPiece();

		if (move instanceof ICaptureMove) {
			// Ход - взятие фигуры врага.
			ICaptureMove capture = (ICaptureMove) move;
			
			Square capturedSquare = capture.getCaptured().get(0);
			Piece  capturedPiece  = capturedSquare.getPiece();
			
			// У захвата короля врага наивысший приоритет.
			if (capturedPiece instanceof King)
				return 1000;
			
			// Пока берем любую фигуру.
			return 999;
		}
		
		// Из всех ходов без взятия фигуры врага лучший ход
		// который максимально приближает к королю врага.
		King enemyKing = getEnemyKing(thePiece);
		int stepWeight = MAX_DISTANCE - distance(target, enemyKing.square);
		
		return stepWeight; 
//		return getSquareWeight(target);
	}
}