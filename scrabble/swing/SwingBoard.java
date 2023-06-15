/*
 * Marek Wiśniewski 338782
 * SwingBoard - klasa GUI planszy 
 * 31.05.2023
 */
package scrabble.swing;

import javax.swing.*;

import scrabble.Game;

import java.awt.*;

public class SwingBoard extends JPanel {
    SwingTile[] tiles;
    private SwingDiscard discard;
    public SwingBoard(Game game) 
    {
        discard = new SwingDiscard(game,game.getBoard().getDiscard());

        Dimension board_screen = new Dimension(15 * SwingTile.size, 15 * SwingTile.size);
        setLayout(new GridLayout(15, 15));
        setLocation(SwingTile.size * SwingGame.side_size, 0);
        setSize(board_screen);
        tiles = new SwingTile[15*15];
        for(int i = 0; i < 15*15; i++)
        {
            tiles[i] = new SwingTile(game,game.getBoard().getTile(i % 15, i / 15));
            add(tiles[i]);
            tiles[i].display();
        }
        
        discard.refresh();
    }
    public void refresh()
    {
        for(SwingTile tile : tiles)
            tile.display();
        discard.refresh();
    }
    public SwingDiscard getDiscard() { return discard; }
}
