package world.ntdi.ntreak.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;
import world.ntdi.ntreak.gui.MegastreakGUI;

public class MegastreakCommandFunction extends CommandCore implements CommandFunction {
    public MegastreakCommandFunction() {
        super("megastreak", null, "ms");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof Player p) {
            new MegastreakGUI(p);
        }
    }
}
