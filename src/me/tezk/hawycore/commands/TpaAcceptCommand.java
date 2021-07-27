package me.tezk.hawycore.commands;

import me.tezk.hawycore.HawyCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TpaAcceptCommand implements CommandExecutor {

    private HawyCore plugin;
    public TpaAcceptCommand(HawyCore pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpaaccept")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Command only available in game.");
                return true;
            }
            Player player = (Player) sender;
            if (args.length != 0) {
                player.sendMessage(ChatColor.RED + "Incorrect syntax. Type /tpaaccept");
                return true;
            }

            if (plugin.getTeleportingPlayers().get(player) != null) {
                Player target = plugin.getTeleportingPlayers().get(player);

                player.sendMessage(ChatColor.YELLOW + "Teleport request accepted!");
                target.sendMessage(player.getName() + ChatColor.YELLOW + " accepted your teleport request!");
                if (target.getHealth() != 20.0) {
                    target.sendMessage(ChatColor.RED + "You must be fully healed (and satiated) to teleport.");
                    return true;
                }
                if (!(target.isOnGround())) {
                    target.sendMessage(ChatColor.RED + "You must be standing on a block to teleport.");
                    return true;
                }

                target.sendMessage(ChatColor.YELLOW + "You will be teleported in 5 seconds unless you move.");
                plugin.getPausedToTeleportPlayers().add(target);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (plugin.getPausedToTeleportPlayers().contains(target)) {
                            target.teleport(player.getLocation());
                            target.sendMessage(ChatColor.WHITE + "Teleported to " + ChatColor.GREEN + player.getName());
                        }
                        if (plugin.getPausedToTeleportPlayers().contains(target)) {
                            plugin.getPausedToTeleportPlayers().remove(target);
                        }
                        cancel();
                    } // run this after X, repeat every Y
                }.runTaskTimer(plugin, 100, 0);

                plugin.getTeleportingPlayers().remove(player, target);
            } else {
                player.sendMessage(ChatColor.RED + "You have no open teleport requests.");
            }

        }
        return true;
    }

}
