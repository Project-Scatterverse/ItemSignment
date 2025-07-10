package org.lirox.itemsignment;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
        itemStack.setItemMeta(meta);
    }

    public static void unsign(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().remove(signerKey);
        itemStack.setItemMeta(meta);
    }

    public static void signWholeDamnInventory(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.isEmpty() && !isSigned(itemStack)) {
                sign(itemStack, player);
            }
        }
    }

    public static void unsignWholeDamnInventory(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.isEmpty() && (isAuthor(itemStack, player) || player.getGameMode().equals(GameMode.CREATIVE))) {
                unsign(itemStack);
            }
        }
    }
}
