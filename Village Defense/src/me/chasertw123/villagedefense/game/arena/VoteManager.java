package me.chasertw123.villagedefense.game.arena;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.chasertw123.villagedefense.Main;

import org.bukkit.entity.Player;

public class VoteManager {

    public HashMap<Player, Arena> votes = new HashMap<Player, Arena>();

    public void addVote(Player p, Arena a) {
        votes.put(p, a);
    }

    public Arena getArena(Main plugin) {
        TreeMap<Arena, ArenaCounter> counts = getArenaCounter(plugin);
        if (counts.size() != 0)
            return counts.firstKey();
        else
            return null;
    }

    public TreeMap<Arena, ArenaCounter> getArenaCounter(Main plugin) {
        HashMap<Arena, ArenaCounter> counts = new HashMap<Arena, ArenaCounter>();

        for (Arena a : votes.values()) {
            if (counts.containsKey(a))
                counts.get(a).incrementCount();
            else
                counts.put(a, new ArenaCounter().incrementCount());
        }

        ValueComparator bvc = new ValueComparator(counts);
        TreeMap<Arena, ArenaCounter> sorted_map = new TreeMap<Arena, ArenaCounter>(bvc);
        sorted_map.putAll(counts);

        for (Arena a : plugin.getArenas())
            if (!sorted_map.containsKey(a))
                sorted_map.put(a, new ArenaCounter());

        return sorted_map;
    }

    class ValueComparator implements Comparator<Arena> {

        Map<Arena, ArenaCounter> base;

        public ValueComparator(Map<Arena, ArenaCounter> base) {
            this.base = base;
        }

        public int compare(Arena a, Arena b) {
            if (a == null || b == null)
                return 1;
            else if (base.get(a) == null || base.get(b) == null)
                return 1;
            else if (base.get(a).getCount() <= base.get(b).getCount()) {
                return 1;
            } else {
                return -1;
            } // returning 0 would merge keys
        }
    }

    public class ArenaCounter {
        int count = 0;

        public ArenaCounter incrementCount() {
            count++;
            return this;
        }

        public int getCount() {
            return count;
        }
    }
}
