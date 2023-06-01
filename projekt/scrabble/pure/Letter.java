/*
 * Marek Wiśniewski 338782
 * Letter - klasa litery
 * 31.05.2023
 */
package scrabble.pure;

public class Letter {
    private Character letter;
    private int points;
    private Tile tile;
    public Letter(char letter, int points)
    {
        this.letter = letter;
        this.points = points;
        tile = null;
    }
    public int getPoints()
    {
        return points;
    }
    public Character getLetter()
    {
        return letter;
    }
    public Character getFullLetter()
    {
        if(points == 0)
            return '.';
        else
            return letter;
    }
    public Tile getTile()
    {
        return tile;
    }
    public void setTile(Tile tile)
    {
        this.tile = tile;
    }
    public void setLetter(char c)
    {
        if(points == 0)
            letter = c;
    }
    public String toString()
    {
        if(points == 0)
            return ".";
        else if(points < 10)
            return letter.toString().toUpperCase() + 
                   (char)(points + '\u2080');
        else 
            return letter.toString().toUpperCase() +
                    (char)(points/10 + '\u2080') +
                    (char)(points%10 + '\u2080');
    }
    public boolean equals(Object o)
    {
        if(o instanceof Letter)
        {
            Letter l = (Letter)o;
            if(points == 0) return l.points == 0;
            else return letter.equals(l.letter);
        }
        else if(o instanceof Character)
        {
            if(points == 0) return o.equals('.');
            else return letter.equals(o);
        }
        else if(o instanceof Boolean)
        {
            return (Boolean)o ^ (points == 0);
        }else return false;
    }
}
