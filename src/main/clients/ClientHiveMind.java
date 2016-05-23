package clients;

import java.io.IOException;
import java.util.*;

import message.*;
import gameboard.*;
import network.Connection;
import gui.DrawBoard;

public class ClientHiveMind {

    public static final String DEFAULT_HOST = "localhost";
    /* Minimum time, in milliseconds, that each turn should take. */
    public static final int DEFAULT_DELAY = 250;

    private int delay;
    private SortedMap<Integer, Connection> connections;
    private PlayerTurn turns;
    private Board board;
    private DrawBoard gui;

    public static void main(String[] args) {
        try {
            int delay = DEFAULT_DELAY;
            if (args[0].equals("--delay")) {
                delay = Integer.parseInt(args[1]);
                // skip over the first two args (that we just parsed)
                args = Arrays.copyOfRange(args, 2, args.length);
            }

            ClientHiveMind CHM = new ClientHiveMind(getConnections(args), delay);
            CHM.run();
        } catch (NumberFormatException e) {
            error("Invalid port number or delay.");
        } catch (IOException e) {
            error("Unable to connect to one or more servers.");
        } catch (InvalidNumberOfPlayersException e) {
            error(e.getMessage());
        }
    }

    public ClientHiveMind(SortedMap<Integer, Connection> connections, int delay) {
        this.connections = connections;
        this.delay = delay;
        this.board = new Board();
        // this.gui is initialized in gameInit()
    }

    /**
     * Main run logic for the client
     */
    public void run() {
        gameInit();
        setupBoard();
        boolean winner = false;

        while (turns.playerCount() > 1 && !winner) {
            int pNumber = turns.next();
            try {
                Move move = getMove(pNumber);
                if (board.makeMove(pNumber, move)) {
                    // performed legal move
                    gui.makeMove(pNumber, move);
                    notifyMove(pNumber, move);
                    if (board.isWinner(pNumber)) {
                        victory(pNumber);
                        winner = true;
                    }
                } else {
                    // illegal move
                    boot(pNumber);
                }
            } catch (NullPointerException e) {
                boot(pNumber);
            }
        }
        if (!winner) {
            victory(turns.next());
        }
    }

    /**
     * Initializes the handshake with the servers
     */
    public void gameInit() {
        int size = connections.size();

        List<String> names = new ArrayList<>();

        for (Integer pNumber : connections.keySet()) {
            connections.get(pNumber).sendMessage(new HelloMessage());
            IAmMessage name = (IAmMessage) connections.get(pNumber).receiveMessage(Message.Type.I_AM);
            names.add(name.team + ":" + name.name);
        }

        for (Integer pNumber : connections.keySet()) {
            GameMessage gameMsg = new GameMessage(pNumber, names.toArray(new String[0]));
            connections.get(pNumber).sendMessage(gameMsg);
        }

        this.gui = new DrawBoard(names.toArray(new String[0]));
    }

    public void setupBoard() {
        if (connections.size() == 2) {
            turns = new PlayerTurn(1, 2);
            board.placePawn(1, new Coordinate(0, 4));
            board.placePawn(2, new Coordinate(8, 4));
            board.setWalls(10);
            gui.placePawn(1, new Coordinate(0, 4));
            gui.placePawn(2, new Coordinate(8, 4));
        } else {
            turns = new PlayerTurn(1, 4, 2, 3);
            board.placePawn(1, new Coordinate(0, 4));
            board.placePawn(4, new Coordinate(4, 8));
            board.placePawn(2, new Coordinate(8, 4));
            board.placePawn(3, new Coordinate(4, 0));
            board.setWalls(5);
            gui.placePawn(1, new Coordinate(0, 4));
            gui.placePawn(4, new Coordinate(4, 8));
            gui.placePawn(2, new Coordinate(8, 4));
            gui.placePawn(3, new Coordinate(4, 0));
        }
    }

    /**
     * @param pNumber - the player to retrieve a move from
     * @return - a move object
     */
    public Move getMove(int pNumber) {
        long start = System.currentTimeMillis();

        Connection conn = connections.get(pNumber);
        conn.sendMessage(new RequestMoveMessage());
        MoveMessage msg = (MoveMessage) conn.receiveMessage(Message.Type.MOVE);

        long end = System.currentTimeMillis();
        long wait = delay - (end - start);
        if (wait < 0)
            wait = 0;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {}

        return msg.move;
    }

    /**
     * @param pNumber - the player who currently moved
     * @param move - the move to be broadcast
     */
    public void notifyMove(int pNumber, Move move) {
        if (move instanceof PawnMove) {
            broadcastMessage(new NotifyPawnMoveMessage(pNumber, (PawnMove) move));
        } else {
            broadcastMessage(new NotifyWallMoveMessage(pNumber, (WallMove) move));
        }

    }

    /**
     * @param pNumber - the player who is being booted Post-Condition: alerts
     * all players of boot, then boots player from the game
     */
    public void boot(int pNumber) {
        broadcastMessage(new BootMessage(pNumber));
        board.removePawn(pNumber);
        turns.bootPlayer(pNumber);
        connections.remove(pNumber);
        gui.removePawn(pNumber);
    }

    /**
     * @param pNumber - the winning player Post Condition: broadcasts winner to
     * all remaining players
     */
    public void victory(int pNumber) {
        broadcastMessage(new VictoryMessage(pNumber));
        gui.winCondition(pNumber);
    }

    /**
     * @param msg - the message to be broadcast to all players
     */
    public void broadcastMessage(Message msg) {
        for (Connection conn : connections.values()) {
            conn.sendMessage(msg);
        }
    }

    /**
     *
     * @param args - string array of server hostname:port
     * @return connections - a map of connections to servers
     * @throws clients.ClientHiveMind.InvalidNumberOfPlayersException
     * @throws NumberFormatException
     * @throws IOException
     */
    private static SortedMap<Integer, Connection> getConnections(String[] args)
            throws InvalidNumberOfPlayersException, NumberFormatException, IOException {

        SortedMap<Integer, Connection> connections = new TreeMap<Integer, Connection>();
        if (args.length != 2 && args.length != 4) {
            throw new InvalidNumberOfPlayersException();
        }
        int i = 1;
        for (String arg : args) {
            // only split at the first colon
            String[] parts = arg.split(":", 2);
            // host (first part) is optional
            String host = (parts.length == 2) ? parts[0] : DEFAULT_HOST;
            // user might have omitted the host but left the colon by accident
            if (host == "")
                host = DEFAULT_HOST;
            String port = (parts.length == 2) ? parts[1] : parts[0];

            connections.put(i, new Connection(host, Integer.parseInt(port)));
            i++;
        }
        return connections;
    }

    /**
     * Prints the given message to stderr and then exits with an error code.
     */
    protected static void error(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
    }

    /**
     * Custom exception class for invalid number of players Extends: Exception
     */
    private static class InvalidNumberOfPlayersException extends Exception {

        public InvalidNumberOfPlayersException() {
            super("Invalid number of players, must be 2 or 4.");
        }
    }
}
