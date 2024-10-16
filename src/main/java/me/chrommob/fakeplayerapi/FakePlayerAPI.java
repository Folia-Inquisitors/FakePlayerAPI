package me.chrommob.fakeplayerapi;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.chrommob.config.ConfigManager;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@Plugin(
        id = "fakeplayerapi",
        name = "FakePlayerAPI",
        version = BuildConstants.VERSION
)
public class FakePlayerAPI {

    @Inject
    private Logger logger;

    @Inject
    private ProxyServer server;

    @Inject @DataDirectory
    private Path dataDirectory;

    private ServerDataTask serverDataTask;
    private static FakePlayerAPI instance;
    private Config config;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        config = new Config("config");
        ConfigManager configManager = new ConfigManager(dataDirectory.toFile());
        configManager.addConfig(config);
        Database database = new Database(null, config.getKey("mysql").getKey("host").getAsString(), config.getKey("mysql").getKey("port").getAsInt(), config.getKey("mysql").getKey("database").getAsString(), config.getKey("mysql").getKey("username").getAsString(), config.getKey("mysql").getKey("password").getAsString());
        serverDataTask = new ServerDataTask(server, database);
        if (config.getKey("inject-motd").getAsBoolean()) {
            server.getEventManager().register(this, new PingListener());
        }
    }

    public static FakePlayerAPI getInstance() {
        return instance;
    }

    public int getUpdatePlayerCount(UUID id) {
        return serverDataTask.getUpdatePlayerCount(id);
    }

    public int getTotalPlayerCount() {
        int total = 0;
        for (String server : config.getServerNames()) {
            total += getUpdatePlayerCount(UUID.fromString(server));
        }
        return total;
    }
}
