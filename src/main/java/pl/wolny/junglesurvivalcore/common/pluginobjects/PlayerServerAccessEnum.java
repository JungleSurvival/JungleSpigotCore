package pl.wolny.junglesurvivalcore.common.pluginobjects;

public enum PlayerServerAccessEnum {

    WHITELISTED(true),
    NOT_WHITELISTED(false),
    MYSQL_ERROR(false);

    boolean access;

    private PlayerServerAccessEnum(boolean access) {
        this.access = access;
    }
    public static PlayerServerAccessEnum getFromBoolean(Boolean access) {
        return (access) ? PlayerServerAccessEnum.WHITELISTED : PlayerServerAccessEnum.NOT_WHITELISTED;
    }
}
