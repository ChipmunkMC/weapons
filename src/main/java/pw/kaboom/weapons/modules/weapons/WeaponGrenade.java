package pw.kaboom.weapons.modules.weapons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class WeaponGrenade implements Listener {
    public static void rightClick(final Material item, final String name,
                                  final PlayerInteractEvent event) {
        if (item == Material.EGG
                && ("§rGrenade".equals(name) || "Grenade".equals(name))) {
            event.setCancelled(true);

            final Player player = event.getPlayer();
            final Location eyeLocation = player.getEyeLocation();

            final Egg egg = player.launchProjectile(Egg.class);
            egg.setCustomName("WeaponGrenade");
            egg.setShooter(player);

            final World world = player.getWorld();
            final float volume = 1.0F;
            final float pitch = 1.0F;

            world.playSound(
                eyeLocation,
                Sound.ENTITY_EGG_THROW,
                volume,
                pitch
            );
        }
    }

    @EventHandler
    private void onPlayerEggThrow(final PlayerEggThrowEvent event) {
        if ("WeaponGrenade".equals(event.getEgg().getCustomName())) {
            event.setHatching(false);
        }
    }

    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent event) {
        if (event.getEntityType() == EntityType.EGG) {
            final Projectile projectile = event.getEntity();

            if ("WeaponGrenade".equals(projectile.getCustomName())) {
                final Location location = projectile.getLocation();
                final World world = location.getWorld();
                final float power = 6;

                world.createExplosion(location, power);
            }
        }
    }
}
