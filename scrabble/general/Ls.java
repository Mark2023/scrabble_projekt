/*
 * Marek Wiśniewski 338782
 * Ls - klasa do przechowywania danych litery
 * 01.06.2023
 */
package scrabble.general;

public  class Ls
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