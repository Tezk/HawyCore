package me.tezk.hawycore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class DataManager {

    private HawyCore plugin;
    public DataManager(HawyCore pl) {
        this.plugin = pl;
    }

    private File file;
    private FileConfiguration customFile;

    public void setup() {
        file = new File(plugin.getDataFolder(), "data.yml");

        if (!(file.exists())){
            try {
                file.createNewFile();
            } catch (IOException e){
                plugin.getLogger().info(Level.SEVERE + "Could not create data.yml" + e);
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() {
        return customFile;
    }

    public void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            plugin.getLogger().info(Level.SEVERE + "Could not save data.yml" + e);
        }
    }

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
