/*
 * Marek Wiśniewski 338782
 * SwingTile - klasa GUI pola 
 * 31.05.2023
 */
package scrabble.swing;

import scrabble.Game;
import scrabble.pure.*;

import javax.swing.*;
import java.awt.*;


public class SwingTile extends JButton {
    public static int size;
    public static final float font_ratio = 0.5f;
    public static Font font = new Font("Ariel Unicode MS" , Font.PLAIN, 10);
    public static Color letterColor = Color.WHITE;
    public static Color myLetterColor = Color.LIGHT_GRAY;
    public Tile tile;
    public SwingTile(Game game) 
    {
        addActionListener(game.getCursor());
        setFocusPainted(false);
        addMouseMotionListener(game.getCursor().listener);
    }
    public SwingTile(Game game,Tile tile)
    {
        this(game);
        this.tile = tile;
    }
    public void display()
    {
        setFont(font);
        if(font.getSize() != size * font_ratio)
            font = font.deriveFont(size * font_ratio);
        setMargin(new Insets(0,0,0,0));
        Color color;
        if(tile.active || tile.getLetter() == null)
            color = tile.color;
        else if(!tile.fixed)
            color = myLetterColor;
        else
            color = letterColor;
        setBackground(color);

        if(tile.getLetter() == null)
            setText("");
        else 
            setText(tile.getLetter().toString());
        setVisible(true);
    }
}
