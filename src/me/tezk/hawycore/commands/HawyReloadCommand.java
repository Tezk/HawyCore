package me.tezk.hawycore.commands;

import me.tezk.hawycore.HawyCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HawyReloadCommand implements CommandExecutor {

    private HawyCore plugin;

    public HawyReloadCommand(HawyCore pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("hawy")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!(sender.isOp())) {
                        sender.sendMessage(ChatColor.RED + "You must be an operator to perform this magical command.");
                        return true;
                    }
                    plugin.reloadConfig();
                    plugin.initialize();
                    sender.sendMessage(ChatColor.GREEN + "HawyCore configuration & data reloaded!");
                    sender.sendMessage(ChatColor.RED + "** Reloading HawyCore without reloading server may cause bugs **");
                    return true;

                } else if (args[0].equalsIgnoreCase("info")) {
                    sender.sendMessage("==== HawyCore "+ChatColor.ITALIC+"help "+ChatColor.WHITE+"====");
                    sender.sendMessage(ChatColor.YELLOW + "/sethome " + ChatColor.GREEN + "- set a home");
                    sender.sendMessage(ChatColor.YELLOW + "/home " + ChatColor.GREEN + "- teleport to home");
                    sender.sendMessage(ChatColor.YELLOW + "/tpa <player> " + ChatColor.GREEN + "- send a teleport request to a player");
                    sender.sendMessage(ChatColor.YELLOW + "/tpaaccept " + ChatColor.GREEN + "- accept a teleport request from a player");
                    return true;

                } else if (args[0].equalsIgnoreCase("clear")) {
                    Bukkit.getServer().getLogger().info("---------------------------Clearing chat---------------------------");
                    for (int x=0; x<20; x++) {
                        Bukkit.getServer().broadcastMessage("");
                    }
                    Bukkit.getServer().getLogger().info("----------------------------Chat cleared----------------------------");

                } else {
                    sender.sendMessage(ChatColor.RED + "Incorrect syntax. Try /hawy <info|reload|clear>");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect syntax. Try /hawy <info|reload|clear>");
                return true;
            }
        }
        return true;
    }
}
