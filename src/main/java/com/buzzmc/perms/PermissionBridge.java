package com.buzzmc.perms;

import java.util.UUID;

public interface PermissionBridge {
    /** Ensure the player has the default/primary group; return true if newly assigned. */
    boolean ensureDefaultGroup(UUID uuid, String groupId);

    /** Set player's primary group (best-effort with Vault). */
    boolean setPrimaryGroup(UUID uuid, String groupId);

    /** Get player's primary group id (lowercase). */
    String getPrimaryGroup(UUID uuid);
}