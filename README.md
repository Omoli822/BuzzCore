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
BUZZER (newplayer) → COACH (buzzcoach) → TRIAL_MOD → CO_MOD → MOD → TRIAL_ADMIN → CO_ADMIN → ADMIN (admin) → OWNER (buzzowner)
```

**Display Names vs LuckPerms Groups:**
- **BUZZER** = `newplayer` group in LuckPerms
- **COACH** = `buzzcoach` group in LuckPerms
- **ADMIN** = `admin` group in LuckPerms
- **OWNER** = `buzzowner` group in LuckPerms

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
/rank set PlayerName BUZZCOACH
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
4. Create the following groups in LuckPerms (all lowercase):
   - `newplayer`, `buzzcoach`, `trial_mod`, `co_mod`, `mod`, `trial_admin`, `co_admin`, `admin`, `buzzowner`
5. Restart server
6. Configure `plugins/BuzzCore/config.yml` as needed

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
# Use UPPERCASE in config (will be converted to lowercase for LuckPerms)
ranks:
  - NEWPLAYER      # Maps to 'newplayer' in LuckPerms
  - BUZZCOACH      # Maps to 'buzzcoach' in LuckPerms
  - TRIAL_MOD      # Maps to 'trial_mod' in LuckPerms
  - CO_MOD         # Maps to 'co_mod' in LuckPerms
  - MOD            # Maps to 'mod' in LuckPerms
  - TRIAL_ADMIN    # Maps to 'trial_admin' in LuckPerms
  - CO_ADMIN       # Maps to 'co_admin' in LuckPerms
  - ADMIN          # Maps to 'admin' in LuckPerms
  - BUZZOWNER      # Maps to 'buzzowner' in LuckPerms

# Customize all plugin messages
messages:
  assignedDefault: "&aWelcome! You've been placed in the &f%newplayer% &agroup."
  alreadyInDefault: "&7You're already in the default group."
  # ... and more
```

## Permissions
- `buzz.rank` - Use /rank commands (default: op)
- `buzz.admin` - Admin commands like /buzzreload (default: op)

## How It Works

### Command Usage (UPPERCASE):
When you type commands, use **UPPERCASE** rank names:
- `/rank set player BUZZOWNER` ✅
- `/rank set player NEWPLAYER` ✅
- `/rank set player TRIAL_MOD` ✅

### LuckPerms Groups (lowercase):
In LuckPerms, create groups with **lowercase** names:
- `newplayer` (not NEWPLAYER)
- `buzzowner` (not BUZZOWNER)
- `trial_mod` (not TRIAL_MOD)

The plugin automatically converts between the two formats!

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
│   │   ├── Rank.java           # Rank enumeration with conversion
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
