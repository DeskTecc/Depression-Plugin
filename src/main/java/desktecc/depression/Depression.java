package desktecc.depression;

import desktecc.depression.datas.PlayerMoodDATA;
import desktecc.depression.events.NegativeMental;
import desktecc.depression.events.PositiveMental;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Depression extends JavaPlugin {

    private static HashMap<UUID, PlayerMoodDATA> playersMental;
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new NegativeMental(), this);
        getServer().getPluginManager().registerEvents(new PositiveMental(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    public static HashMap<UUID, PlayerMoodDATA> getPlayersMental(){
     return playersMental;
    }

    public static PlayerMoodDATA getPlayerMental(Player player){
        return playersMental.get(player.getUniqueId());
    }

    public static void setPlayerMental(Player player, PlayerMoodDATA moodDATA){
        playersMental.put(player.getUniqueId(), moodDATA);
    }

    public static Float getPlayerMentalPoints(Player player){
        return playersMental.get(player.getUniqueId()).getMentalPoints();
    }
}
