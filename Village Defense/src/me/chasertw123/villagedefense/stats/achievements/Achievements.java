package me.chasertw123.villagedefense.stats.achievements;

import java.util.HashMap;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.DuplicateAchievementIDException;
import me.chasertw123.villagedefense.stats.achievements.standard.BetaTester;
import me.chasertw123.villagedefense.stats.achievements.standard.FirstTimer;

public class Achievements {

    public static HashMap<String, Achievement> ID_MAP = new HashMap<String, Achievement>();

    public Achievements(Main plugin) {
        ID_MAP.clear();

        try {
            this.registerAchievement(new BetaTester(plugin));
            this.registerAchievement(new FirstTimer(plugin));
        } catch (DuplicateAchievementIDException e) {
            e.printStackTrace();
        }
    }

    public void registerAchievement(Achievement a) throws DuplicateAchievementIDException {
        if (ID_MAP.containsKey(a.getId()))
            throw new DuplicateAchievementIDException(a.getId() + " is already in a registered achievement");

        ID_MAP.put(a.getId(), a);
    }
}
