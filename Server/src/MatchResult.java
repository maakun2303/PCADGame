import java.io.Serializable;

/**
 * Created by msnsk on 2017/08/28.
 */
public class MatchResult implements Serializable {
    private ClientProfile winner;
    private ClientProfile loser;
    private int winDice;
    private int loseDice;

    public String getWinner(){
        return winner.getNickname();
    }

    public String getLoser(){
        return loser.getNickname();
    }

    public int getWinDice(){
        return winDice;
    }

    public int getLoseDice(){
        return loseDice;
    }

    public void setWinner(ClientProfile winner){
        this.winner = winner;
    }

    public void setLoser(ClientProfile loser){
        this.loser = loser;
    }

    public void setWinDice(int winDice){
        this.winDice = winDice;
    }

    public void setLoseDice(int loseDice){
        this.loseDice = loseDice;
    }
}
