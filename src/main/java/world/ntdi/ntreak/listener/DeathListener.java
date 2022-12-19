package world.ntdi.ntreak.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import world.ntdi.ntreak.Ntreak;
import world.ntdi.ntreak.utils.FormatUtils;

import java.util.UUID;
import java.util.WeakHashMap;

public class DeathListener implements Listener {
    public static WeakHashMap<UUID, Integer> killStreak = new WeakHashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        final Player killer = p.getKiller();
        final UUID kUUID = killer.getUniqueId();

        killStreak.put(p.getUniqueId(), 0);
        killStreak.put(kUUID, getKs(kUUID));

        if (getKs(kUUID) % 5 == 0) {
            Bukkit.broadcastMessage(FormatUtils.broadcastKS(killer, getKs(kUUID)));
        }
        if (getKs(kUUID) % 25 == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FormatUtils.keyCommand(killer));
        }
        if (getKs(kUUID) == 50) {
            Bukkit.broadcastMessage(FormatUtils.megaStreakMessage(killer));
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(
                    player.getLocation(),
                    Sound.valueOf(Ntreak.getInstance().getConfig().getString("megastreak-sound")),
                    1, 1));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1, false, false, false));
        }

        if (getKs(kUUID) > 50) {
            String megastreak = Ntreak.dataYML.getPlayerMegastreak(killer);
            if (megastreak.equals("overdrive")) { // OVERDRIVE
                if (getKs(kUUID) % 10 == 0) { // Key every 10 kills
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FormatUtils.keyCommand(killer)); // Give Key
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 3, false, true, true)); // Strength III (0:10)
                }
            }
            if (megastreak.equals("tank")) { // TANK
                if (getKs(kUUID) % 20 == 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FormatUtils.keyCommand(killer)); // Give Key
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 15, 1, false, true, true)); // Resistance I (0:15)
                }
            }
            if (megastreak.equals("hunter")) { // HUNTER
                if (getKs(kUUID) % 10 == 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FormatUtils.keyCommand(killer)); // Give Key
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 15, 3, false, true, true)); // Speed III (0:15)
                    AttributeInstance attribute = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    attribute.setBaseValue(attribute.getBaseValue() - 0.5D);
                }
            }
        }

    }

    private int getKs(UUID uuid) {
        if (killStreak.containsKey(uuid)) {
            return killStreak.get(uuid);
        }
        return 0;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof Player defender) {
            if (Ntreak.dataYML.getPlayerMegastreak(defender).equals("overdrive")) {
                if (getKs(defender.getUniqueId()) > 50) {
                    double dmg = e.getFinalDamage();
                    int percent = (getKs(defender.getUniqueId()) - 50) / 10;
                    double added = dmg * ((5.0f * percent) / 100.0f);
                    e.setDamage(dmg + added);
                }
            }
        }
    }

    @EventHandler
    public void onHunger(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();

        if (Ntreak.dataYML.getPlayerMegastreak(p).equals("tank")) {
            if (getKs(p.getUniqueId()) > 50) {
                p.setSaturation(0);
                p.setSaturatedRegenRate(999999999);
            }
        }
    }
}
