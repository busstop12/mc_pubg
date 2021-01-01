package xyz.idyh.config;

import ninja.leaping.configurate.ConfigurationNode;
import xyz.idyh.main.Mc_PUBG;

import java.util.UUID;

public class Account {
    private Mc_PUBG plugin;
    private AccountManager accountsManager;
    private UUID uuid;

    private ConfigurationNode accountsConfig;

    public Account(Mc_PUBG plugin, AccountManager accountManager, UUID uuid) {
        this.plugin = plugin;
        this.accountsManager = accountManager;
        this.uuid = uuid;

        accountsConfig = accountManager.getAccountsConfig();
    }

    public int getSkillExp(String skillName) {
        return accountsConfig.getNode(uuid.toString(), "skills", skillName, "exp").getInt(0);
    }

    public void setSkillExp(String skillName, int expAmount) {
        accountsConfig.getNode(uuid.toString(), "skills", skillName, "exp").setValue(expAmount);

        accountsManager.saveConfig();
    }

    public int getSkillLevel(String skillName) {
        return accountsConfig.getNode(uuid.toString(), "skills", skillName, "level").getInt(1);
    }

    public void setSkillLevel(String skillName, int skillLevel) {
        accountsConfig.getNode(uuid.toString(), "skills", skillName, "level").setValue(skillLevel);

        accountsManager.saveConfig();
    }
}
