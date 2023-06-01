/*
 * Marek Wiśniewski 338782
 * ChooseGame - klasa GUI wczytująca nazwę gracza i serwer (opcjonalnie)
 * 31.05.2023
 */
package scrabble.online;

import javax.swing.*;

import scrabble.NewGame;

import java.awt.*;

public class ChooseGame extends NewGame {
    public boolean local;
    JTextField nameField;
    JTextField serverField;
    public String name;
    public String server;
    public ChooseGame() {
        super();
        frame.setLayout(new GridLayout(3,2));
        frame.add(new JLabel("Nazwa:"));
        frame.add(nameField = new JTextField());
        frame.add(new JLabel("Serwer:"));
        frame.add(serverField = new JTextField(""));
        JButton button = new JButton("Start");
        frame.add(button);
        button.addActionListener(button_listener);

        changeFont(frame);
        frame.setVisible(true);
    }
    public boolean is_starting()
    {
        server = serverField.getText();
        name = nameField.getText();
        if(!server.equals("") && !name.equals(""))
        {
            local = false;
            return true;
        }
        else if(!name.equals(""))
        {
            local = true;
            return true;
        }
        else return false;
    }
    public static void main(String[] args)
    {
        new ChooseGame();
    }
}
