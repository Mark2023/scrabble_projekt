/*
 * Marek Wiśniewski 338782
 * PlayerFactory - interface do tworzenie obsługi graczy 
 * 31.05.2023
 */
package scrabble;

import java.util.List;

public interface PlayerFactory {
    public boolean setPlayers(List<PlayerHandler> players,Game game);
}
