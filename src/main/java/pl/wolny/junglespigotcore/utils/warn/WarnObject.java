package pl.wolny.junglespigotcore.utils.warn;

import java.util.Date;

public class WarnObject {
    private Date date;
    private String reason;
    private String id;
    private String playerName;

    public WarnObject(String PlayerName, String id, String reason, Date date){
        this.date = date;
        this.reason = reason;
        this.id = id;
        this.playerName = PlayerName;
    }
    public String getReason() {
        return reason;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
