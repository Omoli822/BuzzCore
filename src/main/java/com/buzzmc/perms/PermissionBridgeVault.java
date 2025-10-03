package com.buzzmc.perms;

import net.milkbowl.vault.permission.Permission;

import java.util.UUID;

public class PermissionBridgeVault implements PermissionBridge {
    private final Permission perms;

    public PermissionBridgeVault(Permission perms) { this.perms = perms; }

    @Override
    public boolean ensureDefaultGroup(UUID uuid, String groupId) {
        String current = getPrimaryGroup(uuid);
        if (current == null || current.isEmpty()) {
            // Vault doesn't have a strict "primary"; add to group
            return perms.playerAddGroup(null, uuid, groupId);
        }
        return false;
    }

    @Override
    public boolean setPrimaryGroup(UUID uuid, String groupId) {
        // Simulate "primary" by clearing other groups (best-effort)
        String[] groups = perms.getPlayerGroups(null, uuid);
        if (groups != null) {
            for (String g : groups) {
                perms.playerRemoveGroup(null, uuid, g);
            }
        }
        return perms.playerAddGroup(null, uuid, groupId);
    }

    @Override
    public String getPrimaryGroup(UUID uuid) {
        String[] groups = perms.getPlayerGroups(null, uuid);
        if (groups == null || groups.length == 0) return "";
        // Treat the first as primary
        return groups[0];
    }
}
