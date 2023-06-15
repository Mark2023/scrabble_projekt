/*
 * Marek Wiśniewski 338782
 * SwingGame - klasa GUI rozgrywki 
 * 31.05.2023
 */
package scrabble.swing;

import scrabble.*;
import scrabble.pure.Hand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingGame {
    Game game;
    private EndOfTurn endOfTurn;
    private SwingBoard swingBoard;
    private SwingHand swingHand;
    private SwingScores swingScores;
    JFrame frame;
    static int down_size = 3;
    static int side_size = 2;
    public SwingGame(Game game)
    {

        this.game = game;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension screen = frame.getSize();
        SwingTile.size = (int) (Math.min(screenSize.getWidth() / 2, screenSize.getHeight() / 2) / 15);
        screen = new Dimension((15 + side_size) * SwingTile.size, (15 + down_size) * SwingTile.size);
        frame.getContentPane().setPreferredSize(screen);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                for(PlayerHandler handler : game.players){
                    handler.close();
                }
            }
        });

        swingHand = new SwingHand(game);
        swingBoard = new SwingBoard(game);
        swingScores = new SwingScores(game);
        endOfTurn = new EndOfTurn(game.getTurn());

        
        JPanel panel0 = new JPanel(null);
        frame.add(panel0);
        panel0.add(endOfTurn);
        panel0.add(swingHand);
        panel0.add(swingBoard);
        panel0.add(swingBoard.getDiscard());
        panel0.add(swingScores);
        panel0.setSize(screen);
        
        panel0.addMouseMotionListener(game.getCursor().listener);


        
        panel0.add(game.getCursor().label);
        panel0.addMouseMotionListener(game.getCursor().listener);
        frame.pack();
    }
    public void start_game()
    {
        frame.setVisible(true);
    }
    public EndOfTurn getEndOfTurn() { return endOfTurn; }
    public SwingBoard getSwingBoard() { return swingBoard; }
    public SwingHand getSwingHand() { return swingHand; }
    public SwingScores getSwingScores() { return swingScores; }
    public void setTitle(String title) {
        frame.setTitle(title);
    }
    public void refresh(Hand hand)
    {
        swingBoard.refresh();
        swingHand.setHand(hand);
    }
}
