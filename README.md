# BuzzCore

A Paper plugin for managing player ranks with automatic group assignment and rank progression.

## Features
- 🎯 Auto-assigns default group to first-time joiners
- 🎮 `/rank` command for managing player ranks
- 🔧 Supports LuckPerms (preferred) and Vault fallback
- 📊 Configurable rank ladder system
- 🔄 Hot-reload configuration with `/buzzreload`
- 🛡️ Vulcan anti-cheat integration ready

## Rank Ladder
```
BUZZER (newplayer) → COACH (buzzcoach) → TRIAL_MOD → CO_MOD → MOD → TRIAL_ADMIN → CO_ADMIN → ADMIN (buzzadmin) → OWNER (buzzowner)
```

**Display Names vs Group Names:**
- **BUZZER** = `newplayer` group
- **COACH** = `buzzcoach` group  
- **ADMIN** = `buzzadmin` group
- **OWNER** = `buzzowner` group

## Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/rank set <player> <rank>` | Set a player's rank | `buzz.rank` |
| `/rank next <player>` | Promote to next rank | `buzz.rank` |
| `/rank prev <player>` | Demote to previous rank | `buzz.rank` |
| `/rank info <player>` | View player's current rank | `buzz.rank` |
| `/buzzreload` | Reload configuration | `buzz.admin` |

## Command Examples
```
/rank set oliver54543 BUZZOWNER
/rank set PlayerName NEWPLAYER
/rank next PlayerName
/rank prev PlayerName
/rank info PlayerName
```

## Requirements
- Paper 1.21.1+
- Java 21
- LuckPerms or Vault (for permissions)
- (Optional) Vulcan for anti-cheat integration

## Installation
1. Download the latest release
2. Place `BuzzCore.jar` in your `plugins/` folder
3. Ensure LuckPerms or Vault is installed
4. Restart server
5. Configure `plugins/BuzzCore/config.yml` as needed

## Building from Source
```bash
git clone https://github.com/Omoli822/BuzzCore.git
cd BuzzCore
./gradlew clean build
```
Jar will be located at `build/libs/BuzzCore.jar`

## Configuration
All configuration options are in `config.yml`:

```yaml
# Plugin prefix for messages
prefix: "&6&lBUZZ &7» "

# Auto-assign new players to default group
autoAssignDefault: true
defaultGroup: "newplayer"

# Rank ladder (low to high) - fully customizable
ranks:
  - NEWPLAYER
  - BUZZCOACH
  - TRIAL_MOD
  - CO_MOD
  - MOD
  - TRIAL_ADMIN
  - CO_ADMIN
  - BUZZADMIN
  - BUZZOWNER

# Customize all plugin messages
messages:
  assignedDefault: "&aWelcome! You've been placed in the &f%newplayer% &agroup."
  alreadyInDefault: "&7You're already in the default group."
  # ... and more
```

## Permissions
- `buzz.rank` - Use /rank commands (default: op)
- `buzz.admin` - Admin commands like /buzzreload (default: op)

## LuckPerms/Vault Notes
- **Commands use UPPERCASE**: `NEWPLAYER`, `BUZZCOACH`, `BUZZOWNER`, etc.
- **LuckPerms groups use lowercase**: `newplayer`, `buzzcoach`, `buzzowner`, etc.
- The plugin automatically converts between the two formats

## Project Structure
```
BuzzCore/
├── src/main/java/com/buzzmc/
│   ├── BuzzCore.java           # Main plugin class
│   ├── cmd/
│   │   └── RankCommand.java    # /rank command handler
│   ├── perms/
│   │   ├── PermissionBridge.java
│   │   ├── PermissionBridgeLuckPerms.java
│   │   └── PermissionBridgeVault.java
│   ├── rank/
│   │   ├── Rank.java           # Rank enumeration
│   │   └── RankService.java    # Rank progression logic
│   ├── util/
│   │   └── Colors.java         # Chat color utilities
│   └── vulcan/
│       └── VulcanHook.java     # Anti-cheat integration stub
└── src/main/resources/
    ├── plugin.yml              # Plugin metadata
    └── config.yml              # Configuration file
```

## Future Enhancements
- Vulcan anti-cheat actions integration
- Permission-based rank restrictions
- Database support for rank history
- Custom rank prefixes and suffixes
- Rank expiration/temporary ranks

## License
MIT License - See [LICENSE](LICENSE) file for details.

## Support
For issues or questions, please open an issue on GitHub.
