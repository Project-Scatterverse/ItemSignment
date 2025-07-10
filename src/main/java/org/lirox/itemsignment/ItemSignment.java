package org.lirox.itemsignment;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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

        log = getLogger();
        log.info("Initialised!");
    }

    @Override
    public void onDisable() {
        log.info("Shutdown!");
    }
}
