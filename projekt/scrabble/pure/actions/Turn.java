/*
 * Marek Wiśniewski 338782
 * Turn - klasa tury
 * 31.05.2023
 */
package scrabble.pure.actions;

import scrabble.pure.Player;

import java.awt.event.*;
import java.util.Stack;


import scrabble.Game;
import scrabble.PlayerHandler;
import scrabble.pure.Letter;
import scrabble.pure.Tile;

public class Turn  implements ActionListener{
    public Move move;
    public Player on_board;
    public int turnNumber;
    public int noPointsTurns;

    private Game game;
    private Stack<Swap> swapStack = new Stack<>();
    public Turn(Game game)
    {
        turnNumber = 0;
        noPointsTurns = 0;
        this.game = game;
    }
    public boolean add(Tile tile,Letter letter)
    {
        synchronized(this)
        {
            if(move != null)
            {
                return move.add(letter,tile);
            }else return false;
        }
    }
    public Letter remove(Tile tile)
    {
        synchronized(this)
        {
            if(move != null)
            {
                return move.remove(tile);
            }else return null;
        }
    }
    public void retract()
    {
        synchronized(this)
        {
            while(!swapStack.isEmpty()) swapStack.pop().swap();
            if(move != null)
                move.retract(on_board);
        }
    }
    public void swap(Letter letter, Tile blank)
    {
        synchronized(this)
        {
            
            Swap swap = new Swap(game,letter,blank.getLetter());
            swap.swap();
            Move swapMove = new Move(game,blank);
            
            if(swapMove.check())
                swapStack.push(swap);
            else
                swap.swap();
        }
    }
    public void end_turn()
    {
        end_turn(true);
    }
    public void start_turn()
    {
        game.players[turnNumber % game.players.length].start_turn();
    }
    public void end_turn(boolean discard_empty)
    {
        
        synchronized(this)
        {
            Player player = game.players[turnNumber % game.players.length].getPlayer();
            if(player == on_board && discard_empty)
            {
                int blank_count = 0;
                for(int i : move.word.indecies)
                {
                    if(game.getBoard().
                            getTile(move.word.index, i, move.word.poziomo).
                            getLetter().getPoints() == 0)
                        blank_count++;
                }
                if(blank_count < swapStack.size())
                {
                    retract();
                }
                else
                {
                    swapStack.clear();
                    move.move(on_board);
                }
            }else 
                retract();
            turnNumber++;
        }
    }
    
    public void actionPerformed(ActionEvent event)
    {
        PlayerHandler player = game.players[turnNumber % game.players.length];
    
        if(on_board == player.getPlayer())
        {
            player.end_turn();
        }
    }
}
