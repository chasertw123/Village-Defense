package me.chasertw123.villagedefense.stats;

import java.util.HashMap;

public class PlayerStats {

    private int mobKills, deaths, gamesPlayed, wavesPlayed, wavesWon, wavesLost, totalExperienceEarned, totalGoldEarned, totalGoldSpent;
    private int gameMobKills, gameDeaths, gameWavesPlayed, gameExperienceEarned, gameGoldEarned;
    private HashMap<String, Boolean> achievements;

    public PlayerStats(int mobKills, int deaths, int gamesPlayed, int wavesPlayed, int wavesWon, int wavesLost, int totalGoldEarned, int totalGoldSpent, HashMap<String, Boolean> achievements) {
        super();
        this.mobKills = mobKills;
        this.deaths = deaths;
        this.gamesPlayed = gamesPlayed;
        this.wavesPlayed = wavesPlayed;
        this.wavesWon = wavesWon;
        this.wavesLost = wavesLost;
        this.totalGoldEarned = totalGoldEarned;
        this.totalGoldSpent = totalGoldSpent;
        this.achievements = achievements;
    }

    public HashMap<String, Boolean> getAchievements() {
        return achievements;
    }

    public void setAchievements(HashMap<String, Boolean> achievements) {
        this.achievements = achievements;
    }

    public int getGameStat(Stat stat) {

        switch (stat) {

            case MOBKILLS:
                return gameMobKills;

            case DEATHS:
                return gameDeaths;

                // TODO: Finsih per game stats
        }

        return -1;
    }

    public int getStat(Stat stat) {

        switch (stat) {

            case DEATHS:
                return deaths;

            case GAMESPLAYED:
                return gamesPlayed;

            case MOBKILLS:
                return mobKills;

            case TOTALGOLDEARNED:
                return totalGoldEarned;

            case TOTALGOLDSPENT:
                return totalGoldSpent;

            case WAVESLOST:
                return wavesLost;

            case WAVESPLAYED:
                return wavesPlayed;

            case WAVESWON:
                return wavesWon;

            case TOTALEXPERIENCEEARNED:
                return totalExperienceEarned;
        }

        return -1;
    }

    public void setStat(Stat stat, int i) {

        switch (stat) {

            case DEATHS:
                this.deaths = i;
                break;

            case GAMESPLAYED:
                this.gamesPlayed = i;
                break;

            case MOBKILLS:
                this.mobKills = i;
                break;

            case TOTALGOLDEARNED:
                this.totalGoldEarned = i;
                break;

            case TOTALGOLDSPENT:
                this.totalGoldSpent = i;
                break;

            case WAVESLOST:
                this.wavesLost = i;
                break;

            case WAVESPLAYED:
                this.wavesPlayed = i;
                break;

            case WAVESWON:
                this.wavesWon = i;
                break;

            case TOTALEXPERIENCEEARNED:
                this.totalExperienceEarned = i;
                break;
        }
    }
}
