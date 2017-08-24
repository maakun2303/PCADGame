import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginGUI extends JFrame {
    private JPanel panel1;
    private JButton loginButton;
    private JTextField textField1;
    public static String Input;

    private ClientProfile player;


    public LoginGUI() {
        setTitle("LoginGUI");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientClass client = null;
                try {
                    client = new ClientClass();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                Input = (textField1.getText());
                if (Input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a nickname");
                }
                else{
                    serverInterface remoteService = null;
                        try {
                            try {
                                remoteService = (serverInterface) Naming.lookup("//localhost:4456/RmiService");
                            } catch (NotBoundException e1) {
                                e1.printStackTrace();
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Server offline, try again later");
                        }
                    try {
                        if(remoteService.showConnectedPlayers() >= remoteService.getMaxPlayers()) JOptionPane.showMessageDialog(null, "Game is full, try again later");
                        else {
                            player = remoteService.login(LoginGUI.Input);
                            if(player.getNickname().equals("")) JOptionPane.showMessageDialog(null, "Nickname already picked! Choose a different one") ;
                            else {
                                    setVisible(false);

                                    Runnable init = new Runnable() {
                                        public void run() {
                                            try {
                                                new WaitingGUI(player);
                                            } catch (RemoteException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    };
                                    SwingUtilities.invokeLater(init);
                            }
                        }
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
        Runnable init = new Runnable() {
            public void run() {
                new LoginGUI();
            }
        };
        SwingUtilities.invokeLater(init);
    }

}
