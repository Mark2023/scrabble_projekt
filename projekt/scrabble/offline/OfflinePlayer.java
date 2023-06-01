/*
 * Marek Wiśniewski 338782
 * OfflinePlayer - klasa gracza offline
 * 31.05.2023
 */
package scrabble.offline;

import scrabble.Game;
import scrabble.PlayerHandler;
import scrabble.pure.*;
import scrabble.pure.actions.*;


public class OfflinePlayer extends PlayerHandler{
    OfflinePlayer(String name,Game game)
    {
        super(game,new Player(name));
    }
    @Override
    public void start_of_turn()
    {
        getGame().getTurn().on_board = getPlayer();
        getGame().getTurn().move = new Move(getGame());
    }
    @Override
    public void end_of_turn()
    {
        Hand discard = getGame().getBoard().getDiscard();
        boolean discard_empty = discard.isEmpty();
        if(!discard_empty)
            getGame().getBag().put(discard);
        
        getGame().getTurn().end_turn(discard_empty);
        getGame().getBag().draw(getPlayer().hand);
    }
    public void start_of_game()
    {
        getGame().getBag().draw(getPlayer().hand);
    }

}
