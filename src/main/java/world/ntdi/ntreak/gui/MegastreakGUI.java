package world.ntdi.ntreak.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.ntreak.Ntreak;
import world.ntdi.ntreak.listener.DeathListener;
import world.ntdi.ntreak.utils.FormatUtils;

public class MegastreakGUI extends GUI {
    public MegastreakGUI(Player p) {
        super(FormatUtils.colorize(Ntreak.getInstance().getConfig().getString("megastreak-gui-title")), 3);

        addItems(p);

        open(p);
    }

    private void addItems(Player p) {

        ItemStack hunter = ItemBuilder.of(Material.YELLOW_WOOL, 1,
                ChatColor.GOLD.toString() + ChatColor.BOLD + "Hunter")
                .lores(FormatUtils.list("hunter"))
                .flag(ItemFlag.HIDE_ENCHANTS)
                .glow(equals(p, "hunter"))
                .build();

        ItemStack overdrive = ItemBuilder.of(Material.RED_WOOL, 1,
                        ChatColor.RED.toString() + ChatColor.BOLD + "Overdrive")
                .lores(FormatUtils.list("overdrive"))
                .flag(ItemFlag.HIDE_ENCHANTS)
                .glow(equals(p, "overdrive"))
                .build();

        ItemStack tank = ItemBuilder.of(Material.LIME_WOOL, 1,
                        ChatColor.GREEN.toString() + ChatColor.BOLD + "Tank")
                .lores(FormatUtils.list("tank"))
                .flag(ItemFlag.HIDE_ENCHANTS)
                .glow(equals(p, "tank"))
                .build();


        createButton(hunter, "hunter", 10);
        createButton(overdrive, "overdrive", 13);
        createButton(tank, "tank", 16);
    }

    private void createButton(ItemStack itemStack, String megastreak, int slot) {
        addButton(Button.create(itemStack, e -> {
            Player p = (Player) e.getWhoClicked();
            if (DeathListener.killStreak.containsKey(p.getUniqueId())) {
                if (DeathListener.killStreak.get(p.getUniqueId()) > 50) {
                    p.closeInventory();
                    p.sendMessage(FormatUtils.noChange());
                    return;
                }
            }
            Ntreak.dataYML.setPlayerMegastreak(p, megastreak);
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.closeInventory();
                    new MegastreakGUI(p);
                }
            }.runTaskLater(Ntreak.getInstance(), 1);
        }), slot);
    }

    private boolean equals(Player p, String val) {
        return Ntreak.dataYML.getPlayerMegastreak(p).equals(val);
    }
}
