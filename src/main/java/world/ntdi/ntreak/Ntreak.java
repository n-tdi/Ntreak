package world.ntdi.ntreak;

import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.ntreak.command.MegastreakCommandFunction;
import world.ntdi.ntreak.listener.DeathListener;
import world.ntdi.ntreak.utils.DataYML;

import java.io.IOException;

public final class Ntreak extends JavaPlugin {
    private static Ntreak instance;
    public static DataYML dataYML;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveDefaultConfig();
        dataYML = new DataYML();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        new MegastreakCommandFunction();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            dataYML.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Ntreak getInstance() {
        return instance;
    }
}
