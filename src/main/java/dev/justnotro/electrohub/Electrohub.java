package dev.justnotro.electrohub;

import dev.justnotro.electrohub.structs.Message;
import dev.justnotro.electrohub.utils.CommandAutoRegistration;
import dev.justnotro.electrohub.utils.Config;
import dev.justnotro.electrohub.utils.ListenerAutoRegistration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;


public final class Electrohub extends JavaPlugin {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static Electrohub instance;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Config
        languageConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        setLanguageConfig(new Config("language", null, false));
        new ListenerAutoRegistration(this, false).register("dev.justnotro.electrohub.listeners");
        if (!new CommandAutoRegistration(this, false).register("dev.justnotro.electrohub.commands")) {
            this.getLogger().warning("Couldn't load all commands properly! please check any exceptions that pop up on console!");
            getPluginLoader().disablePlugin(this);
        } else Bukkit.getConsoleSender().sendMessage("&a&lSuccessfully loaded all commands from default sources!");

        Bukkit.getConsoleSender().sendMessage(Message.fixColor("&aElectroHub is now enabled!"));
    }

    /**
     * This method is used when doing a deep reload for configs.
     * Use only if you disable/enable DoubleJump in the config.yml.
     */
    public void reloadSystem() {
        Electrohub.getInstance().reloadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(Message.fixColor("&cElectroHub is now disabled!"));
    }

    public String getPrefix() {
        return Message.fixColor(getConfig().getString("system.prefix"));
    }

    public FixedMetadataValue doubleJump(boolean state) {
        return new FixedMetadataValue(instance, state);
    }
}
