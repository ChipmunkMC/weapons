package pw.kaboom.weapons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpectralArrow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;

class WeaponArcher implements Listener {
	static void leftClick(Material item, String name, PlayerInteractEvent event) {
		if (item == Material.SPECTRAL_ARROW &&
			"§rArcher".equals(name)) {
			final Player player = event.getPlayer();
			final World world = player.getWorld();

			for (int i = 0; i <= 20; i++) {
				final double randomX = (Math.random() * ((15 + 15) + 1)) - 15;
				final double randomY = (Math.random() * ((15 + 15) + 1)) - 15;
				final double randomZ = (Math.random() * ((15 + 15) + 1)) - 15;

				final Vector randomDirection = new Vector(
					randomX,
					randomY,
					randomZ).normalize().multiply(8);

				final SpectralArrow arrow = (SpectralArrow) world.spawnEntity(
					player.getLocation(),
					EntityType.SPECTRAL_ARROW
				);
				arrow.setCustomName("WeaponArcherArrow");
				arrow.setShooter(player);
				arrow.setVelocity(randomDirection);
			}

			final Location eyeLocation = player.getEyeLocation();
			final float volume = 1.0F;
			final float pitch = 1.5F;

			world.playSound(
				eyeLocation,
				Sound.BLOCK_ANVIL_DESTROY,
				volume,
				pitch
			);
			event.setCancelled(true);
		}
	}

	/* Make shooter invulnerable to weapon projectiles */
	@EventHandler
	void onProjectileCollide(ProjectileCollideEvent event) {
		if (event.getEntityType() == EntityType.SPECTRAL_ARROW) {
			final Projectile projectile = event.getEntity();

			if ("WeaponArcherArrow".equals(projectile.getCustomName())) {
				final Entity collidedWith = event.getCollidedWith();

				if (collidedWith.getType() == EntityType.PLAYER &&
					projectile.getShooter() instanceof Player &&
					((Player) projectile.getShooter()).getUniqueId().equals(collidedWith.getUniqueId())) {
					event.setCancelled(true);
				}
			}
		}
	}
}
