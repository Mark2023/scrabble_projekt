/*
 * Marek Wiśniewski 338782
 * Player - klasa gracza
 * 31.05.2023
 */
package scrabble.pure;

public class Player {
    public String name;
    public int score;
    public Hand hand;
    public Player(String name)
    {
        this.name = name;
        score = 0;
        hand = new Hand();
    }
    public String toString()
    {
        return "Name: " + name +
               "\nscore: " + score +
               "\nhand: " + hand.toString();
    }
}
