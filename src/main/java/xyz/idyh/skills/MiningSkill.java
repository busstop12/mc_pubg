package xyz.idyh.skills;

import ninja.leaping.configurate.ConfigurationNode;

public class MiningSkill implements Skill {
    public MiningSkill() {

    }

    @Override
    public String getSkillName() {
        return "MiningSkill";
    }

    @Override
    public String[][] getExpValues() {
        String[][] values = {{"coal_ore", "10"},
                {"iron_ore", "50"},
                {"gold_iron", "150"},
                {"diamond_ore", "600"}};

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
