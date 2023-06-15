/*
 * Marek Wiśniewski 338782
 * App - klasa do uruchamiania aplikacji ze względu na liczbę argumentów String[] args
 * 31.05.2023
 * kompilacja:
* słownik powinien być w pliku slowa.txt w postaci słowo w linijce

./compile.sh


 *
 * uruchomienie:
java -jar scrabble.jar Gracz1 Gracz2 Gracz3 Gracz4
lub
./example.sh
lub
./example.bat

lub

java -jar scrabble.jar 
lub
./offline.sh
lub
./offline.bat

lub


java -jar scrabble.jar online
lub
./online.sh
lub
./online.bat
 */


import scrabble.Game;
import scrabble.PlayerFactory;
import scrabble.online.OnlineFactory;
import scrabble.offline.NewOfflineGame;
import scrabble.offline.OfflineFactory;


public class App {
    
    public static void main(String[] args) 
    {
        PlayerFactory factory;
        if(args.length >=  2)
        {
            factory = new OfflineFactory(args);
        }
        else if(args.length == 0)
        {
            factory = NewOfflineGame.newGame();
        }
        else factory = new OnlineFactory();
        Game game = new Game(factory);
        game.start_game();
    }
}
