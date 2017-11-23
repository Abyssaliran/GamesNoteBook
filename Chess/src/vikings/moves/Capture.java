package vikings.moves;

import java.util.List;
import java.util.stream.Collectors;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import vikings.pieces.VikingsPiece;
import vikings.pieces.Сyning;

/**
 * <pre>
ПОБЕДА БЕЛЫХ
1.	Если им удаётся поставить короля на открытую прямую к одной из таких клеток, 
	они объявляют «Raichi» (шах), если сразу на две прямые — Tuichi (мат).
 
ПОБЕДА ЧЕРНЫХ
1.	Король считается захваченным, когда его окружают с четырёх сторон. 
	При этом сторонами могут считаться угловые клетки, трон, и стороны доски. 
	Король может быть захвачен вместе с одной белой фишкой, 
	будучи окружён чёрными со всех сторон.

2.	Когда королю угрожает опасность быть захваченным следующим ходом, 
	чёрные предупреждают белых (шах королю).
 
УНИЧТОЖЕНИЕ ФИШЕК
1.	Если фишка своим ходом зажимает фишку противника между собой и другой фишкой 
	или между собой и угловым квадратом, фишка противника считается съеденной. 
	Съедаться может более одной фишки за раз.
	
2.	Черные фишки может входить между двумя вражескими, в этом случае она не считается съеденной. 
	При этом белые могут спокойно поставить свою фишку между двумя чёрными.
 * </pre>
 */
public class Capture extends SimpleMove implements ICaptureMove {
	/**
	 * Захваченные фигуры противника.
	 */
	private List<Piece> captured;

	public Capture(List<Piece> captured, Square[] squares) {
		super(squares);
		this.captured = captured;
	}

	@Override
	public List<Square> getCaptured() {
		return captured
			.stream()
			.map(p -> p.square)
			.collect( Collectors.toList() );
	}

	@Override
	public void doMove() throws GameOver {
		super.doMove();

		// Для каждой клетки фигуры убираем ссылку на эту фигуру.
		captured.forEach(p -> p.square.removePiece());

		// Дошел ли белый король до выхода?
		if ((piece instanceof Сyning) && VikingsPiece.isExit(piece.square)) 
			throw new GameOver(GameResult.WHITE_WIN);
		
		// Есть ли среди захваченных фигур белый король?
		if (captured.stream().anyMatch(p -> p instanceof Сyning))
			throw new GameOver(GameResult.BLACK_WIN);
	}
	
	@Override
	public void undoMove() {
		super.undoMove();
		
		// Для каждой клетки фигуры ставим ссылку на фигуру.
		captured.forEach(p -> p.square.setPiece(p));
	}

	public String toString() {
		return "" + piece + source + "-" + target;
	}
}
