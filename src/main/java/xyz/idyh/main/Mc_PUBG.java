package xyz.idyh.main;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;

//For Logger
import com.google.inject.Inject;
import org.slf4j.Logger;
import xyz.idyh.config.AccountManager;
import xyz.idyh.config.SkillManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Plugin(id = "mcpubg", name = "MCPUBG", version = "1.0.0-DEV")
public class Mc_PUBG {
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Inject
    private Logger logger;

    private ConfigurationNode config;

    private AccountManager accountManager;
    private SkillManager skillManager;

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        // to create the config file
        try {
            config = loader.load();

            if (!defaultConfig.toFile().exists()) {
                config.getNode("test_for_config").setValue(true);
                loader.save(config);
            }
        } catch (IOException e) {
            logger.warn("Error loading default configuration.");
        }

        accountManager = new AccountManager(this);
        skillManager = new SkillManager(this);
    }

    @Listener
    public void init(GameInitializationEvent event) {

    }

    @Listener
    public void postInit(GamePostInitializationEvent event) {

    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("HELLO!");
        logger.error("Did you see the red text?");
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {

    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        UUID playerUUID = event.getTargetEntity().getUniqueId();

        accountManager.createAccount(playerUUID);
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getConfigDir() {
        return configDir;
    }

    public static void main(String[] args) {
        System.out.println("HELLO MINECRAFT");
        System.out.println("HELLO SPONGE");
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }
}
