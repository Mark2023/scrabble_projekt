/*
 * Marek Wiśniewski 338782
 * SwingHand - klasa GUI ręki(stojaka) gracza 
 * 31.05.2023
 */
package scrabble.swing;

import javax.swing.*;

import scrabble.Game;
import scrabble.pure.Hand;

import java.awt.*;

public class SwingHand extends JPanel{
    public SwingTile[] tiles;
    public SwingHand(Game game) 
    {
        super();
        tiles = new SwingTile[7];
        for(int i=0; i<7; i++)
        {
            tiles[i] = new SwingTile(game);
            add(tiles[i]);
        }
        setLayout(new GridLayout(1, 7));
        setVisible(true);
    }
    public void setHand(Hand hand)
    {
        setBounds(0,
                  SwingTile.size * 16,
                  SwingTile.size * 7,
                  SwingTile.size);
        for(int i=0;i<7;i++)
        {
            tiles[i].tile = hand.tiles[i];
            tiles[i].display();
        }
    }
    public void refresh()
    {
        for(SwingTile tile : tiles) tile.display();
    }
}
