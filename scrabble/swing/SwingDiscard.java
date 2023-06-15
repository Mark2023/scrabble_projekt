/*
 * Marek Wiśniewski 338782
 * SwingDiscard - klasa GUI do odrzucania liter 
 * 31.05.2023
 */
package scrabble.swing;

import scrabble.Game;
import scrabble.pure.*;

public class SwingDiscard extends SwingHand {
    public SwingDiscard(Game game,Hand hand) 
    {
        super(game);
        setHand(hand);
        setBounds((int)(SwingTile.size * (SwingGame.side_size + 8.7)),
                  (int)(SwingTile.size * 15.5),
                  SwingTile.size * 6,
                  SwingTile.size *6 / 7);
        setVisible(true);
    }
}
