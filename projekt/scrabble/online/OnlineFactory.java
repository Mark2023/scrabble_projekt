/*
 * Marek Wiśniewski 338782
 * OnlineFactory - klasa łącząca graczy online
 * 31.05.2023
 */
package scrabble.online;

import java.io.IOException;
import java.net.*;
import java.util.*;

import scrabble.*;

public class OnlineFactory implements PlayerFactory {
    public static final int portNumber0 = 57592;
    public OnlineFactory () { // server ? network
        //Socket socket = new Socket();
        
    }
    boolean start_game(String name,List<PlayerHandler> players,Game game)
    {
        boolean result = true;
        ServerSocket serverSocket;
        NewOnlineGame connected = null;
        LocalPlayer me = null;
        try{
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets))
            {
                boolean siteLocalAddress = false;
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                List<InetAddress> listAddresses = Collections.list(inetAddresses);
                for (InetAddress inetAddress : listAddresses)
                    if(inetAddress.isSiteLocalAddress())
                    {
                        siteLocalAddress = true;
                        System.out.printf("Display name: %s\nName: %s\nAddress: %s\n\n", netint.getDisplayName(), 
                                                                                         netint.getName(), 
                                                                                         inetAddress);
                    }
                    if(!siteLocalAddress)
                    for (InetAddress inetAddress : listAddresses)
                    {
                        System.out.printf("Display name: %s\nName: %s\nAddress: %s\n\n", netint.getDisplayName(), 
                                                                                         netint.getName(), 
                                                                                         inetAddress);
                    }
            }

            serverSocket = new ServerSocket(portNumber0);
            connected = new NewOnlineGame(name,serverSocket);
            me = new LocalPlayer(name,game);
            players.add(me);
            for(int i=0;i<3;i++)
            {
                Socket newSocket = serverSocket.accept();
                RemotePlayer pl = new RemotePlayer(newSocket, game);
                players.add(pl);
                connected.add(pl.getPlayer().name);
                me.send(newSocket.getInetAddress().getHostAddress());
                me.send(pl.receive());
                me.addSocket(newSocket);
                me.send(me.getPlayer().name,i);
                me.send(String.valueOf(i + 1),i);
            }
            me.send("");

            serverSocket.close();
            connected.frame.dispose();
        }
        catch(SocketException e)
        {
            if(connected == null || !connected.start || me == null) return false;
            try{
                me.send("");
            }
            catch(IOException err){
                System.out.println(err);
                result = false;
            }

        }
        catch(IOException e)
        {
            System.out.println(e);
            result =  false;
        }
        return result;
    }

    boolean join_game(String name,String serverName,List<PlayerHandler> players,Game game)
    {
        boolean result = true;
        try{
            Socket socket = new Socket(serverName,portNumber0);
            int portNumber = newPort();
            LocalPlayer me = new LocalPlayer(name,game);
            me.addSocket(socket);
            me.send(name,0); // nowy przedstawia się
            me.send(String.valueOf(portNumber),0);

            RemotePlayer gm = new RemotePlayer(socket,game);
            players.add(0,gm);
            int pos = Integer.parseInt(gm.receive());

            
            ServerSocket server = new ServerSocket(portNumber);
            for(int i = 1; i < pos; i++)
                players.add(i,null);
            players.add(pos,me);

            NewOnlineGame connected = new NewOnlineGame(name,null);
            connected.add(gm.getPlayer().name);

            for(int i=1;i<pos;i++)
            {
                Socket newSocket = server.accept();
                me.addSocket(newSocket);
                me.send(name,i);
                RemotePlayer pl = new RemotePlayer(newSocket,game);
                int pos1 = Integer.parseInt(pl.receive());
                players.set(pos1,pl);
                connected.add(pl.getPlayer().name);
            }
            String address;
            int pos0 = pos;
            while(!(address = gm.receive()).isEmpty())
            {
                pos++;
                Socket newSocket = new Socket(address,Integer.valueOf(gm.receive()));
                RemotePlayer pl = new RemotePlayer(newSocket,game);
                me.addSocket(newSocket);
                me.send(name,pos - 1);
                me.send(String.valueOf(pos0),pos - 1);
                players.add(pos,pl);
                connected.add(pl.getPlayer().name);
            }
            connected.frame.dispose();
            server.close();
        }
        catch(IOException e){
            System.out.println(e);
            result = false;
        }
        return result;
    }
    static int newPort()
    {
        Integer port = null;
        while(port == null)
        {
            port = (int) (Math.random() * 10000) + 40000;
            try{
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.close();
            }catch(IOException e){
                port=null;
            }
        }
        return port;
    }
    public boolean setPlayers(List<PlayerHandler> players, Game game)
    {
        ChooseGame choose = new ChooseGame();
        try{
            while(!choose.closed)Thread.sleep(100);
        }catch(InterruptedException e){
            System.out.println(e);
            return false;
        }
        if(choose.start && choose.local)
        {
            return start_game(choose.name,players, game);
        }
        else if(choose.start)
        {
            return join_game(choose.name,choose.server,players, game);
        }
        else return false;
    }
    public static void main(String[] args) 
    {
        Game game = new Game(new OnlineFactory());
        game.start_game();
    }
}
