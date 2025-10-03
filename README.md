# BuzzCore - Community Edition

**🎉 Use ANY rank names you want! No hardcoded restrictions!**

A flexible Paper plugin for managing player ranks with automatic group assignment and rank progression. Perfect for server owners who want full control over their rank names.

## 🌟 Community Edition Features

### ✨ Fully Customizable Rank Names
- **No hardcoded ranks!** Use whatever names you want
- Works with your existing LuckPerms/Vault groups
- Preserves exact casing from config
- Case-insensitive commands for convenience

### Example Rank Setups

**Medieval Server:**
```yaml
ranks:
  - peasant
  - citizen
  - noble
  - knight
  - lord
  - king
```

**Modern Server:**
```yaml
ranks:
  - newbie
  - member
  - vip
  - mvp
  - moderator
  - admin
  - owner
```

**RPG Server:**
```yaml
ranks:
  - wanderer
  - adventurer
  - hero
  - legend
  - mythic
```

**Your Server:**
```yaml
ranks:
  - literally
  - any
  - names
  - you
  - want
```

## 🎮 Features

- 🎯 Auto-assigns default group to first-time joiners
- 🎮 `/rank` command for managing player ranks
- 🔧 Supports LuckPerms (preferred) and Vault fallback
- 📊 Fully config-driven rank ladder
- 🔄 Hot-reload configuration with `/buzzreload`
- 🛡️ Vulcan anti-cheat integration ready
- 💪 Case-insensitive rank matching
- 🎨 Use ANY rank names you want!

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/rank set <player> <rank>` | Set a player's rank | `buzz.rank` |
| `/rank next <player>` | Promote to next rank | `buzz.rank` |
| `/rank prev <player>` | Demote to previous rank | `buzz.rank` |
| `/rank info <player>` | View player's current rank | `buzz.rank` |
| `/buzzreload` | Reload configuration | `buzz.admin` |

## Command Examples

Works with whatever ranks you configure!

```bash
# If you have medieval ranks:
/rank set PlayerName king
/rank set PlayerName peasant

# If you have standard ranks:
/rank set PlayerName owner
/rank set PlayerName member

# If you have custom ranks:
/rank set PlayerName YourCustomRank

# Promote/demote through your ladder:
/rank next PlayerName
/rank prev PlayerName
```

## Requirements

- Paper 1.21.1+
- Java 21
- LuckPerms or Vault (for permissions)
- (Optional) Vulcan for anti-cheat integration

## Installation

1. Download `BuzzCore.jar`
2. Place in your `plugins/` folder
3. Install LuckPerms or Vault
4. Start your server (creates default config.yml)
5. Edit `plugins/BuzzCore/config.yml` with YOUR rank names
6. Create matching groups in LuckPerms (exact same names!)
7. `/buzzreload` or restart server
8. Enjoy!

## Configuration

```yaml
# BuzzCore Community Edition Configuration
# You can use ANY rank names you want!
prefix: "&6&lBUZZ &7» "
autoAssignDefault: true

# Set this to the first rank in your ladder
defaultGroup: "default"

# Rank ladder (low to high)
# Use the exact same names as your LuckPerms/Vault groups!
ranks:
  - default
  - member
  - vip
  - mvp
  - admin
  - owner

messages:
  # All messages are customizable!
  assignedDefault: "&aWelcome! You've been placed in the &f%default% &agroup."
  rankSet: "&a%player%'s rank set to &e%rank%&a."
  # ... and more
```

### Important Notes

✅ **Rank names in config.yml must EXACTLY match your LuckPerms group names!**
- Config: `owner` → LuckPerms group: `owner` ✅
- Config: `Owner` → LuckPerms group: `owner` ❌ (case mismatch)

✅ **Commands are case-insensitive:**
- `/rank set player owner` works
- `/rank set player OWNER` also works
- `/rank set player OwNeR` also works

✅ **The plugin will use the exact casing from your config when setting groups**

## Building from Source

```bash
git clone https://github.com/Omoli822/BuzzCore.git
cd BuzzCore
git checkout community-edition
./gradlew clean build
```

Jar will be located at `build/libs/BuzzCore.jar`

## Permissions

- `buzz.rank` - Use /rank commands (default: op)
- `buzz.admin` - Use /buzzreload and admin functions (default: op)

## Differences from Standard Edition

| Feature | Standard Edition | Community Edition |
|---------|-----------------|-------------------|
| Rank Names | Hardcoded (NEWPLAYER, BUZZCOACH, etc.) | Fully customizable |
| Configuration | Predefined ranks | Use any names you want |
| LuckPerms Groups | Must match specific names | Match your config exactly |
| Flexibility | Fixed for Buzz server | Works for any server |
| Ease of Use | Plug and play for Buzz | Requires configuration |

## Support

Need help? Have questions?
- Open an issue on GitHub
- Check the README.md for documentation
- Review the config.yml comments

## Future Enhancements

- [ ] Vulcan anti-cheat violation actions
- [ ] Permission-based rank restrictions
- [ ] Database support for rank history
- [ ] Custom rank prefixes and suffixes
- [ ] Temporary/expiring ranks
- [ ] Rank-up requirements system
- [ ] Multi-language support
- [ ] PlaceholderAPI integration

## License

BuzzCore is licensed under the MIT License.

## Credits

**Developed by:** Omoli822  
**Built with:** Paper API, LuckPerms API, Vault API  
**Special thanks to:** The Paper, LuckPerms, and Spigot communities

---

**Thank you for using BuzzCore Community Edition!** 🚀

*Your ranks, your way.*
