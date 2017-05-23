import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private static JFrame frame = new JFrame("LoginGUI");
    private JPanel panel1;
    private JButton loginButton;
    private JTextField textField1;


    public LoginGUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.setUsername(textField1.getText());
                client.startConnection(Client.hostName, Client.portNumber);
                //crasha se server down, bisognerà aggiungere qualche controllo...

                frame.setVisible(false);
                WaitingGUI wait = new WaitingGUI();
                wait.startGUI();
            }
        });
    }

    public static void startGUI(){
        frame.setContentPane(new LoginGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LoginGUI gui = new LoginGUI();
        gui.startGUI();

    }
}
