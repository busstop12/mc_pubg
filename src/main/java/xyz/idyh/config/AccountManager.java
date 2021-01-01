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
    private final Logger logger;
    private File accountsFile;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationNode accountsConfig;

    private final Mc_PUBG plugin;

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
        Account account = new Account(plugin, this, uuid);

        if (!hasAccount(uuid)) {
            account.setSkillLevel("mining", 1);
            account.setSkillExp("mining", 0);
            saveConfig();
        } else {
            logger.info("Player has already existed.");
        }
    }

    public boolean hasAccount(UUID uuid) {
        return accountsConfig.getNode(uuid.toString()).getValue() != null;
    }

    public ConfigurationNode getAccountsConfig() {
        return accountsConfig;
    }

    public void saveConfig() {
        try {
            loader.save(accountsConfig);

            logger.info("Successfully saved accounts config.");
        } catch (IOException e) {
            logger.error("Error saving accounts config.");
        }
    }

}
