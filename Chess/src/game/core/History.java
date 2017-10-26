package game.core;

import java.util.ArrayList;
import java.util.List;

/**
 * История игры (список ходов сделанных фигурами на доске).
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class History {
	/**
	 * Номер текущего хода на доске.
	 */
	int curMove = -1;
	
	/**
	 * Список ходов сделанных на доске.
	 */
	List<Move> moves = new ArrayList<>();

	private Board board;

	public History(Board board) {
		this.setBoard(board);
	}

	/**
	 * Добавить ход в историю игрыs.
	 * @param move
	 */
	public void addMove(Move move) {
		moves.add(move);
		curMove++;
	}
	
	/**
	 * @return
	 */
	public List<Move> getMoves() {
		return moves;
	}

	public int getCurMoveNumber() {
		return curMove;
	}
	
	public Move getCurMove() {
		return curMove == -1 ? null : moves.get(curMove);
	}
	
	public void toFirstMove() {
		for (; curMove >=1; curMove--)
			moves.get(curMove).undoMove();
	}
	
	public void toLastMove() {
		for (; curMove < moves.size()-1; curMove++)
			moves.get(curMove).doMove();
	}
	
	public void toNextMove() {
		if (curMove < moves.size()-1) 
			moves.get(++curMove).doMove();
	}
	
	public void toPrevMove() {
		if (curMove >= 0) 
			moves.get(curMove--).undoMove();
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return Вернуть последний ход.
	 */
	public Move getLastMove() {
		if (!moves.isEmpty()) 
			return moves.get( moves.size()-1 );
			
		return null;
	}

	/**
	 * @param move 
	 */
	public int getMoveNumber(Move move) {
		return moves.indexOf(move);
	}

	/**
	 * @param n
	 */
	public void toMove(int n) {
		if (n < curMove)
			while (n < curMove)
				toPrevMove();
		
		if (curMove < n)
			while (curMove < n)
				toNextMove();
	}

}
