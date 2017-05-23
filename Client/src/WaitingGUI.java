import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by msnsk on 2017/05/23.
 */
public class WaitingGUI {
    private static JFrame frame = new JFrame("WaitingGUI");;
    private JPanel panel1;
    private JLabel label1;


    public WaitingGUI() {
        label1.addMouseListener(new MouseAdapter() {
            int count = 3;
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(count > 0) label1.setText("Waiting for more " + String.valueOf(--count) + " users to join the match.");
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