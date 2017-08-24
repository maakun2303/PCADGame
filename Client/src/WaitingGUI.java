import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
        try {
            checkOnlineUsers();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        SimpleTimer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        checkOnlineUsers();
                    } catch (NotBoundException e1) {
                        e1.printStackTrace();
                    }
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
                serverInterface remoteService = null;
                try {
                    try {
                        remoteService = (serverInterface) Naming.lookup("//"+Constants.remoteHost+":"+Constants.portWasBinded+"/RmiService");
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    remoteService.removePlayer(player);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void checkOnlineUsers() throws RemoteException, NotBoundException {
        ClientClass client = new ClientClass();
        serverInterface remoteService = null;
        try {
            remoteService = (serverInterface) Naming.lookup("//"+Constants.remoteHost+":"+Constants.portWasBinded+"/RmiService");

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        serverInterface finalRemoteService = remoteService;
        Runnable target = new Runnable() {
            @Override
            public void run() {
                try {
                    if(finalRemoteService.showConnectedPlayers() < finalRemoteService.getMaxPlayers()){
                        int result = finalRemoteService.getMaxPlayers() - finalRemoteService.showConnectedPlayers();
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
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(target);
    }
}