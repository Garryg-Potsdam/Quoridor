------------
First steps:
------------

If you're reading this, you already have the code.
But you'll need to build it first:
$ ./gradlew build

If it finishes without errors, you're ready to play.

-----------------------------------
How to start a game - the easy way:
-----------------------------------

Just run:
$ ./2-player-game.sh
or
$ ./4-player-game.sh

These commands start 2 or 4 servers on localhost, plus a client.

-------------------------------------
How to start a game - the harder way:
-------------------------------------

If you want to get fancy, you can start individual servers like this:
$ ./server.sh [options] <port>

Server options:
  --port <number>
  --path <integer> [0,10] The metric for path length to start walling
  --chance <integer> [0, 100] The metric for chance to place walls

Then start the client like this:
$ ./client.sh [options] <host1>:<port1> <host2>:<port2>
or
$ ./client.sh [options] <host1>:<port1> <host2>:<port2> <host3>:<port3> <host4>:<port4>

Client options:
  --delay <time>
    How long each turn should take, in milliseconds. Default: 250

When starting the client, host names are optional and default to localhost.

--------------------------------------
How to start a game - the hardest way:
--------------------------------------

Start servers like this:
$ java -cp build/libs/quoridor.jar servers.ServerHiveMind [options] <port>

Server options:
  --port <number>
    You can specify the port like this, but it also works as a positional arg.

Start the client like this:
$ java -cp build/libs/quoridor.jar clients.ClientHiveMind \
    [options] <host1>:<port1> <host2>:<port2>
$ java -cp build/libs/quoridor.jar clients.ClientHiveMind \
    [options] <host1>:<port1> <host2>:<port2> <host3>:<port3> <host4>:<port4>

Client options:
  --delay <time>
    How long each turn should take, in milliseconds. Default: 250

When starting the client, host names are optional and default to localhost.

The backslash at the end of the client lines is the continuation character.
It means "ignore this newline." Unfortunately, these commands are so big and
ugly they don't fit nicely in the README. When typing them into the terminal,
you can omit the backslash and the newline if you like.

But really, why would you start a game like this?
