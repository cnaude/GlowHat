package me.nmc94.GlowHat;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GHPlayer implements Listener {

    private static HashMap<String, Location> pLocations = new HashMap<String, Location>();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        Player player = event.getPlayer();
        GlowHat.resetLight(pLocations.get(pName), player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        int hey = event.getPlayer().getInventory().getHelmet().getTypeId();
        String pName = event.getPlayer().getName();
        Location toLoc = event.getTo();
        Location fromLoc = event.getFrom();
        if (!pLocations.containsKey(pName)) {
            pLocations.put(pName, fromLoc);
        }
        double d = pLocations.get(pName).distance(toLoc);
        Player player = event.getPlayer();

        if ((hey == 50) || (hey == 10) || (hey == 11) || (hey == 51) || (hey == 89) || (hey == 91) || (hey == 124)) {

            if (d >= 2.0) {
                GlowHat.resetLight(pLocations.get(pName), player);
                GlowHat.LightUp(toLoc, player);
                pLocations.put(pName, toLoc);
            }
        } else {
            GlowHat.resetLight(pLocations.get(pName), player);
        }
    }
}