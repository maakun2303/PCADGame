import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by msnsk on 2017/05/23.
 */
public class WaitingGUI extends JFrame{
    private JPanel panel1;
    private JLabel label1;
    private ClientProfile player;
    private Timer SimpleTimer;


    public WaitingGUI(ClientProfile player) throws RemoteException {
        this.player = player;

        setTitle("WaitingGUI");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
        checkOnlineUsers();

        SimpleTimer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkOnlineUsers();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        SimpleTimer.start();



        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ClientClass client = null;
                try {
                    client = new ClientClass();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                serverInterface remoteObject = null;
                try {
                    remoteObject = client.getServerInterface(client.remoteHost,client.portWasBinded );

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                    remoteObject.removePlayer(player);
                }
        });
    }

    public void checkOnlineUsers() throws RemoteException {
        ClientClass client = new ClientClass();
        serverInterface remoteObject = null;
        try {
            remoteObject = client.getServerInterface(client.remoteHost,client.portWasBinded );

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        serverInterface finalRemoteObject = remoteObject;
        Runnable target = new Runnable() {
            @Override
            public void run() {
                if(finalRemoteObject.showConnectedPlayers() < finalRemoteObject.getMaxPlayers()){
                    int result = finalRemoteObject.getMaxPlayers() - finalRemoteObject.showConnectedPlayers();
                    label1.setText("<html><center>Welcome " + player.getNickname() + "<br>Waiting for more " + result + " players</center></html>");
                }
                else {
                    SimpleTimer.stop();
                    setVisible(false);
                    Runnable init = new Runnable() {
                        public void run() {
                            try {
                                new PlayingGUI(player);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SwingUtilities.invokeLater(init);
                }

            }
        };
        SwingUtilities.invokeLater(target);
    }
}