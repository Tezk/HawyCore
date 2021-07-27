package me.tezk.hawycore.commands;

import me.tezk.hawycore.HawyCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TpaCommand implements CommandExecutor {

    private HawyCore plugin;

    public TpaCommand(HawyCore pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
                return true;
            }

            // tpa <player>
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Incorrect command. Try /tpa <player>");
                return true;
            }

            if (Bukkit.getServer().getPlayer(args[0]) == null) {
                player.sendMessage(ChatColor.RED + "That player isn't online!");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            plugin.getTeleportingPlayers().put(targetPlayer, player);
            player.sendMessage(ChatColor.YELLOW + "Teleport request sent to " + ChatColor.WHITE + targetPlayer.getName());
            targetPlayer.sendMessage(ChatColor.YELLOW + "You have 15 seconds to accept the teleport request from " + ChatColor.WHITE + player.getName() + ChatColor.YELLOW + " using /tpaaccept. Otherwise, ignore the request.");

            new BukkitRunnable() {
                @Override
                public void run() {
                    plugin.getTeleportingPlayers().remove(targetPlayer, player);
                    cancel();
                }
            }.runTaskTimer(plugin, 300, 300);


        }
        return true;
    }
}
