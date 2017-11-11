package game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.players.IPlayer;

/**
 * Общий предок всех игр.
 *  
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Game {
	
	/**
	 * Карта для регистрации игроков для каждой из игр.
	 */
	private static 
	Map<Class<? extends Game>, List<IPlayer>> allPlayers = new HashMap<>();

	/**
	 * Добавить игрока для данной игры.
	 * 
	 * @param game - игра для которой добавляют игрока.
	 * @param player - игрок (программа или человек).
	 */
	public static void addPlayer(Class<? extends Game> game, IPlayer player) {
		List<IPlayer> players = allPlayers.get(game);
		
		if (players == null) {
			// Игроков для данной игры еще не добавляли.
			players = new ArrayList<>();
			allPlayers.put(game, players);
		}
		
		players.add(player);
	}
	
	/**
	 * Получить список всех игрокода для заданной игры.
	 * @param game - заданная игра.
	 * @return список игроков для этой игры.
	 */
	public List<IPlayer> getPlayers(Class<? extends Game> game) {
		return allPlayers.get(game);
	}
}
