package repo.binarydctr.duel;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * ******************************************************************
 * Copyright BinaryDctr (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of BinaryDctr. Distribution, reproduction, taking snippets, or
 * claiming any contents as your will break the terms of the license, and void any
 * agreements with you, the third party.
 * ******************************************************************
 **/
public class DuelCommand implements CommandExecutor {

    Core core;

    public DuelCommand(Core core) {

        this.core = core;

    }

    Map<Player, Player> duel = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if(args.length == 0) {
                return true;
            }

            if(args.length == 1) {
                Player player1 = Bukkit.getPlayer(args[0]);
                if(player1 != null) {
                    if(player1 != player) {
                        if (player1.isOnline()) {
                            if (!duel.containsKey(player)) {
                                if (!core.getPlayers().containsKey(player1)) {
                                    sendDuelRequest(player, player1);
                                } else {
                                    player.sendMessage(core.prefix + core.highlight(player1.getName()) + " is currently dueling.");
                                }
                            } else {
                                startDuel(player1, player);
                            }
                        }
                    } else {
                        player.sendMessage(core.prefix + "You cannot duel your self.");
                    }
                }

                FileConfiguration config = core.getConfig();

                if(args[0].equalsIgnoreCase("setspawn1")) {
                    config.set("Arena.Spawn1.X", player.getLocation().getX());
                    config.set("Arena.Spawn1.Y", player.getLocation().getY());
                    config.set("Arena.Spawn1.Z", player.getLocation().getZ());
                    config.set("Arena.Spawn1.Pitch", player.getLocation().getPitch());
                    config.set("Arena.Spawn1.Yaw", player.getLocation().getYaw());
                    config.set("Arena.Spawn1.World", player.getLocation().getWorld().getName());
                    core.saveConfig();
                }

                if(args[0].equalsIgnoreCase("setspawn2")) {
                    config.set("Arena.Spawn2.X", player.getLocation().getX());
                    config.set("Arena.Spawn2.Y", player.getLocation().getY());
                    config.set("Arena.Spawn2.Z", player.getLocation().getZ());
                    config.set("Arena.Spawn2.Pitch", player.getLocation().getPitch());
                    config.set("Arena.Spawn2.Yaw", player.getLocation().getYaw());
                    config.set("Arena.Spawn2.World", player.getLocation().getWorld().getName());
                    core.saveConfig();
                }
            }
        }

        return false;
    }

    public void sendDuelRequest(final Player challenger, final Player challenged) {

        duel.put(challenger, challenged);
        duel.put(challenged, challenger);
        challenger.sendMessage(core.prefix + "You requested a duel with " + core.highlight(challenged.getName()) + ".");
        challenged.sendMessage(core.prefix + core.highlight(challenger.getName()) + " requested a duel with you, type /duel " + core.highlight(challenger.getName()) + " to start, you have 30 seconds to respond.");
        Bukkit.getScheduler().scheduleSyncDelayedTask(core, new Runnable() {
            @Override
            public void run() {
                if(duel.containsKey(challenged) && duel.containsKey(challenger)) {
                    duel.remove(challenger);
                    duel.remove(challenged);
                    challenger.sendMessage(core.prefix + core.highlight(challenged.getName()) + " didn't respond.");
                    challenged.sendMessage(core.prefix + core.highlight(challenger.getName() + "'s") +  " duel request has expired.");
                }
            }
        }, 600L);

    }

    public void startDuel(Player challenger, Player challenged) {

        duel.remove(challenger);
        duel.remove(challenged);
        core.getLocation().put(challenged, challenged.getLocation());
        core.getLocation().put(challenger, challenger.getLocation());
        core.getPlayers().put(challenger, challenged);
        core.getPlayers().put(challenged, challenger);
        challenger.sendMessage(core.prefix + "The duel with " + core.highlight(challenged.getName()) + " has begun.");
        challenged.sendMessage(core.prefix + "The duel with " + core.highlight(challenger.getName()) + " has begun.");
        challenged.setHealth(20);
        challenger.setHealth(20);
        challenged.setFoodLevel(20);
        challenger.setFoodLevel(20);
        challenger.teleport(core.getSpawn1());
        challenged.teleport(core.getSpawn2());
        for(Player players : Bukkit.getOnlinePlayers()) {
            if(players != challenged && players != challenger) {
                challenged.hidePlayer(players);
                challenger.hidePlayer(players);
            }
        }

    }

}
