package com.sayan.sparkpets.commands;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.gui.PetsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PetsCommand implements CommandExecutor {

    private final SparkPets plugin;

    public PetsCommand(SparkPets plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }

        if (!player.hasPermission("sparkpets.use")) {
            player.sendMessage("§cYou don't have permission to use this command.");
            return true;
        }

        new PetsGUI(plugin, player).open();
        return true;
    }
}
