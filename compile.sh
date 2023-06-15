#!/bin/bash

# Marek Wisniewski 338782
# 01.06.2023

unzip -q ./sjp*.zip -d ./sjp
mkdir ./dotclasses
mv ./sjp/slowa.txt ./
javac -encoding utf-8 -d ./dotclasses ./App.java
cd ./dotclasses
jar cfe ./scrabble.jar App *
mv ./scrabble.jar ../
cd ..
rm -r ./dotclasses
python ./split_slowa.py
rm ./slowa.txt
rm -r ./sjp

