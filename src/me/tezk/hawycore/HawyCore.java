package me.tezk.hawycore;

import me.tezk.hawycore.commands.*;
import me.tezk.hawycore.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HawyCore extends JavaPlugin {

    private DataManager dataManager;

    private String motdMessage;
    private boolean motdColourEnabled;
    private List<String> motdColours;
    private List<String> loginPhilosophy;
    private List<String> teleportingHomeCost;
    private String effectType;
    private int effectDuration;
    private String particleType;
    private int particleSize;
    private String activatingClickText;

    private List<Player> sleepingPlayers;
    private List<Player> pausedToTeleport;

    private HashMap<Player, Player> teleportingPlayers;

    @Override
    public void onEnable() {
        initialize();
        getLogger().info("HawyCore loaded baby...");
    }

    public void initialize() {
        saveDefaultConfig();

        this.dataManager = new DataManager(this);
        this.dataManager.setup();

        this.motdMessage = getConfig().getString("motd.message");
        this.motdColourEnabled = getConfig().getBoolean("motd.colourful");
        this.motdColours = getConfig().getStringList("motd.colours");
        this.loginPhilosophy = getConfig().getStringList("login-philosophy");
        this.teleportingHomeCost = getConfig().getStringList("teleporting-home-cost");
        this.effectType = getConfig().getString("sound-effect.type");
        this.effectDuration = getConfig().getInt("sound-effect.duration");
        this.particleType = getConfig().getString("particle.type");
        this.particleSize = getConfig().getInt("particle.size");
        this.activatingClickText = getConfig().getString("shop.activating-click-text");

        this.sleepingPlayers = new ArrayList<>();
        this.teleportingPlayers = new HashMap<>();
        this.pausedToTeleport = new ArrayList<>();

        Bukkit.getPluginCommand("home").setExecutor(new HomeCommand(this));
        Bukkit.getPluginCommand("sethome").setExecutor(new SetHomeCommand(this));
        Bukkit.getPluginCommand("hawy").setExecutor(new HawyReloadCommand(this));
        Bukkit.getPluginCommand("tpa").setExecutor(new TpaCommand(this));
        Bukkit.getPluginCommand("tpaaccept").setExecutor(new TpaAcceptCommand(this));
        getServer().getPluginManager().registerEvents(new ColouredChat(), this);
        getServer().getPluginManager().registerEvents(new ColourfulMOTD(this), this);
        getServer().getPluginManager().registerEvents(new HalfPlayerSleep(this), this);
        getServer().getPluginManager().registerEvents(new HomeCommand(this), this);
        getServer().getPluginManager().registerEvents(new LoginMessage(this), this);
        getServer().getPluginManager().registerEvents(new ShopSign(this), this);
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public String getMotdMessage() {
        return this.motdMessage;
    }

    public boolean isMotdColourEnabled() {
        return this.motdColourEnabled;
    }

    public List<String> getColourListConfig() {
        return this.motdColours;
    }

    public List<String> getTeleportingHomeCost() {
        return this.teleportingHomeCost;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }

    public String getParticleType() {
        return this.particleType;
    }

    public int getParticleSize() {
        return this.particleSize;
    }

    public void addPlayerToSleepingList(Player player) {
        this.sleepingPlayers.add(player);
    }

    public void removePlayerFromSleepingList(Player player) {
        this.sleepingPlayers.remove(player);
    }

    public List<Player> getSleepingPlayers() {
        return this.sleepingPlayers;
    }

    public HashMap<Player, Player> getTeleportingPlayers() {
        return this.teleportingPlayers;
    }

    public void clearSleepers() {
        this.sleepingPlayers.clear();
    }

    public List<Player> getPausedToTeleportPlayers() {
        return this.pausedToTeleport;
    }

    public List<String> getLoginPhilosophy() {
        return this.loginPhilosophy;
    }

    public String getShopActivatingClickText() {
        return this.activatingClickText;
    }


}
