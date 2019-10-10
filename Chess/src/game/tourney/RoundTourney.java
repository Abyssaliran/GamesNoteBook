package game.tourney;

import game.players.IPlayer;

import java.util.List;

/**
 * Турнир по круговой системе.
 */
public class RoundTourney extends Competition {
    public RoundTourney(List<IPlayer> players) {
        super(players);
    }

    @Override
    void run() {

    }
}
