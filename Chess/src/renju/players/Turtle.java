package renju.players;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.PieceColor;
import game.players.PutPiecePlayer;
import renju.pieces.Stone;

/**
 * Реализовать алгоритм игры в рендзю (крестики - нолики).
 * TODO Синёв  Олег
 *
 */
public class Turtle extends PutPiecePlayer {
	private int MAX_MOVES = 80;
	final Comparator<? super Move> brain = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);
	
	public Turtle(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	private int getMoveWeight(Move m2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return "Тортила";
	}

	@Override
	public String getAuthorName() {
		return "Синёв С. Олег";
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		List<Move> correctMoves = getCorrectMoves(board, color);

		if (correctMoves.isEmpty())
			throw new GameOver(GameResult.DRAWN);

		Collections.shuffle(correctMoves);

		// Буратино выбирает лучший ход.
		correctMoves.sort(brain);
		Move bestMove;
		// if (maxWeight<MAX_WEIGHT_THAT_CAN_BE&&checkWillEnemyWin(color))
		// bestMove=saveMove;
		// else
		bestMove = correctMoves.get(0);

		try {
			bestMove.doMove();
		} catch (GameOver e) {

			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.addMove(bestMove);
			board.history.setResult(e.result);

			// Game over prompt LIKE add 2020-10-01
			int mesg = JOptionPane.showConfirmDialog(null, "Nancy win！！！  " + "\n Do you want to play again ?",
					"Game Over", JOptionPane.YES_NO_OPTION);
			if (mesg == 0) {
				board.reset(15, 15);
				new Stone(board.getSquare(15 / 2, 15 / 2), PieceColor.BLACK);
				board.startGame();
			}

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
		if (board.history.getMoves().size() > MAX_MOVES ) {
			// Сохраняем в истории игры последний сделанный ход
			// и результат игры.
			board.history.setResult(GameResult.DRAWN);

			// Сообщаем что игра закончилась ничьей.
			throw new GameOver(GameResult.DRAWN);
		}

	}
}
