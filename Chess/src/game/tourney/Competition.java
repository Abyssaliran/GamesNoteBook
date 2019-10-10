package game.tourney;

import game.players.IPlayer;

import java.util.List;

/**
 * Абстрактное соревнование между игроками
 */
public abstract class Competition {
    public final List<IPlayer> players;
    protected List<IPlayer> winners;

    public Competition(List<IPlayer> players) {
        this.players = players;
    }

    abstract void run();

    /**
     * Выдать список победителей
     *
     * @return победители
     */
    List<IPlayer> getWinners() {
        return winners;
    }
}