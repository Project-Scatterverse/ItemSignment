package org.lirox.itemsignment.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.lirox.itemsignment.MessageFormater;
import org.lirox.itemsignment.SignSigner;

@NullMarked
public class UnsignAllCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        if (player == null) return;
        try {
            SignSigner.unsignWholeDamnInventory(player);
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("sign_all-success"), player));
        } catch (Exception exception) {
            player.sendMessage(MessageFormater.format(MessageFormater.getMessage("error"), player));
            MessageFormater.sendErrorLog(exception);
        }
    }

    @Override
    public @Nullable String permission() {
        return "itemsignment.unsign_all.use";
    }
}