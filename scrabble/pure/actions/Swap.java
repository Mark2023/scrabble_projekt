/*
 * Marek Wiśniewski 338782
 * Swap - klasa Zamiany liter
 * 31.05.2023
 */
package scrabble.pure.actions;

import scrabble.Game;
import scrabble.pure.*;

public class Swap {
    Game game;
    Letter letter_1;
    Letter letter_2;
    public Swap(Game game,Letter letter_1,Letter letter_2) 
    {
        this.game = game;
        this.letter_1 = letter_1;
        this.letter_2 = letter_2;
    }
    public void swap()
    {
        Tile tile_1 = letter_1.getTile();
        Tile tile_2 = letter_2.getTile();
        if(tile_1 == null)
            game.getCursor().setLetter(letter_2);
        else
            tile_1.setLetter(letter_2);
        if(tile_2 == null)
            game.getCursor().setLetter(letter_1);
        else
            tile_2.setLetter(letter_1);
        
    }
}
