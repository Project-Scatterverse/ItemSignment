package org.lirox.itemsignment.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.lirox.itemsignment.MessageFormater;
import org.lirox.itemsignment.SignSigner;

import static org.lirox.itemsignment.ItemSignment.config;

@NullMarked
public class SignAllCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        if (player == null) return;
        try {
            SignSigner.signWholeDamnInventory(player);
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("sign_all-success"), player));
        } catch (Exception exception) {
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("error"), player));
            MessageFormater.sendErrorLog(exception);
        }
    }

    @Override
    public @Nullable String permission() {
        return config.getBoolean("use-permissions", false) ? "itemsignment.sign_all.use" : null;
    }
}