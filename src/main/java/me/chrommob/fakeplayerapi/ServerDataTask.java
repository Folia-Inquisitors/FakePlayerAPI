package me.chrommob.fakeplayerapi;

import com.velocitypowered.api.proxy.ProxyServer;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ServerDataTask {
    private final AtomicReference<Map<UUID, Integer>> playerCount = new AtomicReference<>(Collections.emptyMap());

    ServerDataTask(ProxyServer server, Database database) {
        server.getScheduler()
                .buildTask(FakePlayerAPI.getInstance(), () -> {
                    playerCount.lazySet(database.getPlayerCount());
                })
                .repeat(1, TimeUnit.SECONDS)
                .schedule();
    }

    int getUpdatePlayerCount(UUID id) {
        return Optional.ofNullable(playerCount.get())
                .map(map -> map.getOrDefault(id, 0))
                .orElse(0);
    }
}
