package com.tyrengard.aureycore.foundation.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockUtils {
	public static boolean isAirOrNull(ItemStack item){
		return item == null || item.getType().equals(Material.AIR);
	}
	public static final BlockFace getBlockFacePlayerIsLookingAt(Player p) {
		BlockFace face = null;
		List<Block> blocks = p.getLastTwoTargetBlocks(null, 10);
		if (blocks.size() > 1) 
		    face = blocks.get(1).getFace(blocks.get(0));
		
		return face;
	}
	public static final List<Block> getBlocksAroundLocation(Location l, BlockFace d, int radius, int height, int xOffset, int zOffset, 
			int yOffset, boolean circular, boolean hollow, Material[] allowedMaterials, Material[] prohibitedMaterials) {
		List<Block> output = new ArrayList<>();
		if (d == null) return output;
		
		World w = l.getWorld();
		int cx = l.getBlockX(), minX = cx, maxX = cx;
		int cy = l.getBlockY(), minY = cy, maxY = cy;
		int cz = l.getBlockZ(), minZ = cz, maxZ = cz;
		
		int xMod, yMod, zMod;
		switch (d) {
		case UP: case DOWN:
			xMod = radius; yMod = height - 1; zMod = radius;
			break;
		case WEST: case EAST:
			xMod = height - 1; yMod = radius; zMod = radius;
			break;
		case NORTH: case SOUTH:
			xMod = radius; yMod = radius; zMod = height - 1;
			break;
		default: return output;
		}
		
		// square area
		if (!circular) {
			minX = cx - xMod + xOffset; maxX = cx + xMod + xOffset;
			minZ = cz - zMod + zOffset; maxZ = cz + zMod + zOffset;
			minY = cy - yMod + yOffset; maxY = cy + yMod + yOffset;
		}
		
		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				 for (int y = minY; y <= maxY; y++) {
					output.add(w.getBlockAt(new Location(w, x, y, z)));
				}
			}
		}
		
		if (allowedMaterials != null) {
			List<Material> am = Arrays.asList(allowedMaterials);
			output.removeIf(b -> !am.contains(b.getType()));
		}
		if (prohibitedMaterials != null) {
			List<Material> pm = Arrays.asList(prohibitedMaterials);
			output.removeIf(b -> pm.contains(b.getType()));
		}
		
		return output;
	}
	public static final List<Block> getBlocks(Chunk c) {
		List<Block> output = new ArrayList<>();
		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 256; y++) 
				for (int z = 0; z < 16; z++)
					output.add(c.getBlock(x, y, z));
		
		return output;
	}
}
