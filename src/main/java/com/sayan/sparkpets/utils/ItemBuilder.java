package com.sayan.sparkpets.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private final List<Component> lore = new ArrayList<>();

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder name(String legacyName) {
        meta.displayName(LegacyComponentSerializer.legacySection().deserialize(legacyName)
                .decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public ItemBuilder name(Component name) {
        meta.displayName(name.decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public ItemBuilder lore(String... lines) {
        for (String line : lines) {
            lore.add(LegacyComponentSerializer.legacySection().deserialize(line)
                    .decoration(TextDecoration.ITALIC, false));
        }
        return this;
    }

    public ItemBuilder lore(Component... lines) {
        for (Component line : lines) {
            lore.add(line.decoration(TextDecoration.ITALIC, false));
        }
        return this;
    }

    public ItemBuilder addLore(String line) {
        lore.add(LegacyComponentSerializer.legacySection().deserialize(line)
                .decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public ItemBuilder glow() {
        meta.setEnchantmentGlintOverride(true);
        return this;
    }

    public ItemBuilder hideFlags() {
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemStack build() {
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
