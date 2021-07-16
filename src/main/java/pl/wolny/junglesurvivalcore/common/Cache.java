package pl.wolny.junglesurvivalcore.common;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WaitingPlayer;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WarnList;

import java.util.HashMap;
import java.util.UUID;

public class Cache {
    private final HashMap<UUID, Boolean> WhitelistedPlayers = new HashMap<>();
    private final BidiMap<String, WaitingPlayer> WhitelistNumbers = new DualHashBidiMap<>();
    private final HashMap<String, UUID> DiscordIdToUUID = new HashMap<>();
    private final HashMap<UUID, WarnList> WarnListFromUUID = new HashMap<>();

    public HashMap<UUID, Boolean> getWhitelistedPlayers() {
        return WhitelistedPlayers;
    }

    public BidiMap<String, WaitingPlayer> getWhitelistNumbers() {
        return WhitelistNumbers;
    }

    public HashMap<String, UUID> getDiscordIdToUUID() {
        return DiscordIdToUUID;
    }

    public HashMap<UUID, WarnList> getWarnListFromUUID() {
        return WarnListFromUUID;
    }
}
