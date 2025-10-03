package com.buzzmc.rank;

public enum Rank {
    newplayer,
    buzzcoach,
    trial_mod,
    co_mod,
    mod,
    trial_admin,
    co_admin,
    buzzadmin,
    buzzowner;

    public static Rank fromString(String s) {
        if (s == null) return null;
        try {
            return Rank.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
