package tamerlan.playres;

import java.util.Comparator;

import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import tamerlan.pieces.*;

/**
 * Тохтамыш - хан Золотой Орды.
 * 
 */
public class Tuqtamish extends TamerlanChessPlayer {
	private final Comparator<? super Move> brain
		= (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	@Override
	public String getName() {
		return "Тохтамыш (З.Орда)";
	}

	@Override
	public String getAuthorName() {
		return "Романов В.Ю.";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	Comparator<? super Move> getComparator() {
		return brain;
	}

	/**
	 * Задать вес для хода.
	 * @param move - ход
	 * @return оценка хода.
	 */
	private int getMoveWeight(Move move) {
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
            /**
             * mean pieces weights
             * 'pawn': 1,
             * 'knight': 3.5,
             * 'bishop': 3,
             * 'rook': 5,
             * 'queen': 9,
             * 'giraf': 12.5,
             * 'visir': 6.5,
             * 'warmachine': 8.5
             */
            double[] values = {12.5, 9, 8.5, 6.5, 5, 3.5, 3, 1};
            int[] values_correct = new int[8];
            for (int i = 0; i < values.length; i++) {
                values_correct[i] = (int) (870 + values[i] * 5);
            }
            if (capturedPiece instanceof Giraffe)
                return values_correct[0];
            if (capturedPiece instanceof Queen)
                return values_correct[1];
            if (capturedPiece instanceof WarMachine)
                return values_correct[2];
            if (capturedPiece instanceof Vizir)
                return values_correct[3];
            if (capturedPiece instanceof Rook)
                return values_correct[4];
            if (capturedPiece instanceof Knight)
                return values_correct[5];
            if (capturedPiece instanceof Bishop)
                return values_correct[6];

			// Пока берем любую фигуру.
            return values_correct[7];
		}
		
		// Из всех ходов без взятия фигуры врага лучший ход
		// который максимально приближает к королю врага.
		King enemyKing = getEnemyKing(thePiece);
		int stepWeight = MAX_DISTANCE - distance(target, enemyKing.square);
		
		return stepWeight; 
//		return getSquareWeight(target);
	}
}