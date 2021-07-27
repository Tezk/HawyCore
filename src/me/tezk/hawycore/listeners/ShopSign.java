package me.tezk.hawycore.listeners;

import me.tezk.hawycore.HawyCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopSign implements Listener {

    private HawyCore plugin;

    public ShopSign(HawyCore pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void shop(SignChangeEvent event) {

        if (event.getLine(1).equalsIgnoreCase(plugin.getShopActivatingClickText())) {
            event.getPlayer().sendMessage(ChatColor.RED + "Shop created!");
        }
    }

    @EventHandler
    public void onShopClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', plugin.getShopActivatingClickText()))) {

                ConfigurationSection items = plugin.getConfig().getConfigurationSection("shop.items");
                for (String item : items.getKeys(false)) {
                    if (sign.getLine(2).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("shop.items." + item + ".name")))) {

                        List<String> costsList = plugin.getConfig().getStringList("shop.items."+item+".cost");
                        List<ItemStack> materialsRequired = new ArrayList<>();
                        String costListMsg = "";

                        for (String cost : costsList) {
                            String mat = cost.split(";")[0];
                            int amount = Integer.valueOf(cost.split(";")[1]);
                            costListMsg += String.valueOf(amount) + " " + mat.toLowerCase() + "(s) ";

                            ItemStack itemSt = new ItemStack(Material.valueOf(mat), amount);
                            materialsRequired.add(itemSt);
                        }

                        for (ItemStack indiItemWithAmount : materialsRequired) {

                            if (!(event.getPlayer().getInventory().containsAtLeast(indiItemWithAmount, indiItemWithAmount.getAmount()))) {
                                event.getPlayer().sendMessage(ChatColor.YELLOW + "Costs you " + ChatColor.WHITE + costListMsg + ChatColor.YELLOW +"to buy " + ChatColor.WHITE +
                                        String.valueOf(plugin.getConfig().getInt("shop.items."+item+".amount")) + " "
                                        + plugin.getConfig().getString("shop.items."+item+".buying-item").toLowerCase());
                                return;
                            }

                        }
                        for (ItemStack indiItemWithAmount : materialsRequired) {
                            event.getPlayer().getInventory().removeItem(new ItemStack(indiItemWithAmount.getType(), indiItemWithAmount.getAmount()));
                        }
                        if (plugin.getConfig().getString("shop.items."+item+".spawner-type") != null) {
                            ItemStack spawner = new ItemStack(Material.SPAWNER);
                            ItemMeta meta = spawner.getItemMeta();
                            meta.setDisplayName(plugin.getConfig().getString("shop.items."+item+".spawner-type"));
                            spawner.setItemMeta(meta);
                            event.getPlayer().getInventory().addItem(spawner);
                        } else {
                            event.getPlayer().getInventory().addItem(new ItemStack(Material.valueOf(plugin.getConfig().getString("shop.items." + item + ".buying-item")),
                                    plugin.getConfig().getInt("shop.items."+item+".amount")));
                        }

                        event.getPlayer().sendMessage(ChatColor.YELLOW + "Item purchased!");

                    }
                }
            }
        }
    }

    @EventHandler
    public void colourSigns(SignChangeEvent event) {
        int lineNumber = 0;
        for (String line : event.getLines()) {
            event.setLine(lineNumber, ChatColor.translateAlternateColorCodes('&', line));
            lineNumber++;

        }
    }

    public void setSpawner(Block block, EntityType ent) {
        BlockState blockState = block.getState();
        CreatureSpawner spawner = ((CreatureSpawner) blockState);
        spawner.setSpawnedType(ent);
        blockState.update();
    }

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        ItemStack inh = e.getPlayer().getItemInHand();
        if (b != null && inh != null) {
            if (b.getType() == Material.SPAWNER && inh.getType() == Material.SPAWNER) {
                ItemMeta im = inh.getItemMeta();
                if (im.getDisplayName().equals("Zombie Spawner")) {
                    setSpawner(b, EntityType.ZOMBIE);
                } else if (im.getDisplayName().equals("Skeleton Spawner")) {
                    setSpawner(b, EntityType.SKELETON);
                }
            }
        }
    }
}
