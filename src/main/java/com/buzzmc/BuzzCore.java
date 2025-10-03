package com.buzzmc;

import com.buzzmc.cmd.RankCommand;
import com.buzzmc.perms.PermissionBridge;
import com.buzzmc.perms.PermissionBridgeLuckPerms;
import com.buzzmc.perms.PermissionBridgeVault;
import com.buzzmc.rank.RankService;
import com.buzzmc.util.Colors;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuzzCore extends JavaPlugin implements Listener {

    private PermissionBridge permissionBridge;
    private RankService rankService;
    private String prefix;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadLocal();

        // Detect LuckPerms first
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            getLogger().info("LuckPerms detected. Using LuckPerms API.");
            this.permissionBridge = new PermissionBridgeLuckPerms(this);
        } else {
            // Fallback to Vault (permissions)
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> rsp =
                        getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
                if (rsp != null) {
                    getLogger().info("Vault permissions detected. Using Vault bridge.");
                    this.permissionBridge = new PermissionBridgeVault(rsp.getProvider());
                }
            }
        }

        if (this.permissionBridge == null) {
            getLogger().warning("No LuckPerms or Vault permissions provider found. Rank features will be disabled.");
        }

        this.rankService = new RankService(this);

        // Commands
        getCommand("rank").setExecutor(new RankCommand(this));
        getCommand("rank").setTabCompleter(new RankCommand(this));
        getCommand("buzzreload").setExecutor((sender, cmd, label, args) -> {
            reloadLocal();
            sender.sendMessage(Colors.color(prefix + getConfig().getString("messages.reloaded")));
            return true;
        });

        // Events
        getServer().getPluginManager().registerEvents(this, this);

        // Optional: Vulcan hook stub (safe no-op if not present)
        if (Bukkit.getPluginManager().getPlugin("Vulcan") != null) {
            getLogger().info("Vulcan detected. (Hook stub active — add actions in VulcanHook.java)");
        }
    }

    private void reloadLocal() {
        reloadConfig();
        FileConfiguration c = getConfig();
        this.prefix = c.getString("prefix", "&6&lBUZZ &7» ");
        if (!this.prefix.endsWith(" ")) this.prefix = this.prefix + " ";
    }

    @Override
    public void onDisable() {
        // nothing
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!getConfig().getBoolean("autoAssignDefault", true)) return;
        if (permissionBridge == null) {
            e.getPlayer().sendMessage(Colors.color(prefix + getConfig().getString("messages.noLuckVault")));
            return;
        }

        String defaultGroup = getConfig().getString("defaultGroup", "newplayer");
        boolean assigned = permissionBridge.ensureDefaultGroup(e.getPlayer().getUniqueId(), defaultGroup);
        if (assigned) {
            e.getPlayer().sendMessage(Colors.color(prefix + getConfig().getString("messages.assignedDefault")
                    .replace("%newplayer%", defaultGroup)));
        } else {
            e.getPlayer().sendMessage(Colors.color(prefix + getConfig().getString("messages.alreadyInDefault")));
        }
    }

    public PermissionBridge permissions() { return permissionBridge; }
    public RankService ranks() { return rankService; }
    public String prefix() { return prefix; }
}
