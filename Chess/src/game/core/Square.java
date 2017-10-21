package game.core;

/**
 * Клетка на доске настольных игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Square {
	/**
	 * Вертикаль клетки
	 */
	public int v;

	/**
	 * Горизонталь клетки
	 */
	public int h;

	/**
	 * Доска на которой расположена клетка.
	 */
	private Board board;

	/**
	 * Фигура которая, возможно, стоит на клетке.
	 */
	Piece piece;
	
	protected Square(Board board, int v, int h) {
		this.v = v;
		this.h = h;
		this.board = board;
	}

	/**
	 * @return доска на которой стоит клетка.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Поставить на клетку фигуру.
	 * @param piece какую фигуру постаить.
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
		piece.square = this;
	}

	/**
	 * @return стоит ли на клетке фигура?
	 */
	public boolean isEmpty() {
		return piece == null;		
	}
	
	/**
	 * @return фигура которая стоит на клетке.
	 */
	public Piece getPiece() {
		return piece;		
	}

	/**
	 * Удалить фигуру с клетки.
	 */
	public void removePiece() {
		piece = null;		
	}
}

