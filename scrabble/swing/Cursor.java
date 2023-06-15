/*
 * Marek Wiśniewski 338782
 * Cursor - klasa GUI kursora 
 * 31.05.2023
 */
package scrabble.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import scrabble.Game;
import scrabble.pure.*;
import scrabble.pure.actions.Swap;
import scrabble.pure.actions.Turn;

public class Cursor implements ActionListener {
    private Letter letter;
    public JLabel label = new JLabel();
    public MouseMotionAdapter listener;
    boolean set;
    Game game;
    public Cursor(Game game)
    {
        this.game = game;
        listener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                Point loc = e.getLocationOnScreen();
                Point rel = label.getParent().getLocationOnScreen();
                loc.x = loc.x - rel.x;
                loc.y = loc.y - rel.y;
                label.setLocation(loc.x - SwingTile.size/2
                                , loc.y - SwingTile.size/2);
            }
        
        };
        set = false;
    }
    public void display()
    {
        if(!set)
        {
            label.setOpaque(true);
            label.setSize(SwingTile.size,SwingTile.size);
            label.setBackground(SwingTile.myLetterColor);
            label.setFont(SwingTile.font);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            set = true;
        }
        if(letter == null)
        {
            label.setVisible(false);
        }
        else
        {
            label.setText(letter.toString());
            label.setVisible(true);
            label.getParent().setComponentZOrder(label,0);
        }
    }
    public Letter getLetter()
    {
        return letter;
    }
    public void setLetter(Letter l)
    {
        letter = l;
        if(l != null)
            l.setTile(null);
    }
    public void actionPerformed(ActionEvent e) {
        Turn turn = game.getTurn();
        synchronized (turn)
        {
            SwingTile stile = (SwingTile) e.getSource();
            Tile tile = stile.tile;
            Letter letter = tile.getLetter();
            Letter c_letter = getLetter();
            if(c_letter != null && letter == null)
            {
                if(turn.add(tile,c_letter))
                {
                    setLetter(null);
                }
            }
            else if(letter != null && !tile.fixed) 
            {
                if(c_letter == null)
                {
                    setLetter(turn.remove(tile));
                }
                else
                {
                    new Swap(game,letter,c_letter).swap();
                }
            }
            else if(letter != null && c_letter != null && letter.getPoints() == 0 && c_letter.getPoints() != 0)
            {
                turn.swap(c_letter,tile);
            }
            stile.display();
            display();
        }
    }
}

