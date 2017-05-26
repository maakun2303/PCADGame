import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by msnsk on 2017/05/23.
 */
public class PlayingGUI extends JFrame{
    private JPanel panel1;
    private JLabel label1;
    private ClientProfile player;


    public PlayingGUI(ClientProfile player) {
        this.player = player;

        setTitle("PlayingGUI");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
