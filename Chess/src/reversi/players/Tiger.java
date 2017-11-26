package reversi.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;
import game.core.moves.PassMove;

/**
 * Тигра - игрок в реверси:<br>
 * <ul>
 * <li> Знает что фигуры в углах доски окружить невозможно.</li> 
 * <li> Знает что фигуры на краях окружить сложнее чем в центре доски.</li>
 * <li> Выбирает ход с захватом максимального количества фигур врага.</li>
 * </ul>
 * 
 * @author Екатерина Козак
 */
public class Tiger extends ReversiPlayer {
	final Comparator<? super Move> brain 
			= (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

	@Override
	public String getName() {
		return "Тигра";
	}

	@Override
	public String getAuthorName() {
		return "Екатерина Козак";
	}
	
	/**
	 * Тигра - игрок в реверси.
	 */
	public Tiger(IPieceProvider pieceProvider) {
		super(pieceProvider);
		this.pieceProvider = pieceProvider;
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		PieceColor enemyColor = Board.getOponentColor(color);
		List<Piece> enemies = board.getPieces(enemyColor);
		
		if (enemies.isEmpty()) {
			// Врагов уже нет. Мы выиграли.
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.win(color));
			
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			
			throw new GameOver( GameResult.win(color) );
		}
		
		List<Move> correctMoves = getCorrectMoves(board, color);

		if (correctMoves.isEmpty()){
			// Пропускаем ход - ничего на доске не делаем.
			// Сохраняем ход-пропуск в истории игры.
			board.history.addMove( new PassMove() );

			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			return;
		}

		// Случайным образом переставим ходы
		// чтобы игра не повторялась.
		Collections.shuffle(correctMoves);
		
		correctMoves.sort(brain);
		
		Move bestMove = correctMoves.get(0);
		
		try { bestMove.doMove(); } 
		catch (GameOver e) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.addMove(bestMove);
			board.history.setResult(e.result);
			
			// Просим обозревателей доски показать 
			// положение на доске, сделанный ход и 
			// результат игры.
			board.setBoardChanged();
			
			throw new GameOver(GameResult.DRAWN);
		}
		
		// Сохраняем ход в истории игры.
		board.history.addMove(bestMove);

		// Просим обозревателей доски показать 
		// положение на доске, сделанный ход и 
		// результат игры.
		board.setBoardChanged();

		// Для отладки ограничим количество ходов в игре.
		// После этого результат игры ничья.
		if (board.history.getMoves().size() > 80) {
			// Сохраняем в истории игры последний сделанный ход 
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);
			
			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}
	}

	/**
	 * Определить стоимость хода.
	 * 
	 * @param move
	 *            - ход который оцениваем.
	 * @return стоимость хода.
	 */
	private int getMoveWeight(Move move) {
		IPutMove putMove = (IPutMove) move;
		
		Square target = putMove.getTarget();
		
		if (isCorner(target))
			return 1000; // Встали в угол.
		
		if (isBorder(target))
			return 900; // Встали на край доски.
		
		if (isBigCross(target))
			return 800;
		
		if (isSquareX(target))
			return -900;
		
		if (isSquareC(target))
			return -800;
		
		if (move instanceof ICaptureMove) {
			// Ход - взятие фигур врага.
			ICaptureMove capture = (ICaptureMove) move;
			
			// Правило реверси - брать меньше фигур!
			// В результате - больший выбор ходов потом.
			// Фигуры противника заберем в конце игры.
			return 64 - capture.getCaptured().size();
		}
		
		return 0; 
	}
	
	/**
	 * Если вы сыграете на это поле, ваш противник легко займет угол.
	 * 
	 * @param target
	 *            - проверяемая клетка.
	 * @return
	 */
	public boolean isSquareX(Square target) {
		// TODO Козак
		// http://othello.gomel.by/stepanov/x-pole/
		return false;
	}

	/**
	 * Вторым наиболее плохим полем является C-поле. Оно само уже находится на
	 * стороне и для того чтобы через него попасть в угол, надо постараться
	 * немного больше.
	 * 
	 * @param target
	 *            - проверяемая клетка.
	 * @return
	 */
	public boolean isSquareC(Square target) {
		// TODO Козак
		// http://othello.gomel.by/stepanov/c-pole/
		return false;
	}

	/**
	 * Эта клетка на большом кресте? Ход на крестдаёт возможность делать
	 * минимальные ходы и в будущем, а не только сейчас. Ваши фишки будут все
	 * время внутри соперника, а его снаружи, что очень приветствуется в Отелло.
	 * 
	 * @param target
	 *            - проверяемая клетка.
	 * @return
	 */
	public boolean isBigCross(Square target) {
		// TODO Козак
		// http://othello.gomel.by/stepanov/bolwoy-krest/
		return false;
	}
}