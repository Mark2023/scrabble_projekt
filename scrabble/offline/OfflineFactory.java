/*
 * Marek Wiśniewski 338782
 * OfflineFactory - klasa tworząca obiekty OfflinePlayer
 * 31.05.2023
 */
package scrabble.offline;

import java.util.List;

import scrabble.*;

public class OfflineFactory implements PlayerFactory{
    String[] names;
    public OfflineFactory(String[] names) {
        this.names = names;
    }
    public boolean setPlayers(List<PlayerHandler> players, Game game) 
    {
        if(2 <= names.length && names.length <= 4)
        {
            for(int i=0;i<names.length;i++)
            {
                players.add(i,new OfflinePlayer(names[i], game));
            }
            return true;
        }
        else return false;
    }
    
    static public void main(String[] args)
    {
        OfflineFactory factory = new OfflineFactory(new String[]{"Gracz1","Gracz2","Gracz3","Gracz4"});
        Game game = new Game(factory);
        game.start_game();
    }
}
