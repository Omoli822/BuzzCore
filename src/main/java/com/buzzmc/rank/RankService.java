package com.buzzmc.rank;

import com.buzzmc.BuzzCore;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

public class RankService {
    private final BuzzCore plugin;
    private final List<Rank> ladder;

    public RankService(BuzzCore plugin) {
        this.plugin = plugin;
        FileConfiguration c = plugin.getConfig();
        List<String> list = c.getStringList("ranks");
        if (list == null || list.isEmpty()) {
            ladder = Arrays.asList(Rank.values()); // fallback
        } else {
            ladder = list.stream()
                    .map(Rank::fromString)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

    public List<Rank> ladder() {
        return Collections.unmodifiableList(ladder);
    }

    public Optional<Rank> next(Rank current) {
        int idx = ladder.indexOf(current);
        if (idx == -1 || idx + 1 >= ladder.size()) return Optional.empty();
        return Optional.of(ladder.get(idx + 1));
    }

    public Optional<Rank> prev(Rank current) {
        int idx = ladder.indexOf(current);
        if (idx <= 0) return Optional.empty();
        return Optional.of(ladder.get(idx - 1));
    }

    public boolean isValidRank(String s) {
        return Rank.fromString(s) != null && ladder.contains(Rank.fromString(s));
    }

    public String validRanksString() {
        return ladder.stream().map(Enum::name).collect(Collectors.joining(", "));
    }
}
