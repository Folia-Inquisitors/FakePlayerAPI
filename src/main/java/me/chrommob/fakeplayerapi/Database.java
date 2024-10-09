package me.chrommob.fakeplayerapi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {
    private HikariDataSource dataSource;
    private final UUID id;
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    public Database(UUID id, String host, int port, String database, String username, String password) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        init();
    }

    private void init() {
        // Initialize database connection
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
        createTables();
    }

    private void createTables() {
        // Create table of id to player count
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_count (id VARCHAR(36) PRIMARY KEY, count INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Map<UUID, Integer> getPlayerCount() {
        Map<UUID, Integer> playerCount = new HashMap<>();
        // Get player count from database
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player_count");
            while (resultSet.next()) {
                playerCount.put(UUID.fromString(resultSet.getString("id")), resultSet.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerCount;
    }
}
