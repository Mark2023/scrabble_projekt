/*
 * Marek Wiśniewski 338782
 * Move - klasa wyłożenia
 * 31.05.2023
 */
package scrabble.pure.actions;



import java.util.Iterator;
import java.util.LinkedList;

import scrabble.pure.*;
import scrabble.Game;

public class Move {
    Word word;
    Game game;
    public Move(Game game)
    {
        this.game = game;
        word = new Word(game.getBoard());
    }
    public Move(Game game,Tile tile)
    {
        this.game = game;
        int x = tile.position % 15;
        int y = tile.position / 15;
        word = new Word(game.getBoard(),null,y, x);
    }
    public Boolean add(Letter letter,Tile tile)
    {
        int x = tile.position % 15;
        int y = tile.position / 15;

        Tile tile1 = game.getBoard().getTile(x, y);
        if(tile1 != null && tile1.getLetter() == null)
        {
            if(word.add(x,y))
            {
                tile.setLetter(letter);
                return true;
            }else return false;
        }
        else if(tile != null && tile.getLetter() == null)
        {
            tile.setLetter(letter);
            return true;
        }
        else return false;
    }
    public Letter remove(Tile tile)
    {
        int x = tile.position % 15;
        int y = tile.position / 15;
        Tile tile1 = game.getBoard().getTile(x, y);
        Letter letter = null;
        if(tile1 != null && tile1.getLetter() != null)
        {
            if(word.remove(x,y))
            {
                letter = tile1.getLetter();
                tile1.setLetter(null);
            }
        }
        else if(tile != null && tile.getLetter() != null)
        {
            letter = tile.getLetter();
            tile.setLetter(null);
        }
        return letter;
    }
    public void retract(Player player)
    {
        Iterator<Tile> it = player.hand.iterator();
        Tile tile,btile;
        for(int i : word.indecies)
        {
            do{
                tile = it.next();
            }while(null != tile.getLetter());

            btile = game.getBoard().getTile(word.index, i, word.poziomo);

            tile.setLetter(btile.getLetter());
            btile.setLetter(null);
        }
        word = new Word(game.getBoard());

        if(game.getCursor().getLetter() != null)
        {
            do{
                tile = it.next();
            }while(null != tile.getLetter());
            tile.setLetter(game.getCursor().getLetter());
            game.getCursor().setLetter(null);
            game.getCursor().display();
        }
    }
    boolean fillBlanks(LinkedList<Tile> blanks,int i,LinkedList<Word> words) 
    {
        if(i == blanks.size())
        {
            for(Word w : words)
            {
                String s = w.getWord();
                if(s.length() > 1 && !game.getSlownik().find(s))
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            Tile tile = blanks.get(i);
            
            for(char c : new char[]
            {'a','ą','b','c','ć','d','e','ę','f','g'
            ,'h','i','j','k','l','ł','m','n','ń','o','ó'
            ,'p','r','s','ś','t','u','w','y','z','ź','ż'})
            {
                tile.getLetter().setLetter(c);
                if(fillBlanks(blanks, i + 1, words))
                    return true;
            }
            return false;
        }
    }
    public boolean check()
    {
        return check(new LinkedList<Word>());
    }
    public boolean check(LinkedList<Word> my_words)
    {
        if(word.index == -1)
        {
            return false;
        }
        if(word.poziomo == null)
        {
            int i = word.indecies.getFirst();
            word.poziomo = 
              ((game.getBoard().getTile(i - 1 , word.index) != null &&
                game.getBoard().getTile(i - 1 , word.index).getLetter() != null) || 
               (game.getBoard().getTile(i + 1 , word.index) != null &&
                game.getBoard().getTile(i + 1 , word.index).getLetter() != null));
            if(!word.poziomo)
            {
                int x = word.indecies.removeFirst();
                word.indecies.add(word.index);
                word.index = x;
            }
        }
        LinkedList<Integer> blank_indecies = word.getBlanks();
        LinkedList<Word> blank_words = new LinkedList<>();
        LinkedList<Tile> blanks = new LinkedList<>();
        for(int i : word.indecies)
        {
            Word w = new Word(game.getBoard(), !word.poziomo, i, word.index);
            if(w.getWord().length() > 1)my_words.add(w);
        }
        for(int i : blank_indecies)
        {
            blank_words.add(new Word(game.getBoard(), !word.poziomo, i, word.index));
            blanks.add(game.getBoard().getTile(word.index, i, word.poziomo));
        }
        if(blanks.size() < 2) {
            for(Word w : my_words)
            {
                if(blanks.size() == 2) break;
                for(int i : w.getBlanks())
                if(i != word.index)
                {
                    blanks.add(game.getBoard().getTile(w.index, i, w.poziomo));
                    if(blanks.size() < 2)
                    {
                        Word w1 = new Word(game.getBoard(),word.poziomo, i, w.index);
                        blank_words.add(w1);
                        for(int j : w.getBlanks())
                        if(j != w.index)
                        {
                            blank_words.add(new Word(game.getBoard(), w.poziomo, j, i));
                            blanks.add(game.getBoard().getTile(i, j, word.poziomo));
                        }
                    }
                }
            }
            if(blanks.size() < 2)
            for(Word w : blank_words)
            {
                for(int i : w.getBlanks())
                if(i != word.index)
                {
                    blank_words.add(new Word(game.getBoard(),word.poziomo, i, w.index));
                    break;
                }
            }
        }
        blank_words.add(word);
        for(Word w : my_words)
        {
            blank_words.add(w);
        }
        String main_word = word.getWord();
        return 
           (main_word.length() > 1 &&
            (main_word.length() > word.indecies.size() ||
             !my_words.isEmpty() ||
             (game.getBoard().getTile(7,7).getLetter() != null &&
             !game.getBoard().getTile(7,7).fixed)) && 
            fillBlanks(blanks, 0, blank_words));
        
    }
    private int points()
    {
        LinkedList<Word> my_words = new LinkedList<>();
        if(check(my_words))
        {
            int sum = word.getPoints();
            for(Word w : my_words)
                sum+=w.getPoints();
            return sum;
        }
        else return 0;
    }
    public void move(Player player)
    {
        if(word.indecies.isEmpty()){
            retract(player);
            return;
        }
        int points = points();
        if(points>0)
        {
            for(int i : word.indecies)
            {
                Tile tile = game.getBoard().getTile(word.index, i, word.poziomo);
                tile.fixed = true;
            }
            player.score += points;
            if(word.indecies.size() == 7) 
                player.score += 50;
        }
        else 
        { 
            retract(player);
        }
    }
}
