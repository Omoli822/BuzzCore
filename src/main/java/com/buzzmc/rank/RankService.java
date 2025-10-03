package com.buzzmc.rank;

import com.buzzmc.BuzzCore;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class RankService {
    private final BuzzCore plugin;
    private final List<String> ladder;

    public RankService(BuzzCore plugin) {
        this.plugin = plugin;
        FileConfiguration c = plugin.getConfig();
        List<String> list = c.getStringList("ranks");
        if (list == null || list.isEmpty()) {
            // Default fallback ladder
            ladder = Arrays.asList("default", "member", "vip", "mvp", "admin", "owner");
            plugin.getLogger().warning("No ranks found in config.yml! Using default ladder.");
        } else {
            // Store ranks exactly as they appear in config (preserve case)
            ladder = new ArrayList<>(list);
        }
    }

    public List<String> ladder() {
        return Collections.unmodifiableList(ladder);
    }

    public Optional<String> next(String current) {
        // Case-insensitive search
        int idx = -1;
        for (int i = 0; i < ladder.size(); i++) {
            if (ladder.get(i).equalsIgnoreCase(current)) {
                idx = i;
                break;
            }
        }
        if (idx == -1 || idx + 1 >= ladder.size()) return Optional.empty();
        return Optional.of(ladder.get(idx + 1));
    }

    public Optional<String> prev(String current) {
        // Case-insensitive search
        int idx = -1;
        for (int i = 0; i < ladder.size(); i++) {
            if (ladder.get(i).equalsIgnoreCase(current)) {
                idx = i;
                break;
            }
        }
        if (idx <= 0) return Optional.empty();
        return Optional.of(ladder.get(idx - 1));
    }

    public boolean isValidRank(String s) {
        if (s == null) return false;
        for (String rank : ladder) {
            if (rank.equalsIgnoreCase(s)) return true;
        }
        return false;
    }

    public String validRanksString() {
        return String.join(", ", ladder);
    }

    public String normalizeRank(String input) {
        // Find the rank in ladder and return the exact casing from config
        for (String rank : ladder) {
            if (rank.equalsIgnoreCase(input)) {
                return rank;
            }
        }
        return null;
    }
}
