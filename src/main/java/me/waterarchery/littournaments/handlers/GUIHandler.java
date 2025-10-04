package me.waterarchery.littournaments.handlers;

import me.waterarchery.litlibs.configuration.ConfigManager;
import me.waterarchery.litlibs.libs.gui.builder.item.ItemBuilder;
import me.waterarchery.litlibs.libs.gui.guis.BaseGui;
import me.waterarchery.litlibs.libs.gui.guis.GuiItem;
import me.waterarchery.litlibs.libs.xseries.XMaterial;
import me.waterarchery.litlibs.libs.xseries.profiles.builder.XSkull;
import me.waterarchery.litlibs.libs.xseries.profiles.objects.Profileable;
import me.waterarchery.litlibs.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GUIHandler {

    private static GUIHandler instance;

    public static GUIHandler getInstance() {
        if (instance == null) instance = new GUIHandler();
        return instance;
    }

    private GUIHandler() {
    }

    public String getMenuTitle(ConfigManager menu) {
        FileConfiguration yml = menu.getYml();
        String title = yml.getString("Title");

        return ChatUtils.colorizeLegacy(title);
    }

    public int getMenuSize(ConfigManager menu) {
        FileConfiguration yml = menu.getYml();
        int size = yml.getInt("Size");

        return size / 9;
    }

    public ItemStack craftItemStack(ConfigManager menu, String itemName, String path, @Nullable UUID player) {
        FileConfiguration yml = menu.getYml();

        String name = yml.getString(path + "." + itemName + ".Name");
        String materialName = yml.getString(path + "." + itemName + ".Material", "BEDROCK");
        List<String> rawLore = yml.getStringList(path + "." + itemName + ".Lore");
        List<String> lore = new ArrayList<>();
        int customModelData = yml.getInt(path + "." + itemName + ".CustomModelData", -1);

        ItemStack itemStack = parseMaterial(materialName, player);
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (String part : rawLore) {
            lore.add(ChatUtils.colorizeLegacy(part));
        }

        assert itemMeta != null;
        itemMeta.setDisplayName(ChatUtils.colorizeLegacy(name));
        itemMeta.setLore(lore);
        if (customModelData != -1) itemMeta.setCustomModelData(customModelData);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void fillGUI(FileConfiguration yml, BaseGui gui, ConfigManager manager) {
        // Filling GUI
        if (yml.getBoolean("FillMenu.Enabled", true)) {
            ItemStack itemStack = craftItemStack(manager, "FillItem", "FillMenu", null);
            GuiItem guiItem = ItemBuilder.from(itemStack).asGuiItem();
            gui.getFiller().fill(guiItem);
        }
    }

    public void decorateGUI(FileConfiguration yml, BaseGui gui, ConfigManager manager) {
        for (String decorationItem : Objects.requireNonNull(yml.getConfigurationSection("Decoration")).getKeys(false)) {
            ItemStack itemStack = craftItemStack(manager, decorationItem, "Decoration", null);
            int slot = yml.getInt("Decoration." + decorationItem + ".Slot");
            String action = yml.getString("Decoration." + decorationItem + ".Action", "none");

            GuiItem guiItem = ItemBuilder.from(itemStack)
                    .asGuiItem(event -> {
                        if (action.equalsIgnoreCase("close")) event.getWhoClicked().closeInventory();
                    });

            gui.setItem(slot, guiItem);
        }
    }

    private ItemStack parseMaterial(String material, @Nullable UUID uuid) {
        if (material.contains("HEAD-")) {
            String headBase = material.replace("HEAD-", "");
            return XSkull.createItem().profile(Profileable.detect(headBase)).apply();
        } else if (material.equalsIgnoreCase("PLAYER") && uuid != null) {
            try {
                return XSkull.createItem()
                        .profile(Profileable.of(uuid))
                        .fallback(Profileable.of(new ItemStack(Material.PLAYER_HEAD)))
                        .apply();
            } catch (Exception e) {
                return new ItemStack(Material.PLAYER_HEAD);
            }
        } else {
            return XMaterial.matchXMaterial(material)
                    .map(XMaterial::parseItem)
                    .orElse(new ItemStack(Material.STONE));
        }
    }

}
