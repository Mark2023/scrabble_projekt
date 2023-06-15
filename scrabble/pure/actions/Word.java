/*
 * Marek Wiśniewski 338782
 * Word - klasa słowa wykładanego na planszę 
 * 31.05.2023
 */
package scrabble.pure.actions;

import java.util.LinkedList;

import scrabble.general.SortedList;
import scrabble.pure.*;

public class Word {
    Boolean poziomo;
    int index;
    SortedList<Integer> indecies;
    int index_start;
    int index_end;
    Board board;
    public Word(Board board)
    {
        this.board = board;
        this.poziomo = null;
        this.index = -1;
        indecies = new SortedList<Integer>();
    }
    public Word(Board board,Boolean poziomo,int index,int start)
    {
        this.board = board;
        this.poziomo = poziomo;
        this.index = index;
        indecies = new SortedList<Integer>();
        indecies.add(start);
    }
    public Boolean add(int x,int y)
    {
        Tile tile = board.getTile(x, y);
        if(tile == null || tile.getLetter() != null)
            return false;
        else if(index == -1)
        {
            index = y;
            indecies.addSorted(x);
            return true;
        }
        else if(poziomo == null)
        {
            if(index == y)
            {
                poziomo = true;
                indecies.addSorted(x);
                return true;
            }
            else if(indecies.getFirst() == x)
            {
                poziomo = false;
                indecies.removeFirst();
                indecies.add(index);
                index = x;
                indecies.addSorted(y);
                return true;
            }
            else return false;
        }
        else if(poziomo && y == index)
        {
            indecies.addSorted(x);
            return true;
        }
        else if(!poziomo && x == index)
        {
            indecies.addSorted(y);
            return true;
        }
        else return false;
    }
    public Boolean remove(int x,int y)
    {
        if(indecies.isEmpty()) return false;
        Integer tmp = -1;
        if((poziomo == null || poziomo) && y == index) tmp = x;
        if(poziomo != null && !poziomo && x == index) tmp = y;
        Boolean result = indecies.remove(tmp);
        if(result && indecies.isEmpty())
            index = -1;
        if(poziomo != null && result && indecies.size() == 1)
        {
            if(!poziomo)
            {
                index = indecies.removeFirst();
                indecies.add(x);
            }
            poziomo = null;
        }
        return result;
    }
    public String getWord()
    {
        if(indecies.isEmpty())
            return "";
        else 
        {
            Boolean poziomo = this.poziomo;

            if(poziomo == null)
            {
                int i = indecies.getFirst();
                poziomo = 
                ((board.getTile(i - 1 , index) != null &&
                    board.getTile(i - 1 , index).getLetter() != null) || 
                (board.getTile(i + 1 , index) != null &&
                    board.getTile(i + 1 , index).getLetter() != null));
                if(!poziomo)
                {
                    int x = indecies.removeFirst();
                    indecies.add(index);
                    index = x;
                }
            }
            index_start = indecies.getLast();
            index_end = indecies.getLast();
            Tile tile;
            for(;(null != (tile = board.getTile(index,index_start - 1,poziomo)) && 
                  tile.getLetter() != null); index_start--);
            for(;(null != (tile = board.getTile(index,index_end + 1,poziomo)) && 
                tile.getLetter() != null); index_end++);
            char[] chars = new char[index_end - index_start + 1];
            for(int i = index_start; i <= index_end; i++)
            {
                tile = board.getTile(index, i, poziomo);
                if(tile == null || tile.getLetter() == null)
                    return "";
                else
                    chars[i - index_start] = tile.getLetter().getLetter();
            }
            return new String(chars);
        }
    }
    public int getPoints()
    {
        int sum = 0;
        int word_multiplier = 1;
        int index_start = indecies.getLast();
        int index_end = indecies.getLast();
        Tile tile;
        for(;(null != (tile = board.getTile(index,index_start - 1,poziomo)) && 
                tile.getLetter() != null); index_start--);
        for(;(null != (tile = board.getTile(index,index_end + 1,poziomo)) && 
            tile.getLetter() != null); index_end++);
        if(index_start == index_end) return 0;
        for(int i = index_start; i <= index_end; i++)
        {
            tile = board.getTile(index, i, poziomo);
            if(tile == null || tile.getLetter() == null)
                return 0;
            else
            {
                sum += tile.getLetter().getPoints() * tile.getLetterMultiplier();
                word_multiplier *= tile.getWordMultiplier();
            }
        }
        return sum * word_multiplier;
    }
    public LinkedList<Integer> getBlanks()
    {
        LinkedList<Integer> blanks = new LinkedList<>();
        if(!indecies.isEmpty())
        {
            index_start = indecies.getLast();
            index_end = indecies.getLast();
            Tile tile;
            for(;(null != (tile = board.getTile(index,index_start - 1,poziomo)) && 
                  tile.getLetter() != null); index_start--);
            for(;(null != (tile = board.getTile(index,index_end + 1,poziomo)) && 
                tile.getLetter() != null); index_end++);
            for(int i = index_start; i <= index_end; i++)
            {
                tile = board.getTile(index, i, poziomo);
                if(tile == null || tile.getLetter() == null)
                    return blanks;
                else if(tile.getLetter().getPoints() == 0)
                    blanks.addLast(i);
            }
        }
        return blanks;
    }
}
