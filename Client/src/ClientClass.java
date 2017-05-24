import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;


public class ClientClass {

    static final String remoteHost = "localhost";
    static final int portWasBinded = 4455;


    public serverInterface getServerInterface(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        return remoteObject;
    }

}

