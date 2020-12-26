package xyz.idyh.skills;

import ninja.leaping.configurate.ConfigurationNode;

public interface Skill {
    String getSkillName();

    String[][] getExpValues();

    void setupConfig(ConfigurationNode skillsConfig);
}
