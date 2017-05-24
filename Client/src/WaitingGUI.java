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
    private ClientProfile player;


    public WaitingGUI(ClientProfile player) {
        this.player = player;
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

                if(remoteObject.showConnectedPlayers() < remoteObject.getMaxPlayers()){
                    int result = remoteObject.getMaxPlayers() - remoteObject.showConnectedPlayers();
                    label1.setText("<html><center>Welcome " + player.getNickname() + "<br>Waiting for more " + result + " players</center></html>");
                }
                else {
                    frame.setVisible(false);
                    PlayingGUI play = new PlayingGUI(player);
                    play.startGUI();
                }
            }

        });
    }

    public void startGUI(){
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

/*    public static void main(String[] args) {
        WaitingGUI gui = new WaitingGUI();
        gui.startGUI();

    }*/
}