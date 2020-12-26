package xyz.idyh.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import xyz.idyh.main.Mc_PUBG;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AccountManager {
    private Logger logger;
    private File accountsFile;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationNode accountsConfig;

    private Mc_PUBG plugin;

    public AccountManager(Mc_PUBG plugin) {
        this.plugin = plugin;

        logger = plugin.getLogger();

        setupAccountConfig();
    }

    private void setupAccountConfig() {
        accountsFile = new File(plugin.getConfigDir().toFile(), "accounts.conf");
        loader = HoconConfigurationLoader.builder().setFile(accountsFile).build();

        try {
            accountsConfig = loader.load();

            if (!accountsFile.exists()) {
                accountsConfig.getNode("test_account_config").setValue(true);
                loader.save(accountsConfig);
            }
        } catch (IOException e) {
            logger.warn("Error loading the accounts configuration.");
        }
    }

    public void createAccount(UUID uuid) {
        if (!hasAccount(uuid)) {
            accountsConfig.getNode(uuid.toString(), "mining_level").setValue("1");

            try {
                loader.save(accountsConfig);

                logger.info("Successfully created a new player account.");
            } catch (IOException e) {
                logger.error("Error creating a new player account.");
            }
        } else {
            logger.info("Player has already existed.");
        }
    }

    public boolean hasAccount(UUID uuid) {
        return accountsConfig.getNode(uuid.toString()).getValue() != null;
    }
}
