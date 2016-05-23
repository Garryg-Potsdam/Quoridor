package servers;

import java.io.IOException;

import AI.*;
import message.*;
import gameboard.*;
import network.Connection;

/**
 * This class is the central control unit of our quoridor game.
 *
 * Contains: Server for Client connection. Game board for mapping actual game
 * board. AI instance for choosing moves to make.
 */
public class ServerHiveMind {

    public static final String TEAM_ID = "hvm";

    private Connection conn;
    private String name;
    private Board gb;
    private AIveMind ai;
    private int pNumber;
    private int path;
    private int chance;

    public static void main(String[] args) {
        try {
            int port = -1;
            int path = 7;
            int chance = 100;
            // port can be specified as an option or a positional argument
            if (args.length % 2 == 1)
                error("missing invalid argument.");
            else {
                for (int i = 0; i < args.length; i += 2) {
                    if (args[i].equals("--port")) {
                        port = Integer.parseInt(args[i + 1]);                        
                    } else if (args[i].equals("--path")) {
                        path = Integer.parseInt(args[i + 1]);
                        if (path < 0 || path > 10)
                            error("Path must range from 0 to 10.");
                    } else if (args[i].equals("--chance")) {
                        chance = Integer.parseInt(args[i + 1]);
                        if (chance < 0 || chance > 100)
                            error("Chance must range from 0 to 100.");
                    }
                }
            }

            ServerHiveMind mind = new ServerHiveMind(new Connection(port), path, chance);
            mind.run();
        } catch (NumberFormatException e) {
            error("invalid port number");
        } catch (IOException e) {
            error("could not connect to client");
        }
    }

    /**
     * Creates a new ServerHiveMind that reads and writes from the given server.
     */
    public ServerHiveMind(Connection conn, int path, int chance) {
        this.conn = conn;
        this.name = "hivemind" + (int) (Math.random() * 9999);
        this.path = path;
        this.chance = chance;
    }

    public void run() {
        gameInit();

        boolean done = false;
        while (!done) {
            Message msg = conn.receiveMessage();
            if (msg == null) {
                System.err.println("Recieved invalid message");
                System.exit(1);
            }

            switch (msg.type()) {

                case REQUEST_MOVE:
                    conn.sendMessage(getMove());
                    break;

                case NOTIFY_MOVE:
                    updateBoard((NotifyMoveMessage) msg);
                    break;

                case BOOT:
                    kill((BootMessage) msg);
                    break;

                case VICTORY:
                    winner((VictoryMessage) msg);
                    break;

                default:
                    System.err.println("Received invalid message: " + msg.toString());
                    System.exit(1);
            }
        }
    }

    /**
     * Retrieves a HELLO message and responds with an IAM message.
     */
    public void gameInit() {
        conn.receiveMessage(Message.Type.HELLO);
        conn.sendMessage(new IAmMessage(TEAM_ID, name));
        GameMessage gameMsg = (GameMessage) conn.receiveMessage(Message.Type.GAME);
        if (gameMsg == null) {
            System.out.println("Received invalid message");
            System.exit(1);
        }
        pNumber = gameMsg.playerNumber;
        setUpBoard(gameMsg.playerCount());
        ai = new AIveMind(pNumber, gb, gameMsg.names);
    }

    public void setUpBoard(int playerCount) {
        gb = new Board();
        gb.setPathChance(path, chance);
        if (playerCount == 2) {
            gb.placePawn(1, new Coordinate(0, 4));
            gb.placePawn(2, new Coordinate(8, 4));
            gb.setWalls(10);
        } else {
            gb.placePawn(1, new Coordinate(0, 4));
            gb.placePawn(4, new Coordinate(4, 8));
            gb.placePawn(2, new Coordinate(8, 4));
            gb.placePawn(3, new Coordinate(4, 0));
            gb.setWalls(5);
        }
    }

    public MoveMessage getMove() {
        Move move = ai.getMove();
        if (move instanceof PawnMove)
            return new PawnMoveMessage((PawnMove)move);
        else
            return new WallMoveMessage((WallMove)move);
    }

    /**
     * @param msg - the current move made *
     */
    public void updateBoard(NotifyMoveMessage msg) {
        if (msg instanceof NotifyPawnMoveMessage) {
            PawnMove move = (PawnMove) msg.move;
            gb.movePawn(msg.playerNumber, move.destination);
        } else {
            WallMove wall = (WallMove) msg.move;
            gb.placeWall(msg.playerNumber, wall.wall);
        }
    }

    /**
     * @param msg - the message of the booted player
     */
    public void kill(BootMessage msg) {
        if (msg.playerNumber == pNumber) {
            System.out.println("I got booted");
            System.exit(1);
        } else {
            System.out.format("Player %d got booted\n", msg.playerNumber);
            gb.removePawn(msg.playerNumber);
        }
    }

    /**
     * @param msg - the message of the winner
     */
    public void winner(VictoryMessage msg) {
        if (msg.playerNumber == pNumber) {
            System.out.println("I won!");
        } else {
            System.out.println("I lost :(");
        }
        System.exit(0);
    }

    /**
     * Prints the given message to stderr and then exits with an error code.
     */
    protected static void error(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
    }
}
