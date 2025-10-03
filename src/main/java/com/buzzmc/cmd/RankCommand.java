package com.buzzmc.cmd;

import com.buzzmc.BuzzCore;
import com.buzzmc.rank.Rank;
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
                Rank rank = Rank.fromString(rankStr);
                if (rank == null || !plugin.ranks().ladder().contains(rank)) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.invalidRank")
                            .replace("%valid%", plugin.ranks().validRanksString())));
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target == null || target.getUniqueId() == null) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.playerNotFound")));
                    return true;
                }
                // Use toLuckPermsGroup() to convert to lowercase
                boolean ok = plugin.permissions().setPrimaryGroup(target.getUniqueId(), rank.toLuckPermsGroup());
                if (ok) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString("messages.rankSet")
                            .replace("%player%", target.getName() == null ? "player" : target.getName())
                            .replace("%rank%", rank.name())));
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
                Rank current = Rank.fromString(currentGroup);
                if (current == null) current = Rank.NEWPLAYER;

                Optional<Rank> dest = sub.equals("next") ? plugin.ranks().next(current) : plugin.ranks().prev(current);
                if (dest.isEmpty()) {
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString(sub.equals("next") ? "messages.noNext" : "messages.noPrev")));
                    return true;
                }
                // Use toLuckPermsGroup() to convert to lowercase
                boolean ok = plugin.permissions().setPrimaryGroup(target.getUniqueId(), dest.get().toLuckPermsGroup());
                if (ok) {
                    String msgKey = sub.equals("next") ? "messages.rankNext" : "messages.rankPrev";
                    sender.sendMessage(Colors.color(plugin.prefix() + plugin.getConfig().getString(msgKey)
                            .replace("%player%", target.getName() == null ? "player" : target.getName())
                            .replace("%rank%", dest.get().name())));
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
            return Arrays.stream(com.buzzmc.rank.Rank.values()).map(Enum::name).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
