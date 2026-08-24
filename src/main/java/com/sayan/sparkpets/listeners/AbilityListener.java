package com.sayan.sparkpets.listeners;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.managers.PlayerDataManager;
import com.sayan.sparkpets.models.PetType;
import com.sayan.sparkpets.models.Rarity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class AbilityListener implements Listener {

    private final SparkPets plugin;

    public AbilityListener(SparkPets plugin) {
        this.plugin = plugin;
    }

    private double getValue(Player player, PetType type) {
        PlayerDataManager.ActiveData data = plugin.getPlayerDataManager().get(player);
        if (data.hasPrimary() && data.primaryType == type) {
            return type.getValue(data.primaryRarity);
        }
        if (data.hasSecondary() && data.secondaryType == type) {
            return type.getValue(data.secondaryRarity);
        }
        return 0;
    }

    private boolean hasPet(Player player, PetType type) {
        return getValue(player, type) > 0;
    }

    // Attack Boost (Wolf)
    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        double boost = getValue(player, PetType.WOLF);
        if (boost > 0) {
            event.setDamage(event.getDamage() * (1 + boost / 100.0));
        }

        // Wither Skeleton - chance to apply wither
        double witherChance = getValue(player, PetType.WITHER_SKELETON);
        if (witherChance > 0 && ThreadLocalRandom.current().nextDouble(100) < witherChance) {
            if (event.getEntity() instanceof org.bukkit.entity.LivingEntity living) {
                living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0));
            }
        }
    }

    // Damage Reduction (Golem) + Blast Proof (Creeper) + Phase Shift (Enderman)
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // Golem - general damage reduction
        double reduction = getValue(player, PetType.GOLEM);
        if (reduction > 0) {
            event.setDamage(event.getDamage() * (1 - reduction / 100.0));
        }

        // Creeper - explosion reduction
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
            event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            double blast = getValue(player, PetType.CREEPER);
            if (blast > 0) {
                event.setDamage(event.getDamage() * (1 - blast / 100.0));
            }
        }

        // Enderman - projectile dodge
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            double dodge = getValue(player, PetType.ENDERMAN);
            if (dodge > 0 && ThreadLocalRandom.current().nextDouble(100) < dodge) {
                event.setCancelled(true);
                player.sendMessage("§bPhase Shift! Projectile avoided.");
            }
        }

        // Pig - chance for regeneration when hit
        double pigChance = getValue(player, PetType.PIG);
        if (pigChance > 0 && ThreadLocalRandom.current().nextDouble(100) < pigChance) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
        }
    }

    // Totem - Second Chance
    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        double chance = getValue(player, PetType.TOTEM);
        if (chance > 0 && ThreadLocalRandom.current().nextDouble(100) < chance) {
            event.setCancelled(true);
            player.setHealth(player.getMaxHealth() * 0.5);
            player.sendMessage("§6§lSecond Chance! You were saved by your Totem Pet!");
            player.playSound(player.getLocation(), org.bukkit.Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
        }
    }
}
