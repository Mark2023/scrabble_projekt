/*
 * Marek Wiśniewski 338782
 * NewGame - abstrakcyjne klasa GUI do wczytywania danych gry
 * 31.05.2023
 */
package scrabble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

abstract public class NewGame {
    public JFrame frame;
    public ActionListener button_listener;
    public Boolean start;
    public abstract boolean is_starting();
    public float font_size;
    public boolean closed;
    public NewGame()
    {
        this.start = false;
        this.closed = false;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int size = (int) (Math.min(screenSize.getWidth() / 2, screenSize.getHeight() / 2));

        frame = new JFrame();
        
        frame.setSize(size, size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        font_size = size / 25f;
        button_listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(is_starting())
                    start = true;
                closed = true;
            }
        };
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closed = true;
            }
        });
    }
    public void changeFont ( Component component ) 
    {
        Font font = component.getFont();
        if(font!=null)
            component.setFont ( font.deriveFont(font_size) );
        if ( component instanceof Container )
        {
            for ( Component child : ( ( Container ) component ).getComponents () )
            {
                changeFont ( child );
            }
        }
    }
}
