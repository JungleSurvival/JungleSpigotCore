package pl.wolny.junglesurvivalcore.common;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.bukkit.entity.Player;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WaitingPlayer;
import pl.wolny.junglesurvivalcore.common.pluginobjects.WarnList;

import java.util.*;

public class Cache {
    private final HashMap<UUID, Boolean> WhitelistedPlayers = new HashMap<>();
    private final HashMap<WaitingPlayer, String> WhitelistNumbers = new HashMap<>();
    private final HashMap<String, UUID> DiscordIdToUUID = new HashMap<>();
    private final HashMap<UUID, WarnList> WarnListFromUUID = new HashMap<>();
    private final List<UUID> NonPvpBedrock = new ArrayList<>();
    public final List<UUID> BedrockGamma = new ArrayList<>();
    public final List<UUID> AdminList = new ArrayList<>();

    public HashMap<UUID, Boolean> getWhitelistedPlayers() {
        return WhitelistedPlayers;
    }

    public HashMap<WaitingPlayer, String> getWhitelistNumbers() {
        return WhitelistNumbers;
    }

    public HashMap<String, UUID> getDiscordIdToUUID() {
        return DiscordIdToUUID;
    }

    public HashMap<UUID, WarnList> getWarnListFromUUID() {
        return WarnListFromUUID;
    }

    public List<UUID> getNonPvpBedrock() {
        return NonPvpBedrock;
    }

    public List<UUID> getBedrockGamma() {
        return BedrockGamma;
    }
    public List<UUID> getAdminList(){
        return AdminList;
    }
}
