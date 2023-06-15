/*
 * Marek Wiśniewski 338782
 * ListAndString - klasa zmieniająca String w i z Listy i char[][] 
 * 31.05.2023
 */
package scrabble.general;

import java.util.*;

public class ListAndString {
    public static String LtoS(List<Character> charList)
    {
        StringBuilder sb = new StringBuilder();
        for (Character c : charList) {
            sb.append(c);
        }
        return sb.toString();
    }
    public static List<Character> StoL(String str)
    {
        List<Character> charList = new LinkedList<>();
        for(int i = 0; i < str.length(); i++)
        {
            charList.add(str.charAt(i));
        }
        return charList;
    }
    public static char[][] StoA(String str)
    {
        char[][] board = new char[15][15];
        for(int i = 0; i < str.length(); i++)
            board[i%15][i/15] = str.charAt(i);
        return board;
    }
    public static String AtoS(char[][] board)
    {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < 15; j++)
        for(int i = 0; i < 15; i++)
            sb.append(board[i][j]);
        return sb.toString();
    }
}
