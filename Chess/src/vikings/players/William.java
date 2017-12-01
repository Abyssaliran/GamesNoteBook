package vikings.players;

import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import vikings.moves.Capture;
import vikings.pieces.VikingsPiece;
import vikings.pieces.Сyning;

/**
 * William - викинг в Нормандии 1066 год.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class William extends VikingsPlayer {
	final Comparator<? super Move> brain 
		= (m1, m2) -> getWeight(m2) - getWeight(m1);

	@Override
	public String getName() {
		return "Вильгельм I (Нормандия)";
	}

	@Override
	public String getAuthorName() {
		return "Заблоцкий";
	}

	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	protected Comparator<? super Move> getComparator() {
		return brain;
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

		Board board = source.getBoard();
		Piece piece = source.getPiece();

		int nCaptured = 0;
		
		if (move instanceof ICaptureMove) {
			// Ход - взятие фигур врага.
			Capture capture = (Capture) move;
			
			// Есть ли среди захваченых фигур белвй король?
			List<Piece> captured = capture.getCapturedPieces();
			boolean isKingCapture = captured.stream().anyMatch(p -> p instanceof Сyning);
			
			// Захват вражеского короля получает наибольший приоритет.
			if (isKingCapture)
				return 1000;
			
			// Приоритет у хода с бОльшим количеством взятых фигур.
			nCaptured = captured.size();
		}

		if (piece instanceof Сyning) {
			// Ход белым королем.
			List<Square> exits = VikingsPiece.getExits(board);
			
			// Поиск ближайшего выхода.
			Square nearsExit = exits
					.stream()
					.min((s1, s2) -> s1.distance(target) - s2.distance(target))
					.get();
			
			// Ход королем к ближайшему выходу получает наибольший приоритет.
			int minDistance = nearsExit.distance(target);
			
			if (minDistance == 0)
				return 1000; // Выход короля - наибольший приоритет.
			
			return board.maxDistance() - minDistance;
		}
		
		return nCaptured; 
	}
}