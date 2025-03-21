package com.andrei1058.bedwars.team;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Team;

public class TeamCommandExecutor implements CommandExecutor {

    private final TeamStorageManager teamStorageManager;

    public TeamCommandExecutor(TeamStorageManager teamStorageManager) {
        this.teamStorageManager = teamStorageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("saveteam")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /saveteam <team_name> <storage_key>");
                return true;
            }

            String teamName = args[0];
            String storageKey = args[1];

            if (!teamStorageManager.saveTeam(teamName, storageKey)) {
                sender.sendMessage(ChatColor.RED + "Failed to save team. Check team name or if it already exists with this key.");
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + "Team '" + teamName + "' saved as '" + storageKey + "'.");
            return true;
        } else if (command.getName().equalsIgnoreCase("getteam")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /getteam <storage_key>");
                return true;
            }

            String storageKey = args[0];
            Team team = teamStorageManager.getTeam(storageKey);

            if (team == null) {
                sender.sendMessage(ChatColor.RED + "No team found with key '" + storageKey + "'.");
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + "Team '" + storageKey + "' retrieved: " + team.getName());
            sender.sendMessage(ChatColor.GREEN + "Team Prefix: " + team.getPrefix());
            sender.sendMessage(ChatColor.GREEN + "Team size: " + team.getEntries().size());

            return true;
        } else if (command.getName().equalsIgnoreCase("createteam")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /createteam <team_name> <storage_key>");
                return true;
            }
            String teamName = args[0];
            String storageKey = args[1];

            if (!teamStorageManager.createAndSaveTeam(teamName, storageKey)) {
                sender.sendMessage(ChatColor.RED + "Failed to create and save team. Check team name or if it already exists.");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Team '" + teamName + "' created and saved as '" + storageKey + "'.");

            return true;
        }

        return false;
    }
}