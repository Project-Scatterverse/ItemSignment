package org.lirox.itemsignment.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.lirox.itemsignment.MessageFormater;
import org.lirox.itemsignment.SignSigner;

import static org.lirox.itemsignment.ItemSignment.config;

@NullMarked
public class UnsignCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        if (player == null) return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType().isAir()) {
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("no-item"), player));
            return;
        }

        if (!SignSigner.isSigned(itemStack)) {
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("no-sign"), player));
            return;
        }

        if (!SignSigner.isAuthor(itemStack, player) && !player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("not-author"), player));
            return;
        }

        try {
            SignSigner.unsign(itemStack);
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("unsign-success"), player));
        } catch (Exception exception) {
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("error"), player));
            MessageFormater.sendErrorLog(exception);
        }
    }

    @Override
    public @Nullable String permission() {
        return config.getBoolean("use-permissions", false) ? "itemsignment.unsign.use" : null;
    }
}