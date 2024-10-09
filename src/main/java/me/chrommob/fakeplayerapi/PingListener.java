package me.chrommob.fakeplayerapi;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;

public class PingListener {
    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing currentPing = event.getPing();
        ServerPing newPing = currentPing.asBuilder()
                .onlinePlayers(currentPing.getPlayers().map(ServerPing.Players::getOnline).orElse(0) + FakePlayerAPI.getInstance().getTotalPlayerCount())
                .build();
        event.setPing(newPing);
    }
}
