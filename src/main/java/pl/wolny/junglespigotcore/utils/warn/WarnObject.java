package pl.wolny.junglespigotcore.utils.warn;

import java.util.Date;

public class WarnObject {
    private Date date;
    private String reason;
    private int id;
    private String playerName;

    public WarnObject(String PlayerName, int id, String reason, Date date){
        this.date = date;
        this.reason = reason;
        this.id = id;
        this.playerName = PlayerName;
    }
    public String getReason() {
        return reason;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
