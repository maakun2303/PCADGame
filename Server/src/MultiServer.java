import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class MultiServer {
    private static ArrayList<clientProfile> ClientsList;

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java MultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new MultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}