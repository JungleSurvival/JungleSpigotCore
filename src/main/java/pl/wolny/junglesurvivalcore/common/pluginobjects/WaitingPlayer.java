package pl.wolny.junglesurvivalcore.common.pluginobjects;

import java.util.UUID;

public class WaitingPlayer {
    public UUID id;
    public String name;
    public WaitingPlayer(UUID id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "WaitingPlayer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
