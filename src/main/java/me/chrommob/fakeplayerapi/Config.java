package me.chrommob.fakeplayerapi;

import me.chrommob.config.ConfigKey;
import me.chrommob.config.ConfigWrapper;

import java.util.ArrayList;
import java.util.List;

public class Config extends ConfigWrapper {
    public Config(String name) {
        super(name, keys());
    }

    private static List<ConfigKey> keys() {
        List<ConfigKey> configKeys = new ArrayList<>();
        List<ConfigKey> mysqlKeys = new ArrayList<>();
        mysqlKeys.add(new ConfigKey("host", "localhost", List.of("MySQL host")));
        mysqlKeys.add(new ConfigKey("port", 3306, List.of("MySQL port")));
        mysqlKeys.add(new ConfigKey("database", "minecraft", List.of("MySQL database")));
        mysqlKeys.add(new ConfigKey("username", "root", List.of("MySQL username")));
        mysqlKeys.add(new ConfigKey("password", "", List.of("MySQL password")));
        List<ConfigKey> servers = new ArrayList<>();
        servers.add(new ConfigKey("example", true, List.of("Example server", "This is an example and is not used")));
        ConfigKey toSync = new ConfigKey("servers", servers, List.of("Servers to sync with"));
        configKeys.add(new ConfigKey("mysql", mysqlKeys, List.of("MySQL settings")));
        configKeys.add(toSync);
        return configKeys;
    }

    public List<String> getServerNames() {
        ConfigKey servers = getKey("servers");
        List<String> names = new ArrayList<>();
        for (ConfigKey server : servers.getChildren().values()) {
            if (server.get().equals("example")) continue; // Skip "example" server
            if (server.getAsBoolean())
                names.add(server.get());
        }
        return names;
    }
}
