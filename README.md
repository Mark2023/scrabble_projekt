
# Gra Scrabble

Implementuję popularną grę 2-4 osobową "scrabble" jako projekt końcowy na pracownie z programowania obiektowego \
Grę Możma uruchomić w trybach online i offline

## Spis treści

- [Instrukcja obsługi](#instrukcja-obsługi)
    - [Kompilacja](#kompilacja)
    - [Uruchomienie](#uruchomienie)
    - [Rozgrywka](#rozgrywka)
- [Użyte biblioteki](#użyte-biblioteki)
- [Wybrane klasy](#wybrane-klasy)
- [Spis klas](#spis-klas)

# Instrukcja obsługi

## Kompilacja

Żeby skompilować należy uruchomić skrypt ./compile.sh \
Może to potrwać kilka sekund ponieważ skrypt w pythonie przetwarza słownik w formie tekstowej \
odkompresowane dane zajmują ~40MB

## Uruchamianie

Żeby uruchomić należy wpisać \
```bash
java -jar scrabble.jar [...]
```
z argumentami \
lub uruchomić jeden ze skryptów

### Offline

#### Terminał
Dodając od 2 do 4 argumentów startuje się grę offline z odpowiednio nazwanymi graczami 
skrypt
```bash
example.sh | example.bat
```
podaje nazwy "Gracz1" "Gracz2" "Gracz3" "Gracz4"
#### GUI
Wywołanie polecenie bez argumentów tak jak w skrypcie
```bash
offline.sh | offline.bat
```
otworzy okno z możliwością podania 4 nazw graczy.
Zostawienie od 0 do 2 pól pustych i potwierdzenie otworzy grę offline.



#### Online
Należy dodać 1 argument.
Skrypt
```bash
online.sh | online.bat
```
uruchamia "java -jar scrabble.jar online"

Otworzy się okno z polami "Nazwa" i "Serwer"
zostawienie pola "serwer" pustego wypisze możliwe adresy i ropocznie nasłuchwanie, \
a podanie adresu spróbuje połaczyć się z grą.
## Rozgrywka

1. Po lewej stronie znajdują się:
    - "Teraz:" z nazwą gracza który w tym momencie wykonuje ruch lub "Koniec" w przypadku skończenia się gry
    - "Worek:" z ilością pozostałych liter w worku
    - Nazwy graczy i ich punkty  

2. W trybie online tytuł okienka jest nazwą odpowiedniego gracza

3. W lewym dolnym rogu znajduje się stojak gracza

4. W prawym dolnym rogu znajduje się wymiana liter(pomniejszony stojak) i przycisk końca tury

5. Aby podnieść / upuścić literę, należy kliknąć na pola na którym się znajduje / ma się znajdować.\
Ograniczenia w podnoszeniu:
    - białe litery są już ułożone, można tylko wymieniać "blanki"(z etykietą ".") zgodnie z zasadami (choć nie można wymienić z powrotem)
    - można wymieniać szare i kolorowe litery
    - można układać słowa w tylko jednej linii(rzędzie lub kolumnie)
    

# Użyte biblioteki
```java
java.util;
java.io;
java.net;
javax.swing;
java.awt;
java.awt.event;
```
# Wybrane klasy
wszystkie klasy (oprócz App)
są opakowane w paczkę scrabble, więc pominę prefix "scrabble."
## klasa PlayerHandler 

To klasa abstrakcyjna, która obsługuje interakcje pomiędzy graczem i grą, turą i graczami w różnych trybach. 

Dziedziczą po niej klasy:

- OfflinePlayer
- LocalPlayer
- RemotePlayer

## interfejs PlayerFactory

To interfejs, który ma implementować klasa tworząca obiekty dziedziczące po PlayerHandler.

Implementują go:

- OfflineFactory
- OnlineFactory

## klasa Game

Żeby otworzyć nową grę należy stworzyć obiekt tej klasy za pomocą 
```java
Game game = new Game(PlayerFactory)
//np. new Game(new OnlineFactory())
```
i wystartować
```java
game.start_game()
```
przechowuje wszystkie dana o grze (nic bezpośrednio)
## klasa NewGame
abstrakcyjna klasa do implementowania okienek do zbierania danych potrzebnych do rozpoczęcia gry

Dziedziczą po niej klasy:

- NewOfflieGame
- ChooseGame
- NewOnlineGame

## klasy pure.Tile i swing.SwingTile

klasa Tile przetrzymuje literę(klasy Letter) i inne dane pola,

a klasa SwingTile implementuje warstwę graficzną pola dziedzicząc JButton.
## klasa swing.Cursor

klasa implementuje interakcje kursora z planszą i literami

# Spis klas

- App
- scrabble.PlayerHandler
- scrabble.PlayerFactory
- scrabble.Game
- scrabble.NewGame
- scrabble.pure.Bag
- scrabble.pure.Board
- scrabble.pure.Dictionary
- scrabble.pure.Hand
- scrabble.pure.Letter
- scrabble.pure.Player
- scrabble.pure.Scores
- scrabble.pure.Tile
- scrabble.pure.actions.Move
- scrabble.pure.actions.Swap
- scrabble.pure.actions.Turn
- scrabble.pure.actions.Word
- scrabble.swing.Cursor
- scrabble.swing.EndOfTurn
- scrabble.swing.SwingBoard
- scrabble.swing.SwingDiscard
- scrabble.swing.SwingGame
- scrabble.swing.SwingHand
- scrabble.swing.SwingScores
- scrabble.swing.ScoreSlate
- scrabble.swing.SwingTile
- scrabble.general.ListAndString
- scrabble.general.Ls
- scrabble.general.SortedList
- scrabble.offline.NewOfflineGame
- scrabble.offline.OfflineFactory
- scrabble.offline.OfflinePlayer
- scrabble.online.ChooseGame
- scrabble.online.NewOnlineGame
- scrabble.online.LocalPlayer
- scrabble.online.RemotePlayer
- scrabble.online.OnlineFactory