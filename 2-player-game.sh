#!/bin/sh

./server.sh 9998 | sed "s/^/PLAYER 1: /" &
./server.sh 9999 | sed "s/^/PLAYER 2: /" &

# give the servers time to start up
sleep 1

./client.sh 9998 9999 | sed "s/^/CLIENT: /"
