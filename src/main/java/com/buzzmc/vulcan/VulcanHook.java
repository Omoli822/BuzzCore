package com.buzzmc.vulcan;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class VulcanHook {
    private VulcanHook() {}

    public static boolean isPresent() {
        Plugin p = Bukkit.getPluginManager().getPlugin("Vulcan");
        return p != null && p.isEnabled();
    }

    // Add listeners to Vulcan's API here when you're ready.
    // Keep this class so BuzzCore compiles even if Vulcan isn't installed.
}
