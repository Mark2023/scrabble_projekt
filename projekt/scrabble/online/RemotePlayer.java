/*
 * Marek Wiśniewski 338782
 * RemotePlayer - klasa gracza zdalnego online
 * 31.05.2023
 */
package scrabble.online;

import java.io.*;
import java.net.*;

import scrabble.*;
import scrabble.general.ListAndString;
import scrabble.pure.*;
import scrabble.pure.actions.Turn;

public class RemotePlayer extends PlayerHandler {
    private BufferedReader in;
    Socket socket;
    public RemotePlayer(Socket socket,Game game) throws IOException {
        super(game);
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        String name = receive();
        setPlayer(new Player(name));
    }
    public void start_of_turn() {}
    public void wait_for_end()
    {
        try{
            if(receive().equals("end_game"))
            {
                getGame().end_game();
            }
            else
            {
                Turn turn = getGame().getTurn();
                synchronized (turn){
                    turn.notifyAll();
                }
            }
        }catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }
    }
    public void end_of_turn()  // change state //
    {
        if(!getGame().ended())
        try{
            getPlayer().score = Integer.valueOf(receive());
            char[][] board = ListAndString.StoA(receive());
            String removed = receive();
            String draw = receive();
            getGame().getTurn().end_turn();
            getGame().setGame(board, removed, draw,getPlayer());

        }catch(IOException e){
            System.out.println(e);
        }
        
    }
    public void start_of_game()
    {
        try{
            getGame().setGame(null, null, receive(), getPlayer());
        }catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }
    }
    public String receive() throws IOException
    {
        String str = in.readLine();
        return str;
    }
    public void close() 
    {
        try{
            socket.close();
        }catch(IOException e){
            System.out.println(e);
            System.exit(1);
        }
    }
}
