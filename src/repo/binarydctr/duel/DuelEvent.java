package repo.binarydctr.duel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.FallingBlock;

/**
 * ******************************************************************
 * Copyright BinaryDctr (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of BinaryDctr. Distribution, reproduction, taking snippets, or
 * claiming any contents as your will break the terms of the license, and void any
 * agreements with you, the third party.
 * ******************************************************************
 **/
public class DuelEvent implements Listener {

    Core core;

    public List<FallingBlock> getBlocks() {
        return blocks;
    }

    private List<FallingBlock> blocks;

    public DuelEvent(Core core) {
        this.core = core;
        blocks = new ArrayList<>();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player player1 = event.getEntity().getKiller();

        if(core.getPlayers().containsKey(player) && core.getPlayers().containsKey(player1)) {
            player.setHealth(20);
            player1.setHealth(20);
            player.setFoodLevel(20);
            player1.setFoodLevel(20);
            player.teleport(core.getLocation().get(player));
            player1.teleport(core.getLocation().get(player1));
            core.getLocation().remove(player);
            core.getLocation().remove(player1);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            core.getPlayers().remove(player);
            core.getPlayers().remove(player1);
            Bukkit.broadcastMessage(core.prefix + core.highlight(player1.getName()) + " has defeated " + core.highlight(player.getName()) + " in a duel.");
            for(Player players : Bukkit.getOnlinePlayers()) {
                if(players != player && players != player1) {
                    player.showPlayer(players);
                    player1.showPlayer(players);
                }
            }
        }
    }
}
