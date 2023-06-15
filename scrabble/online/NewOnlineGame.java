/*
 * Marek Wiśniewski 338782
 * NewOnlineGame - klasa GUI wyświetlająca nazwy graczy i 
 *      czekająca na potwierdzenie rozpoczęcia(zatrzymuje serwer)
 * 31.05.2023
 */
package scrabble.online;

import javax.swing.*;

import scrabble.NewGame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class NewOnlineGame extends NewGame {
    LinkedList<JLabel> labels;
    JPanel panel;
    public NewOnlineGame(String name,ServerSocket server)
    {
        super();
        labels = new LinkedList<>();
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(4,1));
            frame.add(panel , BorderLayout.CENTER);
        if(server!=null)
        {
            JButton start_button = new JButton("Start");
            frame.add(start_button, BorderLayout.SOUTH);
            start_button.addActionListener(button_listener);
            frame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e) {
                    if(!server.isClosed())
                    try{
                        server.close();
                    }catch(IOException err){
                        System.out.println(err);
                        System.exit(1);
                    }
                }
            });
        }
        add(name);
        changeFont(frame);
        frame.setVisible(true);
    }
    public boolean is_starting() {
        return (2 <= labels.size() && labels.size() <= 4);
    }
    public void add(String str)
    {
        labels.addLast(new JLabel(str));
        panel.add(labels.getLast());
        changeFont(labels.getLast());
    }
    public static void main(String[] args)
    {
        new NewOnlineGame("gracz1",null);
    }
}
