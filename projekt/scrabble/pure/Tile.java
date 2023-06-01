/*
 * Marek Wiśniewski 338782
 * Tile - klasa pola(na planszy i stojakach)
 * 31.05.2023
 */
package scrabble.pure;

import java.awt.Color;

public class Tile {
    private Letter letter;
    private int word_multiplier;
    private int letter_multiplier;
    public boolean active;
    public Color color;
    public int position;
    static int counter = 0;
    public boolean fixed;
    public Tile()
    {
        letter = null;
        word_multiplier = 1;
        letter_multiplier = 1;
        active = false;
        fixed = false;
        color = new Color(0x01,0x32,0x20);
        position = counter++;

    }
    public void setMultiplier(int m,boolean w)
    {
        if(w)
        {
            if(m == 2)
                color = new Color(0xff,0xc0,0xcb);
            else if(m == 3)
                color = Color.RED;
            word_multiplier = m;
        }
        else
        {
            if(m == 2)
                color = new Color(60,170,255);
            else if(m == 3)
                color = new Color(0x00,0x67,0xFF);
            letter_multiplier = m;
        }
        active = true;
    }
    public boolean isEmpty()
    {
        return null == letter;
    }
    public Letter getLetter()
    {
        return letter;
    }
    public void setLetter(Letter letter)
    {
        this.letter = letter;
        if(letter != null)
            letter.setTile(this);
    }
    public int getWordMultiplier()
    {
        if(active && word_multiplier != 1)
        {
            active = false;
            return word_multiplier;
        }
        else return 1;
    }
    public int getLetterMultiplier()
    {
        if(active && letter_multiplier != 1)
        {
            active = false;
            return letter_multiplier;
        }
        else return 1;
    }
}
