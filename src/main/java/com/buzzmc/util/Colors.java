package com.buzzmc.util;

import org.bukkit.ChatColor;

public final class Colors {
    private Colors() {}

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s == null ? "" : s);
    }
}
