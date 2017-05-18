import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI {
    private JPanel panel1;
    private JButton loginButton;
    private JTextField textField1;
    private static Client client;

    String hostName = "localhost";
    int portNumber = 41423;


    public ClientGUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setUsername(textField1.getText());
                //client.startConnection(hostName,portNumber);

                JOptionPane.showMessageDialog(null,"username sent");


            }
        });
    }

    public void startGUI(){
        JFrame frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ClientGUI gui = new ClientGUI();
        gui.startGUI();

    }
}
