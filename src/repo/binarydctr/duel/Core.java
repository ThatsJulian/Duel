package repo.binarydctr.duel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * ******************************************************************
 * Copyright BinaryDctr (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of BinaryDctr. Distribution, reproduction, taking snippets, or
 * claiming any contents as your will break the terms of the license, and void any
 * agreements with you, the third party.
 * ******************************************************************
 **/
public class Core extends JavaPlugin {

    public String prefix = ChatColor.WHITE + "[" + ChatColor.BLUE + "DUEL" + ChatColor.WHITE + "]" + ChatColor.WHITE + " ";

    private HashMap<Player, Player> players = new HashMap<>();

    private HashMap<Player, Location> location = new HashMap<>();

    public HashMap<Player, Location> getLocation() {
        return location;
    }

    public HashMap<Player, Player> getPlayers() {
        return players;
    }

    public String highlight(String string) {
        return ChatColor.BLUE + string + ChatColor.WHITE + "";
    }

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("duel").setExecutor(new DuelCommand(this));
        getServer().getConsoleSender().sendMessage(prefix + "has been enabled, created by " + highlight("BinaryDctr") + ".");
        getServer().getPluginManager().registerEvents(new DuelEvent(this), this);

    }

    @Override
    public void onDisable() {

    }

    public Location getSpawn1() {
        double x = getConfig().getInt("Arena.Spawn1.X");
        double y = getConfig().getInt("Arena.Spawn1.Y");
        double z = getConfig().getInt("Arena.Spawn1.Z");
        int pitch = getConfig().getInt("Arena.Spawn1.Pitch");
        int yaw = getConfig().getInt("Arena.Spawn1.Yaw");
        String world = getConfig().getString("Arena.Spawn1.World");
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public Location getSpawn2() {
        double x = getConfig().getInt("Arena.Spawn2.X");
        double y = getConfig().getInt("Arena.Spawn2.Y");
        double z = getConfig().getInt("Arena.Spawn2.Z");
        int pitch = getConfig().getInt("Arena.Spawn2.Pitch");
        int yaw = getConfig().getInt("Arena.Spawn2.Yaw");
        String world = getConfig().getString("Arena.Spawn2.World");
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
