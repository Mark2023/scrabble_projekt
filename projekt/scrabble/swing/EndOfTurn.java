/*
 * Marek Wiśniewski 338782
 * EndOfTurn - klasa GUI przycisku do zakończenia tury 
 * 31.05.2023
 */
package scrabble.swing;

import javax.swing.*;
import scrabble.pure.actions.Turn;

public class EndOfTurn extends JButton{
    Turn turn;
    public EndOfTurn(Turn turn)
    {
        super("koniec tury");
        setVisible(true);
        this.turn = turn;
        addActionListener(turn);
        setBounds(SwingTile.size * (15 + SwingGame.side_size - 6),
                  SwingTile.size * 17,
                  SwingTile.size * 6,
                  SwingTile.size * SwingGame.down_size / 3);
    }
}
