#!/bin/python

# Marek Wisniewski 338782
# skrypt do rozdzielania slownika
# python moze miec problem z polkimi znakami
# 01.06.2023

import os
if not os.path.exists("./slowa"):
    os.makedirs("./slowa")
    file = open("./slowa.txt","r")
    for s in file.readlines():
        if not os.path.exists("./slowa/"+s[0] + ".txt"):
            out = open("./slowa/"+s[0]+".txt", "w")
        out.write(s)
    out.close()
    file.close()
