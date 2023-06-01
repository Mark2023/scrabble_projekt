/*
 * Marek Wiśniewski 338782
 * SwingScores - klasa GUI tabeli wyników 
 * 31.05.2023
 */
package scrabble.swing;

import java.awt.*;

import javax.swing.*;

import scrabble.Game;
import scrabble.pure.Scores;

public class SwingScores extends JPanel{
    ScoreSlate[] scores;
    public SwingScores(Game game)
    {
        super();
        Scores scores = game.getScores();
        Font font = new Font("Ariel Unicode MS" , Font.PLAIN, SwingTile.size / 3);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBounds(0, 0,
                  SwingTile.size * 2,
                  SwingTile.size * 2 * (scores.size() + 1));
        this.scores = new ScoreSlate[scores.size() + 1];
        scores.labels = new JLabel[scores.size()];
        this.scores[0] = new ScoreSlate(game,"Teraz",font);
        this.scores[1] = new ScoreSlate(game,"Worek",font);
        for(int i = 1; i < scores.size(); i++)
        {
            this.scores[i + 1] = new ScoreSlate(game,scores.getName(i),font);
        }
        add(this.scores[0]);
        scores.turn = this.scores[0].score;
        for(int i = 1; i <= scores.size(); i++)
        {
            scores.labels[i - 1] = this.scores[i].score;
            add(this.scores[i]);
        }

        addMouseMotionListener(game.getCursor().listener);
        setVisible(true);
    }
}

class ScoreSlate extends JPanel {

    JLabel napis;
    JLabel score;
    ScoreSlate(Game game,String nazwa,Font font)
    {
        super(new GridLayout(2,1));
        setSize(SwingTile.size * 2,SwingTile.size * 2);
        napis = new JLabel(nazwa);
        score = new JLabel();
        add(napis);
        add(score);
        napis.setFont(font);
        score.setFont(font);
        napis.setHorizontalAlignment(SwingConstants.CENTER);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        napis.setVerticalAlignment(SwingConstants.CENTER);
        score.setVerticalAlignment(SwingConstants.CENTER);
        addMouseMotionListener(game.getCursor().listener);
    }
}