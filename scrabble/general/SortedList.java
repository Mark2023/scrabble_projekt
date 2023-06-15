/*
 * Marek Wiśniewski 338782
 * SortedList - rozszerza LinkedList o addSorted
 * 31.05.2023
 */
package scrabble.general;

import java.util.LinkedList;

public class SortedList<T extends Comparable<T>> extends LinkedList<T>
{
    public int addSorted(T value)
    {
        int i = 0;
        for(;i < size() && value.compareTo(get(i)) == 1;i++);
        add(i,value);
        return i;
    }
}
