package network;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import message.Message;
import message.MessageFormatException;

public class Connection {

    /**
     * Milliseconds to wait when reading from the socket.
     */
    public static int DEFAULT_TIMEOUT = 30000;

    private BufferedReader in;
    private PrintWriter out;

    /**
     * Creates a Connection that reads from/writes to the given port.
     *
     * This constructor will block until it receives a client connection.
     */
    public Connection(int port) throws IOException {
        this((new ServerSocket(port)).accept());
    }

    /**
     * Creates a Connection that reads from/writes to the given host + port.
     *
     * This constructor will block until it connects to the remote machine.
     */
    public Connection(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    /**
     * Creates a Connection that reads from/writes to the given socket.
     */
    public Connection(Socket socket) throws IOException {
        this(new InputStreamReader(socket.getInputStream()), new OutputStreamWriter(socket.getOutputStream()));
        socket.setSoTimeout(DEFAULT_TIMEOUT);
    }

    /**
     * Creates a Connection that reads from in and writes to out.
     */
    public Connection(Reader in, Writer out) {
        this.in = new BufferedReader(in);
        this.out = new PrintWriter(out, true);
    }

    /**
     * Closes all the connections when done with the server object
     */
    public void closeConnections() {
        try {
            out.close();
            in.close();
        } catch(IOException e) {
            // whatever - it's fine
        }
    }

    /**
     * Returns the next line from input (whatever that may be).
     */
    public String receive() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public Message receiveMessage() {
        String line = receive();
        if (line == null)
            return null;

        try {
            return Message.fromString(line);
        } catch (MessageFormatException e) {
            return null;
        }
    }

    public Message receiveMessage(Message.Type type) {
        Message msg = receiveMessage();
        if (msg == null || msg.type() != type)
            return null;
        return msg;
    }

    /**
     * Writes message to output (whatever that may be).
     *
     * This method handles newlines automatically.
     */
    public void send(String message) {
        out.println(message);
    }

    public void sendMessage(Message message) {
        send(message.toString());
    }
}
