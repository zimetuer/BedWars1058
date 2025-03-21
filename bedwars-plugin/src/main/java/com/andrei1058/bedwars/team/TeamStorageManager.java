package com.andrei1058.bedwars.team;

import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.OfflinePlayer; // Import OfflinePlayer
import org.bukkit.Bukkit; // Import Bukkit

import java.util.HashMap;
import java.util.Map;

public class TeamStorageManager {

    private final Map<String, Team> storedTeams = new HashMap<>();
    private final Scoreboard scoreboard;
    private final Plugin plugin;

    public TeamStorageManager(Plugin plugin) {
        this.plugin = plugin;
        ScoreboardManager scoreboardManager = plugin.getServer().getScoreboardManager();
        if (scoreboardManager != null) {
            scoreboard = scoreboardManager.getMainScoreboard();
        } else {
            scoreboard = null;
            plugin.getLogger().severe("Scoreboard Manager is null! TeamStorageManager will not function correctly.");
        }
    }

    public boolean saveTeam(String teamName, String storageKey) {
        if (scoreboard == null) return false;

        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            return false;
        }
        if(storedTeams.containsKey(storageKey)){
            return false;
        }
        storedTeams.put(storageKey, team);
        return true;
    }

    public Team getTeam(String key) {
        return storedTeams.get(key);
    }

    public boolean createAndSaveTeam(String teamName, String storageKey) {
        if (scoreboard == null) return false;

        if (scoreboard.getTeam(teamName) != null) {
            return false; // Team with that name already exists.
        }
        if(storedTeams.containsKey(storageKey)){
            return false;
        }

        Team newTeam = scoreboard.registerNewTeam(teamName);
        storedTeams.put(storageKey, newTeam);
        return true;
    }

    public void clearStoredTeams() {
        storedTeams.clear();
    }

    public boolean addPlayerToTeam(String storageKey, String playerName) {
        if (scoreboard == null) return false;

        Team team = storedTeams.get(storageKey);
        if (team == null) {
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName); // Use OfflinePlayer to add even if the player is offline
        team.addEntry(player.getName()); //  Add by name!  Important!
        return true;
    }

    public boolean removePlayerFromTeam(String storageKey, String playerName) {
        if (scoreboard == null) return false;

        Team team = storedTeams.get(storageKey);
        if (team == null) {
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName); // Use OfflinePlayer
        team.removeEntry(player.getName()); // Remove by name! Important!
        return true;
    }
}