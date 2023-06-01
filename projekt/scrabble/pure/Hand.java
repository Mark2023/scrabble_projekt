/*
 * Marek Wiśniewski 338782
 * Hand - klasa ręki(stojaka) gracza
 * 31.05.2023
 */
package scrabble.pure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Hand implements Iterable<Tile>{
    public Tile[] tiles;
    public Hand()
    {
        tiles = new Tile[7];
        for(int i = 0; i < 7; i++)
            tiles[i] = new Tile();
    }
    public Iterator<Tile> iterator()
    {
        return new TileIterator(this);
    }
    public Boolean isEmpty()
    {
        boolean empty = true;
        for(Tile tile : tiles)
        {
            if(!tile.isEmpty()) empty = false;
        }
        return empty;
    }
    public int getPoints()
    {
        int sum = 0;
        for(Tile tile : tiles)
        {
            if(!tile.isEmpty())
                sum += tile.getLetter().getPoints();
        }
        return sum;
    }
    public boolean remove(Character ch)
    {
        for(Tile tile : tiles)
        {
            if(!tile.isEmpty() && tile.getLetter().equals(ch))
            {
                tile.setLetter(null);
                return true;
            }
        }
        return false;
    }
    public List<Character> getChars()
    {
        LinkedList<Character> chars = new LinkedList<Character>();
        for(Tile tile : tiles)
        {
            if(!tile.isEmpty())
                chars.add(tile.getLetter().getFullLetter());
        }
        return chars;
    }
    public String toString()
    {
        char[] chars = new char[7];
        for(int i = 0; i < 7; i++)
        {
            if(tiles[i].isEmpty())
                chars[i]='_';
            else
                chars[i] = tiles[i].getLetter().getFullLetter();
        }
        return String.valueOf(chars);
    }
}

class TileIterator implements Iterator<Tile>
{
    int c;
    Hand hand;
    TileIterator(Hand hand)
    {
        this.hand = hand;
        c=0;
    }
    public boolean hasNext()
    {
        return c<7;
    }
    public Tile next()
    {
        if(hasNext())
        {
            c++;
            return hand.tiles[c-1];
        }
        else return null;
    }

}
