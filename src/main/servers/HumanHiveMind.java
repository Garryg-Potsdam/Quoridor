package servers;

import java.io.IOException;
import java.util.Scanner;

import message.*;
import gameboard.*;
import network.Connection;

/**
 * This class is the central control unit of our
 * quoridor game.
 *
 * Contains: Human for Client connection.
 *           Game board for mapping actual game board.
 *           AI instance for choosing moves to make.
 */

public class HumanHiveMind {

    public static final String TEAM_ID = "hvm";

    private Connection conn;
    private String name;
    private Scanner stdin;
    private int ourPlayerNumber;
    private static int port;
    
    //public static final GameBoard gameBoard = new GameBoard();
    //public static final AI ai = new AI();   
    public static void main(String[] args) {
        try {            
            if (args[0].equals("--port"))
                port = Integer.parseInt(args[1]);
            else
                error("Invalid input parameter " + args[0] + " must specify --port.");
            HumanHiveMind mind = new HumanHiveMind(new Connection(port));
            mind.run();
        } catch (NumberFormatException e) {
            error("invalid port number");
        } catch (IOException e) {
            error("could not connect to client");
        }
    }

    /**
     * Creates a new HumanHiveMind that reads and writes from the given server.
     */
    public HumanHiveMind(Connection conn) {
        this.conn = conn;
        this.name = "hivemind" + (int)(Math.random() * 9999);;
        this.stdin = new Scanner(System.in);
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
                performOurTurn();
                break;

            case NOTIFY_MOVE:
                processMove((NotifyMoveMessage) msg);
                break;

            case BOOT:
                BootMessage bootMsg = (BootMessage)msg;
                if (bootMsg.playerNumber == ourPlayerNumber)
                    weGotBooted(bootMsg);
                else
                    theyGotBooted(bootMsg);
                break;

            case VICTORY:
                VictoryMessage victoryMsg = (VictoryMessage)msg;
                if (victoryMsg.playerNumber == ourPlayerNumber)
                    weWin(victoryMsg);
                else
                    theyWin(victoryMsg);
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
        GameMessage gameMsg = (GameMessage)conn.receiveMessage(Message.Type.GAME);
        if (gameMsg == null) {
            System.out.println("Received invalid message");
            System.exit(1);
        }
        ourPlayerNumber = gameMsg.playerNumber;
    }

    public void performOurTurn() {
        System.out.println("What is your move?");
        MoveMessage move = null;
        while (true) {
            try {
                move = (MoveMessage)Message.fromString("TESUJI " + stdin.nextLine());
                break;
            } catch (MessageFormatException e) {
                System.out.println("Invalid move message - try again");
            }
        }
        conn.sendMessage(move);
    }

    public void processMove(NotifyMoveMessage msg) {
        System.out.println(msg.toString());
        // TODO: update game board
    }

    public void weGotBooted(BootMessage msg) {
        System.out.println("We got booted!");
        System.exit(0);
    }

    public void theyGotBooted(BootMessage msg) {
        System.out.format("Player %d was booted.\n", msg.playerNumber);
        // TODO: remove their pawn from the game board
    }

    public void weWin(VictoryMessage msg) {
        System.out.println("We won!");
        System.exit(0);
    }

    public void theyWin(VictoryMessage msg) {
        System.out.println("We lost... >:(");
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
