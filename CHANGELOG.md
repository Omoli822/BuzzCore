# BuzzCore v3.0.0 - Patch Notes
**Release Date:** October 3, 2025

---

## 🎉 Initial Release (v3.0.0)

BuzzCore is a comprehensive rank management plugin for Paper servers with seamless LuckPerms and Vault integration.

---

## ✨ Features

### Rank Management System
- **Automatic Group Assignment** - New players are automatically assigned to the default "newplayer" group
- **Rank Progression Commands** - Easily promote/demote players through your rank hierarchy
- **Multi-Permission System Support** - Works with both LuckPerms (preferred) and Vault as fallback
- **Hot-Reload Configuration** - Update settings without restarting your server

### Complete Rank Ladder
```
NEWPLAYER → BUZZCOACH → TRIAL_MOD → CO_MOD → MOD → TRIAL_ADMIN → CO_ADMIN → ADMIN → BUZZOWNER
```

### Commands
- `/rank set <player> <rank>` - Set a player's rank directly
- `/rank next <player>` - Promote player to next rank in ladder
- `/rank prev <player>` - Demote player to previous rank in ladder
- `/rank info <player>` - View a player's current rank
- `/buzzreload` - Reload configuration without restart

### Smart Rank Conversion
- **Command Format:** Use UPPERCASE rank names (e.g., `BUZZOWNER`, `TRIAL_MOD`)
- **LuckPerms Format:** Automatically converts to lowercase groups (e.g., `buzzowner`, `trial_mod`)
- **Seamless Integration:** No manual conversion needed!

---

## 🔧 Technical Details

### Supported Platforms
- **Minecraft Version:** 1.21.1+
- **Server Software:** Paper (and forks)
- **Java Version:** 21+
- **Dependencies:** LuckPerms or Vault (for permissions)

### Plugin Architecture
- **Permission Bridge System** - Modular design supporting multiple permission plugins
- **LuckPerms API Integration** - Native support with async operations
- **Vault Fallback** - Graceful degradation when LuckPerms unavailable
- **Event-Driven** - Efficient player join handling
- **Configuration-Driven** - Fully customizable rank ladder and messages

---

## 📋 Configuration

### Default Settings
```yaml
prefix: "&6&lBUZZ &7» "
autoAssignDefault: true
defaultGroup: "newplayer"

ranks:
  - NEWPLAYER
  - BUZZCOACH
  - TRIAL_MOD
  - CO_MOD
  - MOD
  - TRIAL_ADMIN
  - CO_ADMIN
  - ADMIN
  - BUZZOWNER
```

### Customizable Elements
- ✅ Plugin message prefix
- ✅ Auto-assignment toggle
- ✅ Default group name
- ✅ Complete rank ladder order
- ✅ All player-facing messages
- ✅ Color codes and formatting

---

## 🛡️ Future Integration Support

### Vulcan Anti-Cheat (Coming Soon)
- Integration stub included for future anti-cheat actions
- Prepared for automatic violation handling
- Safe compilation even without Vulcan installed

---

## 🐛 Bug Fixes

### Critical Fixes (Pre-Release)
- **Fixed:** Rank enum case sensitivity causing "invalid rank" errors
- **Fixed:** LuckPerms group name conversion (UPPERCASE → lowercase)
- **Fixed:** Default group reference using wrong constant
- **Fixed:** Rank ladder validation returning empty list
- **Fixed:** Message placeholder replacements for group names

### Improvements
- **Improved:** Tab completion for rank names
- **Improved:** Error messages now show valid rank list
- **Improved:** Permission bridge fallback system
- **Improved:** Config reload handling

---

## 📦 Installation

### Quick Start
1. Download `BuzzCore.jar`
2. Place in your `plugins/` folder
3. Install LuckPerms or Vault
4. Create groups in LuckPerms (lowercase):
   - `newplayer`, `buzzcoach`, `trial_mod`, `co_mod`, `mod`
   - `trial_admin`, `co_admin`, `admin`, `buzzowner`
5. Restart your server
6. Enjoy!

### Building from Source
```bash
git clone https://github.com/Omoli822/BuzzCore.git
cd BuzzCore
./gradlew clean build
```

---

## 🔐 Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `buzz.rank` | Use all /rank commands | op |
| `buzz.admin` | Use /buzzreload and admin functions | op |

---

## 💡 Usage Examples

```bash
# Set a player to owner rank
/rank set oliver54543 BUZZOWNER

# Promote a player one rank
/rank next PlayerName

# Demote a player one rank
/rank prev PlayerName

# Check a player's current rank
/rank info PlayerName

# Reload configuration
/buzzreload
```

---

## 📝 Known Issues

### Current Limitations
- Owner rank cannot be promoted beyond (by design)
- Newplayer rank cannot be demoted below (by design)
- Requires either LuckPerms or Vault for functionality
- No rank expiration/temporary ranks yet

---

## 🎯 Roadmap

### Planned Features (Future Updates)
- [ ] Vulcan anti-cheat violation actions
- [ ] Permission-based rank restrictions
- [ ] Database support for rank history
- [ ] Custom rank prefixes and suffixes
- [ ] Temporary/expiring ranks
- [ ] Rank-up requirements system
- [ ] Multi-language support
- [ ] PlaceholderAPI integration

---

## 🤝 Contributing

Found a bug or have a suggestion? Open an issue on GitHub!

**Repository:** https://github.com/Omoli822/BuzzCore

---

## 📄 License

BuzzCore is licensed under the MIT License.

---

## 🙏 Credits

**Developed by:** Omoli822  
**Built with:** Paper API, LuckPerms API, Vault API  
**Special thanks to:** The Paper, LuckPerms, and Spigot communities

---

## 📞 Support

Need help? Have questions?
- Open an issue on GitHub
- Check the README.md for documentation
- Review the config.yml comments

---

**Thank you for using BuzzCore!** 🚀

*Making rank management simple and powerful.*
