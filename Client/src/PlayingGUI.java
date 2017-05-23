import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by msnsk on 2017/05/23.
 */
public class PlayingGUI {
    private static JFrame frame = new JFrame("PlayingGUI");;
    private JPanel panel1;
    private JLabel label1;


    public PlayingGUI() {
    }

    public static void startGUI(){
        frame.setContentPane(new PlayingGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PlayingGUI gui = new PlayingGUI();
        gui.startGUI();

    }
}
