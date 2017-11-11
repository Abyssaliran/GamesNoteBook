package game.core;

/**
 * Ситуация окончания игры.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameOver extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GameResult result;
	
	public GameOver(GameResult result) {
		this.result = result;
	}
}
