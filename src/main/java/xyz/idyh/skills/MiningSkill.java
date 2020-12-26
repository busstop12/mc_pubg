package xyz.idyh.skills;

import ninja.leaping.configurate.ConfigurationNode;

public class MiningSkill implements Skill {
    public MiningSkill() {

    }

    @Override
    public String getSkillName() {
        return "mining";
    }

    @Override
    public String[][] getExpValues() {
        String[][] values = {{"minecraft:coal_ore", "10"},
                {"minecraft:iron_ore", "50"}};

        return values;
    }

    @Override
    public void setupConfig(ConfigurationNode skillsConfig) {
        String[][] values = getExpValues();

        for (String[] value : values) {
            skillsConfig.getNode(getSkillName(), value[0]).setValue(value[1]);
        }
    }
}
