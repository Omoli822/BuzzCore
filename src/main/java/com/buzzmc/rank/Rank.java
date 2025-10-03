package com.buzzmc.rank;

public enum Rank {
    NEWPLAYER,
    BUZZCOACH,
    TRIAL_MOD,
    CO_MOD,
    MOD,
    TRIAL_ADMIN,
    CO_ADMIN,
    ADMIN,
    BUZZOWNER;

    public static Rank fromString(String s) {
        if (s == null) return null;
        try {
            return Rank.valueOf(s.trim().toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
    
    // Convert to LuckPerms group name (lowercase)
    public String toLuckPermsGroup() {
        return this.name().toLowerCase();
    }
}
