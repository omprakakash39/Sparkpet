package com.sayan.sparkpets;

import com.sayan.sparkpets.commands.PetsCommand;
import com.sayan.sparkpets.listeners.AbilityListener;
import com.sayan.sparkpets.listeners.GUIListener;
import com.sayan.sparkpets.listeners.PetInteractListener;
import com.sayan.sparkpets.managers.PetManager;
import com.sayan.sparkpets.managers.PlayerDataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SparkPets extends JavaPlugin {

    private static SparkPets instance;
    private PetManager petManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.petManager = new PetManager(this);
        this.playerDataManager = new PlayerDataManager(this);

        getCommand("pets").setExecutor(new PetsCommand(this));

        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        getServer().getPluginManager().registerEvents(new PetInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);

        getLogger().info("SparkPets has been enabled!");
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.saveAll();
        }
        getLogger().info("SparkPets has been disabled!");
    }

    public static SparkPets getInstance() {
        return instance;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
