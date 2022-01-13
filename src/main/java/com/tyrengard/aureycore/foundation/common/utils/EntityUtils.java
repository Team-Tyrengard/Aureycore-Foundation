package com.tyrengard.aureycore.foundation.common.utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Golem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class EntityUtils {
	public static final MetadataValue getMetadataValue(Entity entity, String title, Plugin pl) {
		for (MetadataValue mv : entity.getMetadata(title))
			if (mv.getOwningPlugin().equals(pl))
				return mv;
		
		return null;
	}
	
	public static final List<LivingEntity> getLivingEntitiesClosestToLocation(Location l, BlockFace face, double x, double y, double z,
			boolean playersIncluded, boolean mobsIncluded) {
		Location loc = l.clone();
		if (face != null) {
			switch (face) {
			case NORTH: // -Z
				loc.add(new Vector(0, 0, -z));
				break;
			case SOUTH: // +Z
				loc.add(new Vector(0, 0, z));
				break;
			case WEST: // -X
				loc.add(new Vector(-x, 0, 0));
				break;
			case EAST: // +X
				loc.add(new Vector(x, 0, 0));
				break;
			default: break;
			}
		}
		Stream<Entity> stream = l.getWorld().getNearbyEntities(loc, x, y, z, e -> e instanceof LivingEntity).stream();
		if (playersIncluded && !mobsIncluded)
			stream = stream.filter(entity -> entity instanceof Player);
		else if (!playersIncluded && mobsIncluded)
			stream = stream.filter(entity -> !(entity instanceof Player));
		
		return stream.sorted(new DistanceComparator(l)).map(entity -> (LivingEntity) entity).collect(Collectors.toList());
	}
	
	public static final List<LivingEntity> getLivingEntitiesClosestToEntity(Entity e, double x, double y, double z, 
			boolean playersIncluded, boolean mobsIncluded) {
		return getLivingEntitiesClosestToLocation(e.getLocation(), null, x, y, z, playersIncluded, mobsIncluded);
	}
	
	public static final List<LivingEntity> getLivingEntitiesClosestToEntity(Entity e, BlockFace face, double x, double y, double z, 
			boolean playersIncluded, boolean mobsIncluded) {
		return getLivingEntitiesClosestToLocation(e.getLocation(), face, x, y, z, playersIncluded, mobsIncluded);
	}
	
	public static final List<Player> getPlayersClosestToEntity(Entity e, double x, double y, double z) {
		return getLivingEntitiesClosestToLocation(e.getLocation(), null, x, y, z, true, false).stream().map(le -> (Player) le)
				.collect(Collectors.toList());
	}
	
	public static final boolean isHostile(LivingEntity e) {
		return e instanceof Monster || e instanceof Boss ||
			(e instanceof Bee && ((Bee) e).getAnger() > 0) || (e instanceof Wolf && ((Wolf) e).isAngry()) ||
			((e instanceof Slime || e instanceof Golem || e instanceof Llama || e instanceof PolarBear) && ((Mob) e).getTarget() != null);
	}
	
	private static final class DistanceComparator implements Comparator<Entity> {
		private Location target;
		DistanceComparator(Location target) {
			this.target = target;
		}
		@Override
		public int compare(Entity arg0, Entity arg1) {
			double d1 = arg0.getLocation().distance(target),
				   d2 = arg1.getLocation().distance(target);
			return (int) (d2 - d1); // d2 - d1 bec closer
		}
	}
}
