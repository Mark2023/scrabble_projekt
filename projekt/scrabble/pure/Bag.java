/*
 * Marek Wiśniewski 338782
 * Bag - klasa woreczka z literami
 * 31.05.2023
 */
package scrabble.pure;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Bag {
    private LinkedList<Letter> letters;
    
    private Letter draw()
    {
        if(letters.isEmpty())
            return null;
        Collections.shuffle(letters);
        Letter letter = letters.removeFirst();
        return letter;
    }
    public LinkedList<Character> draw(Hand hand)
    {
        LinkedList<Character> drawn = new LinkedList<>();
        for(Tile tile : hand)
        {
            if(tile.getLetter() == null)
            {
                tile.setLetter(draw());
                drawn.add(tile.getLetter().getFullLetter());
            }
        }
        return drawn;
    }
    private Letter take(Character l)
    {
        int points = (l.equals('.') ? 0 : 1);
        int index = letters.indexOf(new Letter(l, points));
        if(index == -1)
        {
            System.out.println(l);
            System.out.println(letters);
        }
        return letters.remove(index);
    }
    public void take(Hand hand,List<Character> list)
    {
        Iterator<Character> it = list.iterator();
        for(Tile tile : hand)
        {
            if(tile.isEmpty())
                tile.setLetter(take(it.next()));
        }
    }
    public void put(Letter letter)
    {
        if(letter != null)
        {
            letter.setTile(null);
            letters.add(letter);
        }
    }
    public void put(Hand hand)
    {
        for(Tile tile : hand)
        {
            if(!tile.isEmpty())
            {
                put(tile.getLetter());
                tile.setLetter(null);
            }
        }
    }
    public boolean isEmpty()
    {
        return letters.isEmpty();
    }
    public int size()
    {
        return letters.size();
    }
    public Bag(Slownik slownik)
    {
        letters = new LinkedList<Letter>();
        for(Ls l : slownik.letters)
        {
            for(int i=0; i<l.count; i++)
                letters.add(new Letter(l.letter, l.points));
        }
    }
    
}
