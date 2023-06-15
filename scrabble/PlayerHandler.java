/*
 * Marek Wiśniewski 338782
 * PlayerHandler - abstrakcyjna klasa do obsługi gracza w różnych trybach gry
 * 31.05.2023
 */
package scrabble;

import scrabble.pure.*;
import scrabble.pure.actions.Move;
import scrabble.pure.actions.Turn;

abstract public class PlayerHandler {
    private Player player;
    private Game game;
    public PlayerHandler(Game game, Player player)
    {
        this.player = player;
        this.game = game;
    }
    public PlayerHandler(Game game)
    {
        this.game = game;
    }
    public void wait_for_end(){
        Turn turn = game.getTurn();
        synchronized (turn)
        {
            try{
                turn.wait();
            }catch (InterruptedException e){
                System.out.println(e);
                System.exit(1);
            }
        }
    }
    public void close() {}
    public abstract void start_of_turn();
    public abstract void end_of_turn();
    public abstract void start_of_game();
    public void setPlayer(Player player) { 
        if(this.player == null)
            this.player = player;
    }
    public Player getPlayer() { return player; }
    public Game getGame() { return game; }
    public void end_turn()
    {
        Turn turn = game.getTurn();
        synchronized (turn)
        {
            turn.notifyAll();
        }
    }

    public void start_turn()
    {
        Turn turn = game.getTurn();
        synchronized (turn)
        {
            game.getScores().setTurn(player);
            turn.move = new Move(getGame());
            start_of_turn();
            game.getScores().setScores();
            game.getSwing().refresh(game.getTurn().on_board.hand);
        }
        
        wait_for_end();
        if(!getGame().ended())
        synchronized(turn)
        {
            int score = player.score;
            end_of_turn();
            if(player.score == score)
                turn.noPointsTurns++;
            else turn.noPointsTurns = 0;

            game.getSwing().refresh(game.getTurn().on_board.hand);
            game.getScores().setScores();

            if((game.getBag().isEmpty() && player.hand.isEmpty()) || turn.noPointsTurns >= 2*game.players.length)
            {
                game.end_game();
            }
        }
    }
    public void start_game()
    {
        start_of_game();
        if(game.getTurn().on_board != null)
            game.getSwing().getSwingHand().setHand(game.getTurn().on_board.hand);

    }
    public int end_of_game()
    {
        player.score -= player.hand.getPoints();
        return player.score;
    }
}
