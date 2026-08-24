package com.sayan.sparkpets.managers;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.models.PetType;
import com.sayan.sparkpets.models.Rarity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final SparkPets plugin;
    private final Map<UUID, ActiveData> cache = new HashMap<>();
    private final File dataFolder;

    public PlayerDataManager(SparkPets plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    }

    public static class ActiveData {
        public PetType primaryType;
        public Rarity primaryRarity;
        public PetType secondaryType;
        public Rarity secondaryRarity;

        public boolean hasPrimary() {
            return primaryType != null;
        }

        public boolean hasSecondary() {
            return secondaryType != null;
        }
    }

    public ActiveData get(Player player) {
        return cache.computeIfAbsent(player.getUniqueId(), uuid -> load(uuid));
    }

    private ActiveData load(UUID uuid) {
        File file = new File(dataFolder, uuid.toString() + ".yml");
        ActiveData data = new ActiveData();
        if (!file.exists()) return data;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.contains("primary.type")) {
            try {
                data.primaryType = PetType.valueOf(config.getString("primary.type"));
                data.primaryRarity = Rarity.valueOf(config.getString("primary.rarity"));
            } catch (Exception ignored) {}
        }
        if (config.contains("secondary.type")) {
            try {
                data.secondaryType = PetType.valueOf(config.getString("secondary.type"));
                data.secondaryRarity = Rarity.valueOf(config.getString("secondary.rarity"));
            } catch (Exception ignored) {}
        }
        return data;
    }

    public void save(Player player) {
        ActiveData data = get(player);
        File file = new File(dataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration config = new YamlConfiguration();

        if (data.hasPrimary()) {
            config.set("primary.type", data.primaryType.name());
            config.set("primary.rarity", data.primaryRarity.name());
        }
        if (data.hasSecondary()) {
            config.set("secondary.type", data.secondaryType.name());
            config.set("secondary.rarity", data.secondaryRarity.name());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save data for " + player.getName());
        }
    }

    public void saveAll() {
        for (UUID uuid : cache.keySet()) {
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null) save(player);
        }
    }

    public void setPrimary(Player player, PetType type, Rarity rarity) {
        ActiveData data = get(player);
        data.primaryType = type;
        data.primaryRarity = rarity;
        save(player);
    }

    public void setSecondary(Player player, PetType type, Rarity rarity) {
        ActiveData data = get(player);
        data.secondaryType = type;
        data.secondaryRarity = rarity;
        save(player);
    }

    public void clearPrimary(Player player) {
        ActiveData data = get(player);
        data.primaryType = null;
        data.primaryRarity = null;
        save(player);
    }

    public void clearSecondary(Player player) {
        ActiveData data = get(player);
        data.secondaryType = null;
        data.secondaryRarity = null;
        save(player);
    }
}
