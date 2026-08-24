package com.sayan.sparkpets.listeners;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.gui.EggGUI;
import com.sayan.sparkpets.gui.FusionGUI;
import com.sayan.sparkpets.gui.PetsGUI;
import com.sayan.sparkpets.managers.PlayerDataManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final SparkPets plugin;

    public GUIListener(SparkPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        String title = event.getView().getTitle();

        // ========== MAIN PETS GUI ==========
        if (title.contains("Pets") && !title.contains("Fusion") && !title.contains("Eggs")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;

            int slot = event.getSlot();

            if (slot == 3) {
                new EggGUI(plugin, player).open();
                return;
            }

            if (slot == 5) {
                new FusionGUI(plugin, player).open();
                return;
            }

            // Deactivate Primary
            if (slot == 13) {
                PlayerDataManager.ActiveData data = plugin.getPlayerDataManager().get(player);
                if (data.hasPrimary()) {
                    ItemStack pet = plugin.getPetManager().createPetItem(data.primaryType, data.primaryRarity);
                    player.getInventory().addItem(pet);
                    plugin.getPlayerDataManager().clearPrimary(player);
                    player.sendMessage("§aPrimary pet deactivated!");
                    new PetsGUI(plugin, player).open();
                }
                return;
            }

            // Deactivate Secondary
            if (slot == 14) {
                PlayerDataManager.ActiveData data = plugin.getPlayerDataManager().get(player);
                if (data.hasSecondary()) {
                    ItemStack pet = plugin.getPetManager().createPetItem(data.secondaryType, data.secondaryRarity);
                    player.getInventory().addItem(pet);
                    plugin.getPlayerDataManager().clearSecondary(player);
                    player.sendMessage("§aSecondary pet deactivated!");
                    new PetsGUI(plugin, player).open();
                }
                return;
            }
        }

        // ========== EGG GUI ==========
        else if (title.contains("Pet Eggs")) {
            if (!player.hasPermission("sparkpets.admin")) {
                event.setCancelled(true);
                player.sendMessage("§cOnly admins can take Pet Eggs!");
            }
        }

        // ========== FUSION GUI ==========
        else if (title.contains("Pet Fusion")) {
            int slot = event.getSlot();
            if (slot < 11 || slot > 15) {
                if (slot != 22) {
                    event.setCancelled(true);
                }
            }
            if (slot == 22) {
                event.setCancelled(true);
                player.sendMessage("§eFusion system is ready. Put same pets and try again later.");
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        String title = event.getView().getTitle();
        if (title.contains("Pets") || title.contains("Pet Eggs") || title.contains("Pet Fusion")) {
            event.setCancelled(true);
        }
    }
}
