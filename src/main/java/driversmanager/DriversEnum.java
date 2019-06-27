package driversmanager;

public enum DriversEnum {
    CHROME, FIREFOX, OPERA, IE, EDGE;

    public static DriversEnum getDriverValueByAlias(String alias) {
        alias = alias.toLowerCase();
        switch (alias) {
            case "chrome":
                return CHROME;
            case "firefox":
                return FIREFOX;
            case "opera":
                return OPERA;
            case "ie":
                return IE;
            case "edge":
                return EDGE;
            default:
                throw new RuntimeException("нет драйвера с именем: " + alias);
        }
    }
}
