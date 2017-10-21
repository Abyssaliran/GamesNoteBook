package game.core;

/**
 * 8-направлений по вертикали, горизонали и диагоналям.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum Dirs {
	LEFT_UP  (-1, -1), UP  (+0, -1), RIGHT_UP  (+1, -1),
	LEFT     (-1, +0),               RIGHT     (+1, +0),
	LEFT_DOWN(-1, +1), DOWN(+0, +1), RIGHT_DOWN(+1, +1);
	
	public static final 
	Dirs[] ALL = {
		LEFT_UP,     UP, RIGHT_UP,
		LEFT,            RIGHT,
		LEFT_DOWN, DOWN, RIGHT_DOWN
	};
	
	/**
	 * Смещение по вертикали.
	 */
	public int dv;
	
	/**
	 * Смещение по горизонтали.
	 */
	public int dh;
	
	/**
	 * @param dv - смещение по вертикали.
	 * @param dh - смещение по горизонтали.
	 */
	Dirs(int dv, int dh) {
		this.dv = dv;
		this.dh = dh;
	}
}
