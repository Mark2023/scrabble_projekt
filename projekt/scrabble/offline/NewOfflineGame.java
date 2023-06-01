/*
 * Marek Wiśniewski 338782
 * NewOfflineGame - klasa GUI wczytująca nazwy graczy offline
 * 31.05.2023
 */
package scrabble.offline;

import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import scrabble.NewGame;
import scrabble.PlayerFactory;

public class NewOfflineGame extends NewGame{
    JTextField[] nameFields;
    String[] names;
    public NewOfflineGame()
    {
        super();
        frame.setLayout(new GridLayout(5,1));
        nameFields = new JTextField[4];
        for(int i=0;i<4;i++)
        {
            frame.add(nameFields[i] = new JTextField());
        }
        JButton button = new JButton("Start");
        button.addActionListener(button_listener);
        frame.add(button);
        changeFont(frame);
        frame.setVisible(true);
    }
    public boolean is_starting()
    {
        List<String> tmp_names = new LinkedList<String>();
        for(JTextField label : nameFields)
        {
            if(!label.getText().isEmpty())
                tmp_names.add(label.getText());
        }
        names = tmp_names.toArray(new String[tmp_names.size()]);
        return (2 <= names.length && names.length <= 4);
    }
    public static PlayerFactory newGame()
    {
        NewOfflineGame newGame = new NewOfflineGame();
        try{
            while(!newGame.closed)Thread.sleep(100);
        }catch(InterruptedException e){
            System.out.println(e);
            System.exit(1);
        }
        if(newGame.start)
        {
            return new OfflineFactory(newGame.names);
        } else return null;

    }
}
