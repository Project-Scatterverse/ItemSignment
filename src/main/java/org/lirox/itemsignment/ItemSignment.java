package org.lirox.itemsignment;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.lirox.itemsignment.commands.SignAllCommand;
import org.lirox.itemsignment.commands.SignCommand;
import org.lirox.itemsignment.commands.UnsignAllCommand;
import org.lirox.itemsignment.commands.UnsignCommand;

import java.util.logging.Logger;

public final class ItemSignment extends JavaPlugin {
    public static Logger log;
    public static FileConfiguration config;
    public static NamespacedKey signerKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();

        signerKey = new NamespacedKey(this, "signer");

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            var registrar = event.registrar();
            registrar.register("sign", new SignCommand());
            registrar.register("sign_all", new SignAllCommand());
            registrar.register("unsign", new UnsignCommand());
            registrar.register("unsign_all", new UnsignAllCommand());
        });

        log = getLogger();
        log.info("Initialised!");
    }

    @Override
    public void onDisable() {
        log.info("Shutdown!");
    }
}
