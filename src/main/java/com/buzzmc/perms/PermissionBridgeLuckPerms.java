package com.buzzmc.perms;

import com.buzzmc.BuzzCore;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class PermissionBridgeLuckPerms implements PermissionBridge {
    private final LuckPerms lp;

    public PermissionBridgeLuckPerms(BuzzCore plugin) {
        this.lp = LuckPermsProvider.get();
    }

    @Override
    public boolean ensureDefaultGroup(UUID uuid, String groupId) {
        AtomicBoolean changed = new AtomicBoolean(false);
        lp.getUserManager().modifyUser(uuid, user -> {
            String primary = user.getPrimaryGroup();
            if (primary == null || primary.isEmpty()) {
                user.setPrimaryGroup(groupId);
                user.data().add(Node.builder("group." + groupId).build());
                changed.set(true);
            }
        });
        return changed.get();
    }

    @Override
    public boolean setPrimaryGroup(UUID uuid, String groupId) {
        AtomicBoolean ok = new AtomicBoolean(false);
        lp.getUserManager().modifyUser(uuid, user -> {
            user.setPrimaryGroup(groupId);
            user.data().add(Node.builder("group." + groupId).build());
            ok.set(true);
        });
        return ok.get();
    }

    @Override
    public String getPrimaryGroup(UUID uuid) {
        User user = lp.getUserManager().getUser(uuid);
        if (user == null) {
            try { user = lp.getUserManager().loadUser(uuid).join(); } catch (Exception ignored) {}
        }
        String g = (user == null ? "" : user.getPrimaryGroup());
        return g == null ? "" : g;
    }
}