package desktecc.depression.events;

import desktecc.depression.Depression;
import desktecc.depression.datas.PlayerMoodDATA;
import desktecc.depression.datas.PlayerSleepDATA;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static desktecc.depression.Depression.*;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        //task for check sleep
        BukkitTask sleepCheck = new BukkitRunnable(){
            @Override
            public void run(){
                float timeSinceRest = (float) player.getStatistic(Statistic.TIME_SINCE_REST);
                getPlayerMental(player).setVillagerClickCounter(0);
                if (player.getStatistic(Statistic.TIME_SINCE_REST) >= 18000) {
                    Float mentalPointsDamage = (timeSinceRest/10000)*2.0F;
                    getPlayerMental(player).subMentalPoints(mentalPointsDamage);
                } else {
                    getPlayerMental(player).getCheckSleep().setAsleep(false);
                    getPlayerMental(player).addMentalPoints(2.0F);
                }
                Bukkit.getConsoleSender().sendMessage(debugSoloInfo(player));
            }
        }.runTaskTimer(Depression.getPlugin(),20L * 60L, 20L * 60L * 15L);

        PlayerSleepDATA playerSleepDATA = new PlayerSleepDATA(sleepCheck, false);

        PlayerMoodDATA moodDATA = new PlayerMoodDATA(false, playerSleepDATA,false, 0,100.0F);

        insertPlayerMental(event.getPlayer(), moodDATA);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerMoodDATA playerMood = getPlayerMental(player);
        //remove the sleep check
        playerMood.getCheckSleep().getTask().cancel();
        //then remove the player from list
        removePlayerMental(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        Player player = event.getEntity();
        //Infos reset when player die
        getPlayerMental(player).setMentalPoints(100.0F);
        getPlayerMental(player).setTimeAlone(0L);
        getPlayerMental(player).getCheckSleep().setAsleep(false);
        getPlayerMental(player).setNearEnderman(false);
        getPlayerMental(player).setOnDark(false);
        getPlayerMental(player).setVillagerClickCounter(0);
    }
}
