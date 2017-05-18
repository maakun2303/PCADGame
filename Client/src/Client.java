import java.io.*;
import java.net.*;
public class Client {

    static final String hostName = "localhost";
    static final int portNumber = 41423;

    //ClientProfile sarebbe utile poterla vedere e impostare già qui sul client...ma non so come farlo.
    //Da vedere e nel caso implementare, per ora uso un'altra soluzione schifosa...

    ///////////
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }
    //////////

    public void startConnection(String hostName, int portNumber) {
        try (
                Socket gSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(gSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(gSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;

            if ((fromServer = in.readLine()) != null) System.out.println("Server: " + fromServer);
            if (username != null) {
                System.out.println("Client: " + username);
                out.println(username);
            }
            if ((fromServer = in.readLine()) != null) System.out.println("Server: " + fromServer);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {

        Client client = new Client();
        client.startConnection(hostName, portNumber);

    }
}

