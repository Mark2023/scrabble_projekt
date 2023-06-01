/*
 * Marek Wiśniewski 338782
 * Scores - klasa tabeli wyników
 * 31.05.2023
 */
package scrabble.pure;

import javax.swing.JLabel;

import scrabble.PlayerHandler;

public class Scores {
    Bag bag;
    Player[] players;
    public JLabel[] labels;
    public JLabel turn;
    public void setScores() {
        if(labels[0] != null)
            labels[0].setText(((Integer)bag.size()).toString());
        for(int i=0;i<players.length;i++)
            if(labels[i+1] != null)
                labels[i+1].setText(((Integer)players[i].score).toString());
    }
    public void setTurn(Player player)
    {
        turn.setText(player.name);
    }
    public Scores(Bag bag, PlayerHandler[] players) {
        this.bag = bag;
        this.players = new Player[players.length];
        for(int i=0;i<players.length;i++)
            this.players[i] = players[i].getPlayer();
    }
    public int size() {
        return players.length + 1;
    }
    public String getName(int i) {
        return players[i - 1].name;
    }
}
