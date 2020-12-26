package xyz.idyh.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import xyz.idyh.main.Mc_PUBG;
import xyz.idyh.skills.MiningSkill;

import java.io.File;
import java.io.IOException;

public class SkillManager {
    private Logger logger;
    private File skillsFile;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationNode skillsConfig;

    private Mc_PUBG plugin;

    private MiningSkill miningSkill;

    public SkillManager(Mc_PUBG plugin) {
        this.plugin = plugin;

        logger = plugin.getLogger();

        miningSkill = new MiningSkill();
        
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
}
