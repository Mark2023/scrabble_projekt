/*
 * Marek Wiśniewski 338782
 * Game - klasa rozgrywki
 * 31.05.2023
 */
package scrabble;
import java.util.Iterator;
import java.util.LinkedList;

import scrabble.general.ListAndString;
import scrabble.pure.*;
import scrabble.pure.actions.Turn;
import scrabble.swing.*;

public class Game
{
    public PlayerHandler[] players;
    private Board board;
    private Bag bag;
    private SwingGame swing;
    private Scores scores;
    private Cursor cursor;
    private Turn turn;
    private boolean ended;
    private Slownik slownik;
    public Game(PlayerFactory factory)
    {
        slownik = new Slownik();
        bag = new Bag(slownik);
        cursor = new Cursor(this);
        board = new Board();
        LinkedList<PlayerHandler> tmp_players = new LinkedList<>();
        turn = new Turn(this);

        if(factory.setPlayers(tmp_players,this))
        {
            players = tmp_players.toArray(new PlayerHandler[tmp_players.size()]);
            scores = new Scores(bag,players);

            swing = new SwingGame(this);
        }

    }

    public SwingGame getSwing() { return swing; }
    public Bag getBag() { return bag; }
    public Board getBoard() { return board; }
    public Scores getScores() { return scores; }
    public Cursor getCursor() { return cursor; }
    public Turn getTurn() { return turn; }
    public boolean ended() { return ended; }
    public Slownik getSlownik() { return slownik; }
    public void start_game()
    {
        if(players != null)
        {
            synchronized(turn)
            {
                ended = false;
                for(PlayerHandler player : players)
                {
                    player.start_game();
                }
                swing.start_game();
                scores.setScores();
            }
            while(!ended)
                turn.start_turn();
        }
    }
    public void end_game()
    {
        synchronized(turn)
        {
            for(PlayerHandler player : players)
                player.end_of_game();
            ended = true;
            scores.turn.setText("Koniec");
            scores.setScores();
        }
    }
    public void setGame(char[][] ch_b,String removed,String chars_bag,Player player)
    {
        synchronized(turn)
        {
            //turn.retract();
            if(ch_b != null)
            {
                Iterator<Tile> discarded = board.getDiscard().iterator();
                for(Tile tile : turn.on_board.hand)
                {
                    if(tile.isEmpty())
                    {
                        Tile tile1 = null;
                        while(discarded.hasNext() && (tile1 == null || tile1.isEmpty()))
                            tile1 = discarded.next();
                        if(tile1 != null && !tile1.isEmpty())
                        {
                            tile.setLetter(tile1.getLetter());
                            tile1.setLetter(null);
                        }
                    }

                }
                for(int i = 0; i < 15; i++)
                for(int j = 0; j < 15; j++)
                {
                    char ch = ch_b[i][j];
                    Tile tile = board.getTile(i,j);
                    if((tile.isEmpty() && ch != ' ') || 
                    (!tile.isEmpty() && !tile.getLetter().equals(ch)))
                    {
                        tile.setLetter(new Letter(ch,slownik.points_for_letter(ch)));
                        tile.fixed = true;
                        tile.active = false;
                        player.hand.remove(ch);
                    }
                }
            }
            if(removed != null)
                for(char ch : ListAndString.StoL(removed))
                    player.hand.remove(ch);
            if(chars_bag != null)
            {
                bag.take(player.hand,ListAndString.StoL(chars_bag));
            }
        }
    }
    public char[][] getLetters()
    {
        char[][] chars = new char[15][15];
        synchronized (turn)
        {
            for(int i = 0; i < 15; i++)
            for(int j = 0; j < 15; j++)
            {
                Tile tile = board.getTile(i,j);
                if(tile.isEmpty())
                    chars[i][j] = ' ';
                else
                    chars[i][j] = tile.getLetter().getFullLetter();

            }
        }
        return chars;
    }
}