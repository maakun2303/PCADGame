import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ClientClass{

    protected ClientClass() throws RemoteException {
        super();
    }

    public serverInterface getServerInterface(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        System.out.println(Thread.currentThread().getName());
        return remoteObject;
    }

/*    public static void main(String[] args){
             serverInterface remoteService = null;
            CallHandler callHandler = new CallHandler();
            Client client = new Client(remoteHost, portWasBinded, callHandler);
            remoteService = (serverInterface) client.getGlobal(serverInterface.class);
            ClientClass client2 = new ClientClass();
            remoteService.addObserver(client2);

    }*/
}

