package org.lirox.itemsignment;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.lirox.itemsignment.ItemSignment.config;
import static org.lirox.itemsignment.ItemSignment.log;

public class MessageFormater {
    private final static MiniMessage mm = MiniMessage.miniMessage();
    private final static String prefix = config.getString("messages.prefix", "404 Check config");

    public static @NotNull Component format(String message, Player player) {
        return mm.deserialize((prefix+message)
                .replace("%player%", player.getName())
                .replace("%time%", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .replace("%date%", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd")))
                .replace("%year%", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")))
                .replace("%mc_day%", String.valueOf(player.getWorld().getFullTime() / 24000L))
        );
    }

    public static @NotNull String getMessage(String key) {
        return config.getString("messages."+key, "404 Check config");
    }

    public static void sendErrorLog(Exception exception) {
        log.warning(config.getString("messages.error-console", "404 Check config"));
        log.warning(exception.getMessage());
    }
}
