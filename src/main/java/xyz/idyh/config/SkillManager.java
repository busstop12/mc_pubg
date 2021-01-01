package xyz.idyh.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;
import xyz.idyh.main.Mc_PUBG;
import xyz.idyh.skills.MiningSkill;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SkillManager {
    private final Logger logger;
    private File skillsFile;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationNode skillsConfig;

    private final Mc_PUBG plugin;

    private final MiningSkill miningSkill;

    private final AccountManager accountManager;
    private final ConfigurationNode accountsConfig;

    public SkillManager(Mc_PUBG plugin) {
        this.plugin = plugin;

        logger = plugin.getLogger();

        miningSkill = new MiningSkill();

        accountManager = plugin.getAccountManager();
        accountsConfig = accountManager.getAccountsConfig();

        setupSkillConfig();
    }

    private void setupSkillConfig() {
        skillsFile = new File(plugin.getConfigDir().toFile(), "skills.conf");
        loader = HoconConfigurationLoader.builder().setFile(skillsFile).build();

        try {
            skillsConfig = loader.load();

            if (!skillsFile.exists()) {
                skillsConfig.getNode("test_skill_config").setValue(true);
                miningSkill.setupConfig(skillsConfig);
                loader.save(skillsConfig);
            }
        } catch (IOException e) {
            logger.warn("Error loading the skills configuration.");
        }
    }

    @Listener
    public void onBlockBreak(ChangeBlockEvent event) {
        if (event.getCause().first(Player.class).isPresent()) {
            Player player = event.getCause().first(Player.class).get();
            UUID playerUUID = player.getUniqueId();
            String blockName = event.getTransactions().get(0).getOriginal().getState().getName();
            int expAmount = skillsConfig.getNode("mining", blockName).getInt(0);

            player.sendMessage(Text.of("You broke: ", blockName));
        }
    }
}
