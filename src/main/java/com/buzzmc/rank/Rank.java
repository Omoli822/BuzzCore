package com.buzzmc.rank;

public enum Rank {
    newplayer,
    COACH,
    TRIAL_MOD,
    CO_MOD,
    MOD,
    TRIAL_ADMIN,
    CO_ADMIN,
    ADMIN;

    public static Rank fromString(String s) {
        if (s == null) return null;
        try {
            return Rank.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
