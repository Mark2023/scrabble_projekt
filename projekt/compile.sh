
unzip -q sjp*.zip -d sjp
mv sjp/slowa.txt ./
javac -encoding utf-8 -d ./bin App.java
cd ./bin
jar cfe scrabble.jar App *
mv scrabble.jar ../
cd ..
rm -r ./bin
python split_slowa.py
rm slowa.txt
rm -r sjp

