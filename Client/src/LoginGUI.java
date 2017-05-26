import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginGUI extends JFrame {
    private JPanel panel1;
    private JButton loginButton;
    private JTextField textField1;
    public static String Input;

    private ClientProfile player;


    public LoginGUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientClass client = new ClientClass();
                Input = (textField1.getText());
                if (Input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a nickname");
                }
                else{
                        serverInterface remoteObject = null;
                        try {
                            remoteObject = client.getServerInterface(client.remoteHost,client.portWasBinded );

                        } catch (IOException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Server offline, try again later");
                        }
                        if(remoteObject.showConnectedPlayers() >= 4) JOptionPane.showMessageDialog(null, "Game is full, try again later");
                        else {
                            player = remoteObject.login(LoginGUI.Input);
                            if(player.getNickname().equals("tryAgain")) JOptionPane.showMessageDialog(null, "Nickname already picked! Choose a different one");
                            else {
                                    setVisible(false);
                                    WaitingGUI wait = new WaitingGUI(player);
                                    wait.startGUI();
                            }
                        }
                }
            }
        });
    }

    public void startGUI(){
        setTitle("LoginGUI");
        setContentPane(new LoginGUI().panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        LoginGUI gui = new LoginGUI();
        gui.startGUI();

    }
}
