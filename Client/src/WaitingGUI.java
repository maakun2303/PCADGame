import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by msnsk on 2017/05/23.
 */
public class WaitingGUI {
    private static JFrame frame = new JFrame("WaitingGUI");;
    private JPanel panel1;
    private JLabel label1;


    public WaitingGUI() {
        label1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ClientClass client = new ClientClass();
                serverInterface remoteObject = null;
                try {
                    remoteObject = client.getServerInterface(client.remoteHost,client.portWasBinded );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if(remoteObject.ShowConnectedPlayers() > 0) label1.setText("Waiting for more " + remoteObject.ShowConnectedPlayers());
                else {
                    frame.setVisible(false);
                    PlayingGUI play = new PlayingGUI();
                    play.startGUI();
                }
            }

        });
    }

    public static void startGUI(){
        frame.setContentPane(new WaitingGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        WaitingGUI gui = new WaitingGUI();
        gui.startGUI();

    }
}