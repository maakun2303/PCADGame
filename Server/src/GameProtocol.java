import java.net.*;
import java.io.*;

public class GameProtocol {
    private static final int WAITING = 0;
    private static final int SENTNAMEREQUEST = 1;
    private static final int ACCEPTEDNAME = 2;
    private static final int TEST = 3;

    private int state = WAITING;

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Welcome. Enter your nickname here:";
            state = SENTNAMEREQUEST;
        } else if (state == SENTNAMEREQUEST) {
            if (!theInput.equalsIgnoreCase("") && !theInput.isEmpty()) {
                theOutput = "Your nickname has been set to " + theInput + ". Write \"test\" or \"quit\".";
                state = ACCEPTEDNAME;
            } else {
                theOutput = "Invalid name string, please try again.";
            }
        } else if (state == ACCEPTEDNAME) {
            if (theInput.equalsIgnoreCase("test")) {
                theOutput = "TESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtest";
                state = TEST;
            } else if (theInput.equalsIgnoreCase("quit")) {
                theOutput = "Bye.";
                state = WAITING;
            } else {
                theOutput = "Please write \"test\" or \"quit\".";
            }
        } else if (state == TEST) {
            if (theInput.equalsIgnoreCase("")) {
                theOutput = "Bye.";
                state = WAITING;
            }
        }
        return theOutput;
    }
}
