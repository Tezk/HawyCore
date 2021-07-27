package me.tezk.hawycore.commands;

import me.tezk.hawycore.HawyCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    private HawyCore plugin;

    public SetHomeCommand(HawyCore pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
                return true;
            }
            Player player = (Player) sender;
            if (args.length != 0) {
                player.sendMessage(ChatColor.GREEN + "/sethome - to set a home at your location!");
                return true;
            }

            String world = player.getLocation().getWorld().getName();
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();
            float pitch = player.getLocation().getPitch();
            float yaw = player.getLocation().getYaw();

            plugin.getDataManager().get().set("homes." + player.getUniqueId().toString() + ".world", world);
            plugin.getDataManager().get().set("homes." + player.getUniqueId().toString() + ".x", x);
            plugin.getDataManager().get().set("homes." + player.getUniqueId().toString() + ".y", y);
            plugin.getDataManager().get().set("homes." + player.getUniqueId().toString() + ".z", z);
            plugin.getDataManager().get().set("homes." + player.getUniqueId().toString() + ".pitch", pitch);
            plugin.getDataManager().get().set("homes." + player.getUniqueId().toString() + ".yaw", yaw);
            plugin.getDataManager().save();
            player.sendMessage(ChatColor.GREEN + "Home set!");
            return true;

        }
        return true;
    }
}
