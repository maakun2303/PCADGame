import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI {
    private JFrame frame;
    private JFrame frame2;
    private JPanel panel1;
    private JPanel panel2;
    private JButton loginButton;
    private JTextField textField1;


    public ClientGUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.setUsername(textField1.getText());
                client.startConnection(Client.hostName, Client.portNumber);

                panel1.removeAll();
                panel1.repaint();
                JOptionPane.showMessageDialog(null,"username sent");
            }
        });
    }

    public void startGUI(){
        frame = new JFrame("ClientGUI");
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
