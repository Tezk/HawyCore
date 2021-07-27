package me.tezk.hawycore.listeners;

import me.tezk.hawycore.HawyCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HalfPlayerSleep implements Listener {

    private HawyCore plugin;

    public HalfPlayerSleep(HawyCore pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event) {
        if (Bukkit.getServer().getWorld("world").getTime() < 12500) {
            return;
        }
        plugin.addPlayerToSleepingList(event.getPlayer());

        new BukkitRunnable() {
                @Override
                public void run() {
                    if (!(event.getPlayer().isSleeping())) {
                        this.cancel();
                        return;
                    }
                    float onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();

                    if (plugin.getSleepingPlayers().size() >= Math.ceil(onlinePlayers/2)) {
                        Bukkit.getServer().getWorld("world").setTime(0);
                        Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Rise and shine gentlemen...");
                        plugin.clearSleepers();
                        cancel();
                        return;
                    } else {

                        double remaining = (Math.ceil( (onlinePlayers / 2) ) - plugin.getSleepingPlayers().size());
                        if (remaining == 1) {
                            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "1" + ChatColor.WHITE + " more player must sleep to " +
                                    "make it morning!");
                        } else {
                            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + String.valueOf((int)remaining) + ChatColor.WHITE +
                                    " more players must sleep to make it morning!");
                        }

                    }
                    cancel();
                    return;
                }
                // 80 is, run this after 20*4s.
            // repeat this every 0 0s, as we cancel() it doesnt have the opportunity to repeat
            }.runTaskTimer(plugin, 80, 80);


    }

    @EventHandler
    public void onPlayerSleepLeave(PlayerBedLeaveEvent event) {
        plugin.removePlayerFromSleepingList(event.getPlayer());

    }
}
