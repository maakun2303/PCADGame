import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by msnsk on 2017/05/23.
 */
public class WaitingGUI extends UnicastRemoteObject implements RemoteObserver {
    private JFrame frame1;
    private JPanel panel1;
    private JLabel label1;
    private ClientProfile player;


    public WaitingGUI(ClientProfile player) throws RemoteException {
        this.player = player;
        frame1 = new JFrame();
        frame1.setTitle("WaitingGUI");
        frame1.setContentPane(panel1);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(400, 300);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        serverInterface remoteService = null;
        try {
            remoteService = (serverInterface) Naming.lookup("//" + Constants.remoteHost + ":" + Constants.portWasBinded + "/RmiService");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        remoteService.addObserver(this);
        try {
            checkOnlineUsers();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }


        frame1.addWindowListener(new java.awt.event.WindowAdapter() {
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
                        remoteService = (serverInterface) Naming.lookup("//" + Constants.remoteHost + ":" + Constants.portWasBinded + "/RmiService");
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
        serverInterface remoteService = null;
        try {
            remoteService = (serverInterface) Naming.lookup("//" + Constants.remoteHost + ":" + Constants.portWasBinded + "/RmiService");

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        serverInterface finalRemoteService = remoteService;
        Runnable target = new Runnable() {
            @Override
            public void run() {
                try {
                    if (finalRemoteService.showConnectedPlayers() < finalRemoteService.getMaxPlayers()) {
                        int result = finalRemoteService.getMaxPlayers() - finalRemoteService.showConnectedPlayers();
                        label1.setText("<html><center>Welcome " + player.getNickname() + "<br>Waiting for more " + result + " players</center></html>");
                    } else {
                        frame1.setVisible(false);
                        Runnable init = new Runnable() {
                            public void run() {
                                try {
                                    try {
                                        new PlayingGUI(player);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (NotBoundException e) {
                                        e.printStackTrace();
                                    }
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

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        if(!frame1.isVisible())
        {
            serverInterface remoteService = null;
            try {
                remoteService = (serverInterface) Naming.lookup("//" + Constants.remoteHost + ":" + Constants.portWasBinded + "/RmiService");
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            remoteService.deleteObserver(WaitingGUI.this);
        }
        else {
            try {
                checkOnlineUsers();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }
}