package desktecc.depression;

import desktecc.depression.datas.PlayerMoodDATA;
import desktecc.depression.events.NegativeMental;
import desktecc.depression.events.PlayerEvents;
import desktecc.depression.events.PositiveMental;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Depression extends JavaPlugin {

    private static final HashMap<UUID, PlayerMoodDATA> playersMental = new HashMap<>();
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new NegativeMental(), this);
        getServer().getPluginManager().registerEvents(new PositiveMental(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"Depression Plugin"+ChatColor.GREEN+" enabled!"+ChatColor.RESET);

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"Depression Plugin"+ChatColor.RED+" disabled!"+ChatColor.RESET);
    }

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    public static HashMap<UUID, PlayerMoodDATA> getPlayersMental(){
     return playersMental;
    }

    public static String debugSoloInfo(Player player){
        return String.format("onDark: %b\n", getPlayerMental(player).getOnDark()) +
                String.format("PlayerAsleep: %b\n", getPlayerMental(player).getCheckSleep().getAsleep()) +
                String.format("nearEnderman: %b\n", getPlayerMental(player).getNearEnderman()) +
                String.format("timeAlone: %d\n", getPlayerMental(player).getTimeAlone()) +
                String.format("MentalPoints: %f %%\n", getPlayerMental(player).getMentalPoints());
    }

    public static PlayerMoodDATA getPlayerMental(Player player){
        return playersMental.get(player.getUniqueId());
    }

    public static void insertPlayerMental(Player player, PlayerMoodDATA moodDATA){
        playersMental.put(player.getUniqueId(), moodDATA);
    }

    public static void removePlayerMental(Player player){
        playersMental.remove(player.getUniqueId());
    }

    public static Float getPlayerMentalPoints(Player player){
        return playersMental.get(player.getUniqueId()).getMentalPoints();
    }
}
