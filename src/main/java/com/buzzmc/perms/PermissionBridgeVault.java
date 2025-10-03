package com.buzzmc.perms;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PermissionBridgeVault implements PermissionBridge {
    private final Permission perms;

    public PermissionBridgeVault(Permission perms) {
        this.perms = perms;
    }

    private OfflinePlayer off(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Override
    public boolean ensureDefaultGroup(UUID uuid, String groupId) {
        String[] groups = perms.getPlayerGroups(null, off(uuid));
        if (groups == null || groups.length == 0) {
            return perms.playerAddGroup(null, off(uuid), groupId);
        }
        return false;
    }

    @Override
    public boolean setPrimaryGroup(UUID uuid, String groupId) {
        String[] groups = perms.getPlayerGroups(null, off(uuid));
        if (groups != null) {
            for (String g : groups) {
                if (!g.equalsIgnoreCase(groupId)) {
                    perms.playerRemoveGroup(null, off(uuid), g);
                }
            }
        }
        return perms.playerAddGroup(null, off(uuid), groupId);
    }

    @Override
    public String getPrimaryGroup(UUID uuid) {
        String[] groups = perms.getPlayerGroups(null, off(uuid));
        if (groups == null || groups.length == 0) return "";
        return groups[0] == null ? "" : groups[0];
    }
}