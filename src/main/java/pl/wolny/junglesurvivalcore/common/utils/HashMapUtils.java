package pl.wolny.junglesurvivalcore.common.utils;

import pl.wolny.junglesurvivalcore.common.pluginobjects.WaitingPlayer;

import java.util.*;

public class HashMapUtils {
    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <K, V> K get(HashMap<WaitingPlayer, String> map, UUID key) {
        for (Map.Entry<WaitingPlayer, String> entry : map.entrySet()) {
            if (entry.getKey().id.equals(key)) {
                return (K) entry.getKey();
            }
        }
        return null;
    }
}
