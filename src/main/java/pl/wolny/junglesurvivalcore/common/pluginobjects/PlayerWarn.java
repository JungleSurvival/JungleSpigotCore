package pl.wolny.junglesurvivalcore.common.pluginobjects;

import java.util.Date;

public class PlayerWarn {
    private Date date;
    private String reason;
    private String admin;
    private boolean mcWarn;

    public Date getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public String getAdmin() {
        return admin;
    }

    public boolean isMcWarn() {
        return mcWarn;
    }

    public PlayerWarn(Date date, String reason, String admin, Boolean mcWarn) {
        this.date = date;
        this.reason = reason;
        this.admin = admin;
        this.mcWarn = mcWarn;
    }
}
