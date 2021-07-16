package pl.wolny.junglesurvivalcore.common.pluginobjects;

import java.util.ArrayList;
import java.util.List;

public class WarnList {
    private final List<PlayerWarn> playerWarnList = new ArrayList<>();
    private String mcName;

    public List<PlayerWarn> getPlayerWarnList() {
        return playerWarnList;
    }
    public WarnList(String mcName) {
        this.mcName = mcName;
    }
}
