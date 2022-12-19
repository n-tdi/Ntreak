package world.ntdi.ntreak.utils;

import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.config.Config;
import world.ntdi.ntreak.Ntreak;

import java.util.UUID;

public class DataYML extends Config {
    public DataYML() {
        super("megastreak.yml", Ntreak.getInstance());
    }

    public String getPlayerMegastreak(Player p) {
        UUID uuid = p.getUniqueId();
        if (getString("megastreak."+uuid) != null) {
            return getString("megastreak."+uuid);
        }
        set("megastreak."+uuid, "overdrive");
        return "overdrive";
    }

    public void setPlayerMegastreak(Player p, String megastreak) {
        set("megastreak."+p.getUniqueId(), megastreak);
    }
}
