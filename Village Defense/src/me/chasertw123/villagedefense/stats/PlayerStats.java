package me.chasertw123.villagedefense.stats;

public class PlayerStats {
    private int mobKills, deaths, gamesPlayed, wavesPlayed, wavesWon, wavesLost, totalGoldEarned, totalGoldSpent;

    public PlayerStats(int mobKills, int deaths, int gamesPlayed, int wavesPlayed, int wavesWon, int wavesLost, int totalGoldEarned, int totalGoldSpent) {
        super();
        this.mobKills = mobKills;
        this.deaths = deaths;
        this.gamesPlayed = gamesPlayed;
        this.wavesPlayed = wavesPlayed;
        this.wavesWon = wavesWon;
        this.wavesLost = wavesLost;
        this.totalGoldEarned = totalGoldEarned;
        this.totalGoldSpent = totalGoldSpent;
    }

    public int getMobKills() {
        return mobKills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWavesPlayed() {
        return wavesPlayed;
    }

    public int getWavesWon() {
        return wavesWon;
    }

    public int getWavesLost() {
        return wavesLost;
    }

    public int getTotalGoldEarned() {
        return totalGoldEarned;
    }

    public int getTotalGoldSpent() {
        return totalGoldSpent;
    }

    public void setMobKills(int mobKills) {
        this.mobKills = mobKills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setWavesPlayed(int wavesPlayed) {
        this.wavesPlayed = wavesPlayed;
    }

    public void setWavesWon(int wavesWon) {
        this.wavesWon = wavesWon;
    }

    public void setWavesLost(int wavesLost) {
        this.wavesLost = wavesLost;
    }

    public void setTotalGoldEarned(int totalGoldEarned) {
        this.totalGoldEarned = totalGoldEarned;
    }

    public void setTotalGoldSpent(int totalGoldSpent) {
        this.totalGoldSpent = totalGoldSpent;
    }

    public int getStat(Stat stat) {

        switch (stat) {
            case DEATHS:
                return getDeaths();
            case GAMESPLAYED:
                return getGamesPlayed();
            case MOBKILLS:
                return getMobKills();
            case TOTALGOLDEARNED:
                return getTotalGoldEarned();
            case TOTALGOLDSPENT:
                return getTotalGoldSpent();
            case WAVESLOST:
                return getWavesLost();
            case WAVESPLAYED:
                return getWavesPlayed();
            case WAVESWON:
                return getWavesWon();
        }

        return -1;
    }

    public void setStat(Stat stat, int i) {
        switch (stat) {
            case DEATHS:
                setDeaths(i);
            case GAMESPLAYED:
                setGamesPlayed(i);
            case MOBKILLS:
                setMobKills(i);
            case TOTALGOLDEARNED:
                setTotalGoldEarned(i);
            case TOTALGOLDSPENT:
                setTotalGoldSpent(i);
            case WAVESLOST:
                setWavesLost(i);
            case WAVESPLAYED:
                setWavesPlayed(i);
            case WAVESWON:
                setWavesWon(i);
        }
    }
}
