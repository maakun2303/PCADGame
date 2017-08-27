import java.io.Serializable;

public class GameTimer implements Serializable {
    int minutes;
    int seconds;

    public GameTimer(){
        minutes = 0;
        seconds = 10;
    }

    public void decTimer(){
        if(seconds == 0) {
            if (minutes > 0) {
                minutes--;
                seconds = 59;
            }
        }
        else seconds--;
    }

    public String toString(){
        String min = (minutes < 10)? "0"+ minutes : String.valueOf(minutes);
        String sec = (seconds < 10)? "0" + seconds : String.valueOf(seconds);
        return min + ":" + sec;
    }

    public boolean isRunning(){
        return (minutes == 0 && seconds == 0)? false : true;
    }
}