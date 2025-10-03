package com.buzzmc.rank;

public enum Rank {
    NEWPLAYER,
    BUZZCOACH,
    TRIAL_MOD,
    CO_MOD,
    MOD,
    TRIAL_ADMIN,
    CO_ADMIN,
    BUZZADMIN,
    BUZZOWNER;

    public static Rank fromString(String s) {
        if (s == null) return null;
        try {
            return Rank.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
