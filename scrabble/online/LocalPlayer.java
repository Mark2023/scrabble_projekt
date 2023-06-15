/*
 * Marek Wiśniewski 338782
 * LocalPlayer - klasa gracza lokalnego online
 * 31.05.2023
 */
package scrabble.online;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

import scrabble.*;
import scrabble.general.ListAndString;
import scrabble.pure.*;

public class LocalPlayer extends PlayerHandler{
    public LinkedList<PrintWriter> out;
    public LocalPlayer(String name,Game game){
        super(game,new Player(name));
        out = new LinkedList<>();
    }
    
    public void addSocket(Socket socket) throws IOException
    {
        out.addLast(new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
    }
    public void send(String message) throws IOException 
    {
        for(PrintWriter out : out)
        {
            out.println(message);
            out.flush();
        }
    }
    public void send(String message,int pos) throws IOException 
    {
        out.get(pos).println(message);
        out.get(pos).flush();
    }
    public void start_of_turn() // begin //
    {
        getGame().getTurn().on_board = getPlayer();
    }
    public void end_of_turn() // end + send //
    {
        Hand discard = getGame().getBoard().getDiscard();
        boolean discard_empty = discard.isEmpty();
        String removed = ListAndString.LtoS(discard.getChars());
        if(!discard_empty)
            getGame().getBag().put(discard);
        
        getGame().getTurn().end_turn(discard_empty);
        String board = ListAndString.AtoS(getGame().getLetters());
        String draw = ListAndString.LtoS(getGame().getBag().draw(getPlayer().hand));
        try{
            send("turn");
            send(String.valueOf(getPlayer().score));
            send(board);
            send(removed);
            send(draw);
        }catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }
    }
    public void start_of_game()
    {
        String draw = ListAndString.LtoS(getGame().getBag().draw(getPlayer().hand));
        try{
            send(draw);
        }catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }
        getGame().getTurn().on_board = getPlayer();
        getGame().getSwing().setTitle(getPlayer().name);
    }
}
