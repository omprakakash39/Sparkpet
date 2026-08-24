package com.sayan.sparkpets.listeners;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.managers.PlayerDataManager;
import com.sayan.sparkpets.models.PetType;
import com.sayan.sparkpets.models.Rarity;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PetInteractListener implements Listener {

    private final SparkPets plugin;

    public PetInteractListener(SparkPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // ========== PET EGG ==========
        if (plugin.getPetManager().isPetEgg(item)) {
            event.setCancelled(true);
            Rarity rarity = plugin.getPetManager().getRarity(item);

            // Remove one egg
            item.setAmount(item.getAmount() - 1);

            // Random pet of that rarity
            List<PetType> allPets = new ArrayList<>();
            for (PetType type : PetType.values()) allPets.add(type);
            PetType randomPet = allPets.get(ThreadLocalRandom.current().nextInt(allPets.size()));

            ItemStack petItem = plugin.getPetManager().createPetItem(randomPet, rarity);
            player.getInventory().addItem(petItem);

            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
            player.sendMessage("§aYou received a " + rarity.getBoldColoredName() + " §a" + randomPet.getDisplayName() + "§a!");
            return;
        }

        // ========== ACTIVATE PET ==========
        if (plugin.getPetManager().isPet(item)) {
            event.setCancelled(true);

            PetType type = plugin.getPetManager().getPetType(item);
            Rarity rarity = plugin.getPetManager().getRarity(item);
            if (type == null || rarity == null) return;

            PlayerDataManager.ActiveData data = plugin.getPlayerDataManager().get(player);

            if (type.isPrimary()) {
                if (data.hasPrimary()) {
                    player.sendMessage("§cYou already have a Primary pet active! Deactivate it first.");
                    return;
                }
                plugin.getPlayerDataManager().setPrimary(player, type, rarity);
                player.sendMessage("§aPrimary pet activated: " + type.getDisplayName());
            } else {
                if (data.hasSecondary()) {
                    player.sendMessage("§cYou already have a Secondary pet active! Deactivate it first.");
                    return;
                }
                plugin.getPlayerDataManager().setSecondary(player, type, rarity);
                player.sendMessage("§aSecondary pet activated: " + type.getDisplayName());
            }

            // Remove pet from inventory
            item.setAmount(item.getAmount() - 1);

            // Beacon activate sound
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
        }
    }
}
