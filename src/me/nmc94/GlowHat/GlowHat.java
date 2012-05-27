package me.nmc94.GlowHat;

import java.util.HashMap;
import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumSkyBlock;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GlowHat extends JavaPlugin
{
  private final GHPlayer eventListener = new GHPlayer();
  public static HashMap<String, HashMap<Location, Integer>> oldBlocks = new HashMap();
  private final HashMap<Player, Boolean> debugees = new HashMap();


  @Override
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(eventListener, this);
  }

  public boolean isDebugging(Player player) {
    if (debugees.containsKey(player)) {
      return ((Boolean)debugees.get(player)).booleanValue();
    }
    return false;
  }

  public void setDebugging(Player player, boolean value)
  {
    debugees.put(player, Boolean.valueOf(value));
  }

  public static void LightUp(Location toPlayerLocation, Player player)
  {
    CraftWorld cWorld = (CraftWorld)toPlayerLocation.getWorld();
 
    int xNew = toPlayerLocation.getBlockX();
    int yNew = toPlayerLocation.getBlockY() + 2;
    int zNew = toPlayerLocation.getBlockZ();
 
    int lightLevel = 15;
 
    cWorld.getHandle().a(EnumSkyBlock.BLOCK, xNew, yNew, zNew, lightLevel);
 
    Location newSource = new Location(cWorld, xNew, yNew - 1, zNew);
    Material blockMaterial = newSource.getBlock().getType();
    byte blockData = newSource.getBlock().getData();
    newSource.getBlock().setType(blockMaterial);
    newSource.getBlock().setData(blockData);
    updateChunk(player);
  }

  public static void resetLight(Location fromPlayerLocation, Player player)
  {
    CraftWorld cWorld = (CraftWorld)fromPlayerLocation.getWorld();
 
    int xPrevious = fromPlayerLocation.getBlockX();
    int yPrevious = fromPlayerLocation.getBlockY() + 2;
    int zPrevious = fromPlayerLocation.getBlockZ();
 
    Location previousSource = new Location(cWorld, xPrevious, yPrevious, zPrevious);
    Material blockMaterial = previousSource.getBlock().getType();
    byte blockData = previousSource.getBlock().getData();
    previousSource.getBlock().setType(blockMaterial);
    previousSource.getBlock().setData(blockData);
    updateChunk(player,fromPlayerLocation);
  }
  
  public static void updateChunk(Player player)
  {
    CraftWorld cWorld = (CraftWorld)player.getWorld();
    EntityPlayer ep = ((CraftPlayer)player).getHandle();
 
    Chunk c = cWorld.getChunkAt(player.getLocation());
    ChunkCoordIntPair coord0 = new ChunkCoordIntPair(c.getX(), c.getZ());
 
    ep.chunkCoordIntPairQueue.add(0, coord0);
  }
  
  public static void updateChunk(Player player, Location location)
  {
    CraftWorld cWorld = (CraftWorld)player.getWorld();
    EntityPlayer ep = ((CraftPlayer)player).getHandle();
    Chunk c = cWorld.getChunkAt(location);
    ChunkCoordIntPair coord = new ChunkCoordIntPair(c.getX(), c.getZ());
    ep.chunkCoordIntPairQueue.add(0, coord);
  }
}

/* http://forums.bukkit.org/threads/lights.74943/ */