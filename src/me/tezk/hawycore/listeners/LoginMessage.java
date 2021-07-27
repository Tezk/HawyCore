package me.tezk.hawycore.listeners;

import me.tezk.hawycore.HawyCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginMessage implements Listener {

    private HawyCore plugin;

    public LoginMessage(HawyCore pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // todo
        plugin.getLoginPhilosophy();

    }
}
