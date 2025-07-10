package org.lirox.itemsignment.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.lirox.itemsignment.MessageFormater;

import static org.lirox.itemsignment.ItemSignment.*;

@NullMarked
public class SignCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        if (player == null) return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.isEmpty()) {
            player.sendMessage(MessageFormater.format(config.getString("messages.no-item", "404 Check config"), player));
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(signerKey)) {
            player.sendMessage(MessageFormater.format(config.getString("messages.already-signed", "404 Check config"), player));
            return;
        }

        try {
            data.set(signerKey, PersistentDataType.STRING, player.getName());
            itemStack.setItemMeta(meta);
            player.sendMessage(MessageFormater.format(config.getString("messages.sign-success", "404 Check config"), player));
        } catch (Exception exception) {
            player.sendMessage(MessageFormater.format(config.getString("messages.error", "404 Check config"), player));
            log.warning(config.getString("messages.error-console", "404 Check config"));
            log.warning(exception.getMessage());
        }
    }

    @Override
    public @Nullable String permission() {
        return "itemsignment.sign.use";
    }
}