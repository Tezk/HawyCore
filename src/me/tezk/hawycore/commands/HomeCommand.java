package me.tezk.hawycore.commands;

import me.tezk.hawycore.HawyCore;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HomeCommand implements CommandExecutor, Listener {

    private HawyCore plugin;

    public HomeCommand(HawyCore pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("home")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
                return true;
            }
            Player player = (Player) sender;
            if (args.length != 0) {
                player.sendMessage(ChatColor.RED + "Incorrect usage: /home");
                return true;
            }
            String uuid = player.getUniqueId().toString();
            if (plugin.getDataManager().get().get("homes." + uuid) == null) {
                player.sendMessage(ChatColor.RED + "You need to set a home first!");
                return true;
            }

            if (player.getHealth() != 20.0) {
                player.sendMessage(ChatColor.RED + "You must be fully healed (and satiated) to teleport home.");
                return true;
            }
            if (!(player.isOnGround())) {
                player.sendMessage(ChatColor.RED + "You must be standing on a block to teleport home.");
                return true;
            }

            List<String> costsList = plugin.getTeleportingHomeCost();
            List<ItemStack> materialsRequired = new ArrayList<>();
            String costListMsg = "";

            for (String cost : costsList) {
                String mat = cost.split(";")[0];
                int amount = Integer.valueOf(cost.split(";")[1]);
                costListMsg += String.valueOf(amount) + " " + mat.toLowerCase() + "(s) ";

                ItemStack item = new ItemStack(Material.valueOf(mat), amount);
                materialsRequired.add(item);
            }

            for (ItemStack indiItemWithAmount : materialsRequired) {
                if (!(player.getInventory().containsAtLeast(indiItemWithAmount, indiItemWithAmount.getAmount()))) {
                    player.sendMessage(ChatColor.GREEN + "Costs you " + ChatColor.WHITE + costListMsg + ChatColor.GREEN +"to teleport home");
                    return true;
                }
            }

            player.sendMessage(ChatColor.GREEN + "You will be teleported home in 5 seconds unless you move.");
            plugin.getPausedToTeleportPlayers().add(player);
            new BukkitRunnable() {

                @Override
                public void run() {

                    if (plugin.getPausedToTeleportPlayers().contains(player)) {

                        String world = plugin.getDataManager().get().getString("homes." + uuid + ".world");
                        int x = plugin.getDataManager().get().getInt("homes." + uuid + ".x");
                        int y = plugin.getDataManager().get().getInt("homes." + uuid + ".y");
                        int z = plugin.getDataManager().get().getInt("homes." + uuid + ".z");
                        float pitch = plugin.getDataManager().get().getInt("homes." + uuid + ".pitch");
                        float yaw = plugin.getDataManager().get().getInt("homes." + uuid + ".yaw");

                        Location home = new Location(Bukkit.getWorld(world), x, y, z);
                        home.setPitch(pitch);
                        home.setYaw(yaw);
                        player.teleport(home);

                        String effect = plugin.getEffectType();
                        int effectDuration = plugin.getEffectDuration();
                        String particle = plugin.getParticleType();
                        int particleSize = plugin.getParticleSize();

                        try {
                            player.playEffect(player.getLocation(), Effect.valueOf(effect), effectDuration);
                            player.spawnParticle(Particle.valueOf(particle), player.getLocation(), particleSize);
                        } catch (Exception ex) {
                            plugin.getLogger().info("Incorrect particle or effect enum used in config. Check the Spigot API.");
                        }

                        for (ItemStack indiItemWithAmount : materialsRequired) {
                            player.getInventory().removeItem(new ItemStack(indiItemWithAmount.getType(), indiItemWithAmount.getAmount()));
                        }

                        player.sendMessage(ChatColor.GREEN + "Teleported home!");
                        plugin.getPausedToTeleportPlayers().remove(player);
                    }
                    cancel();
                } // run this after X, repeat every Y
            }.runTaskTimer(plugin, 100, -1);
        }
        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (plugin.getPausedToTeleportPlayers().contains(event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.RED + "Teleportation cancelled!");
        }
        while (plugin.getPausedToTeleportPlayers().contains(event.getPlayer())) {
            plugin.getPausedToTeleportPlayers().remove(event.getPlayer());

        }

    }
}
