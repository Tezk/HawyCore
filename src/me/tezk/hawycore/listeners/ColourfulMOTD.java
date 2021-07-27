package me.tezk.hawycore.listeners;

import me.tezk.hawycore.HawyCore;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ColourfulMOTD implements Listener {

    private HawyCore plugin;

    public ColourfulMOTD(HawyCore pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        String message = plugin.getMotdMessage();
        boolean colourful = plugin.isMotdColourEnabled();

        if (!(colourful)) {
            event.setMotd(ChatColor.translateAlternateColorCodes('&', message));
            return;
        }

        String colouredMessage = "";
        List<ChatColor> colours = new ArrayList<>();
        List<String> colourListConfig = plugin.getColourListConfig();
        for (String col : colourListConfig) {
            colours.add(ChatColor.valueOf(col));
        }

        for (Character cha : message.toCharArray()) {
            int counter = ThreadLocalRandom.current().nextInt(0, colours.size()-1 + 1);
            colouredMessage += colours.get(counter) + cha.toString();
        }
        event.setMotd(colouredMessage);

    }
}
