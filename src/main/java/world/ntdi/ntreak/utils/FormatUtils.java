package world.ntdi.ntreak.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import world.ntdi.ntreak.Ntreak;

import java.util.ArrayList;
import java.util.List;

public final class FormatUtils {
    private static final FileConfiguration config = Ntreak.getInstance().getConfig();

    public static String colorize(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String broadcastKS(final Player p, final int ks) {
        return colorize(
                config.getString("killstreak-broadcast")
                        .replace("%player%", p.getName())
                        .replace("%count%", ks + "")
        );
    }

    public static String keyCommand(final Player p) {
        return config.getString("key-command")
                .replace("%player%", p.getName());
    }

    public static String megaStreakMessage(final Player p) {
        return colorize(
                config.getString("megastreak-message")
                        .replace("%player%", p.getName())
                        .replace("%megastreak%", Ntreak.dataYML.getPlayerMegastreak(p))
        );
    }

    public static String setAsMegastreak() {
        return colorize(
                config.getString("set-as-megastreak")
        );
    }
    public static List<String> list(String value) {
        List<String> newItems = new ArrayList<>();
        newItems.add(setAsMegastreak());
        config.getStringList(value).forEach(str -> newItems.add(colorize(str)));
        return newItems;
    }

    public static String noChange() {
        return colorize(
                config.getString("no-change")
        );
    }
}
