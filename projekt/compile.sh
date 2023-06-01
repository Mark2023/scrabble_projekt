
javac -encoding utf-8 -d ./bin App.java
cd ./bin
jar cfe scrabble.jar App *
mv scrabble.jar ../
cd ..
rm -r ./bin
python split_slowa.py

