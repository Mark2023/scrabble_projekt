/*
 * Marek Wiśniewski 338782
 * Slownik - klasa słownika
 * 31.05.2023
 */
package scrabble.pure;

import java.io.*;

public class Slownik {
    public String path;
    public Slownik()
    {
        path = "./slowa";
    }
    public Slownik(String path)
    {
        this.path = path;
    }
    public Slownik(String path, Ls[] letters)
    {
        this.path = path;
        this.letters = letters;
    }
    public Boolean find(String word)
    {
        Boolean found = false;
        if(word.equals(""))
            return false;
        try{
            BufferedReader fis = new BufferedReader (new InputStreamReader(new FileInputStream(path + "/" + word.charAt(0) +".txt"), "UTF-8"));
            while(true)
            {
                String word1 = fis.readLine();
                if(word1 == null || (found = word1.equals(word)))
                    break;
            }
            fis.close();
        }
        catch(IOException e){
            return false;
        }
        return found;
    }
    public int points_for_letter(Character letter)
    {
        for(Ls l : letters)
        {
            if(l.letter.equals(letter))
                return l.points;
        }
        return 0;
    }
    public static void main(String[] args)
    {
        Slownik dict = new Slownik();
        String[] words = new String[]{"panda","razą","pędy","oż","dął",new String(new char[] {'d','ą','ł'})};
        for(String w : words)
        {
            System.out.printf("%s %b\n",w,dict.find(w));
        }
    }
    public Ls[] letters = {
        new Ls('a', 9, 1),
        new Ls('ą', 1, 5),
        new Ls('b', 2, 3),
        new Ls('c', 3, 2),
        new Ls('ć', 1, 6),
        new Ls('d', 3, 2),
        new Ls('e', 7, 1),
        new Ls('ę', 1, 5),
        new Ls('f', 1, 5),
        new Ls('g', 2, 3),
        new Ls('h', 2, 3),
        new Ls('i', 8, 1),
        new Ls('j', 2, 3),
        new Ls('k', 3, 2),
        new Ls('l', 3, 2),
        new Ls('ł', 2, 3),
        new Ls('m', 3, 2),
        new Ls('n', 5, 1),
        new Ls('ń', 1, 7),
        new Ls('o', 6, 1),
        new Ls('ó', 1, 5),
        new Ls('p', 3, 2),
        new Ls('r', 4, 1),
        new Ls('s', 4, 1),
        new Ls('ś', 1, 5),
        new Ls('t', 3, 2),
        new Ls('u', 2, 3),
        new Ls('w', 4, 1),
        new Ls('y', 4, 2),
        new Ls('z', 5, 1),
        new Ls('ź', 1, 7),
        new Ls('ż', 1, 5),
        new Ls('.', 2, 0),
    };
}

class Ls
{
    public Character letter;
    public int points;
    public int count;
    public Ls(Character letter, int count, int points)
    {
        this.letter = letter;
        this.points = points;
        this.count = count;
    }
}