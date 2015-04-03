package me.chasertw123.villagedefense.game.arena;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.entity.Player;

public class VoteManager {
    public HashMap<Player, Arena> votes = new HashMap<Player, Arena>();

    public void addVote(Player p, Arena a) {
        votes.put(p, a);
    }

    public Arena getArena() {
        TreeMap<Arena, ArenaCounter> counts = getArenaCounter();
        if (counts.size() != 0)
            return counts.firstKey();
        else
            return null;
    }

    public TreeMap<Arena, ArenaCounter> getArenaCounter() {
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
        //TODO: Add maps with 0 votes.

        return sorted_map;
    }

    class ValueComparator implements Comparator<Arena> {

        Map<Arena, ArenaCounter> base;

        public ValueComparator(Map<Arena, ArenaCounter> base) {
            this.base = base;
        }

        public int compare(Arena a, Arena b) {
            if (base.get(a).getCount() <= base.get(b).getCount()) {
                return 1;
            } else {
                return -1;
            } // returning 0 would merge keys
        }
    }

    public class ArenaCounter {
        int count = 1;

        public ArenaCounter incrementCount() {
            count++;
            return this;
        }

        public int getCount() {
            return count;
        }
    }
}
