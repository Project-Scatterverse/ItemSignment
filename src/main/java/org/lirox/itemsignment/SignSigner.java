package org.lirox.itemsignment;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static org.lirox.itemsignment.ItemSignment.signerKey;

// yes, a sign signer, because I want it
public class SignSigner {
    public static boolean isSigned(ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(signerKey);
    }

    public static boolean isAuthor(ItemStack itemStack, Player player) {
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(signerKey, PersistentDataType.STRING, "").equals(player.getName());
    }

    public static void sign(ItemStack itemStack, Player player) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(signerKey, PersistentDataType.STRING, player.getName());
        List<Component> lore = new ArrayList<>();
        if (meta.hasLore()) lore = meta.lore();
        lore.add(MessageFormater.format(MessageFormater.getMessage("sign"), player));
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }

    public static void unsign(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().remove(signerKey);
        List<Component> lore = meta.lore();
        lore.remove(lore.size()-1);
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }


    public static void signWholeDamnInventory(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack != null && !itemStack.getType().isAir() && !isSigned(itemStack)) {
                sign(itemStack, player);
                player.getInventory().setItem(i, itemStack);
            }
        }
    }

    public static void unsignWholeDamnInventory(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack != null && !itemStack.getType().isAir() && isSigned(itemStack) && (isAuthor(itemStack, player) || player.getGameMode() == GameMode.CREATIVE)) {
                unsign(itemStack);
                player.getInventory().setItem(i, itemStack);
            }
        }
    }
}
