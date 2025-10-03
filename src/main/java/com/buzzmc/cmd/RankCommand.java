package com.buzzmc.cmd;

import com.buzzmc.BuzzCore;
import com.buzzmc.util.Colors;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class RankCommand implements TabExecutor {
    private final BuzzCore plugin;

    public RankCommand(BuzzCore plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Colors.color(plugin.prefix() + "&7Usage: /rank <set|next|prev|info> [player] [rank]"));
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "info": {
                if (args.length < 2) {
                    sender.sendMessage(Colors.color(plugin.prefix() + "&c/rank info <player>"));
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target == null || (target.getUniqueId() == null)) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.playerNotFound")));
                    return true;
                }
                if (plugin.permissions() == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.noLuckVault")));
                    return true;
                }
                String group = plugin.permissions().getPrimaryGroup(target.getUniqueId());
                sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.rankInfo")
                        .replace("%player%", target.getName() == null ? "player" : target.getName())
                        .replace("%rank%", group == null ? "" : group)));
                return true;
            }
            case "set": {
                if (args.length < 3) {
                    sender.sendMessage(Colors.color(plugin.prefix() + "&c/rank set <player> <rank>"));
                    return true;
                }
                if (plugin.permissions() == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.noLuckVault")));
                    return true;
                }
                String rankStr = args[2];
                // Validate rank exists in config
                if (!plugin.ranks().isValidRank(rankStr)) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.invalidRank")
                            .replace("%valid%", plugin.ranks().validRanksString())));
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target == null || target.getUniqueId() == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.playerNotFound")));
                    return true;
                }
                // Get exact rank name from config (preserves casing)
                String normalizedRank = plugin.ranks().normalizeRank(rankStr);
                boolean ok = plugin.permissions().setPrimaryGroup(target.getUniqueId(), normalizedRank);
                if (ok) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.rankSet")
                            .replace("%player%", target.getName() == null ? "player" : target.getName())
                            .replace("%rank%", normalizedRank)));
                } else {
                    sender.sendMessage(Colors.color(plugin.prefix() + "&cFailed to set rank."));
                }
                return true;
            }
            case "next":
            case "prev": {
                if (args.length < 2) {
                    sender.sendMessage(Colors.color(plugin.prefix() + "&c/rank " + sub + " <player>"));
                    return true;
                }
                if (plugin.permissions() == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.noLuckVault")));
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target == null || target.getUniqueId() == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.playerNotFound")));
                    return true;
                }
                String currentGroup = plugin.permissions().getPrimaryGroup(target.getUniqueId());
                
                // If player has no rank or invalid rank, use first rank in ladder
                String current = currentGroup;
                if (!plugin.ranks().isValidRank(current)) {
                    current = plugin.ranks().ladder().isEmpty() ? null : plugin.ranks().ladder().get(0);
                }
                
                if (current == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + "&cNo ranks configured in config.yml!"));
                    return true;
                }

                Optional<String> dest = sub.equals("next") ? plugin.ranks().next(current) : plugin.ranks().prev(current);
                if (dest.isEmpty()) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString(sub.equals("next") ? "messages.noNext" : "messages.noPrev")));
                    return true;
                }
                
                boolean ok = plugin.permissions().setPrimaryGroup(target.getUniqueId(), dest.get());
                if (ok) {
                    String msgKey = sub.equals("next") ? "messages.rankNext" : "messages.rankPrev";
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString(msgKey)
                            .replace("%player%", target.getName() == null ? "player" : target.getName())
                            .replace("%rank%", dest.get())));
                } else {
                    sender.sendMessage(Colors.color(plugin.prefix() + "&cFailed to update rank."));
                }
                return true;
            }
            default:
                sender.sendMessage(Colors.color(plugin.prefix() + "&cUnknown subcommand."));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("set","next","prev","info").stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("set")
                || args[0].equalsIgnoreCase("next") || args[0].equalsIgnoreCase("prev"))) {
            return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            // Tab complete with ranks from config
            return plugin.ranks().ladder();
        }
        return Collections.emptyList();
    }
}
