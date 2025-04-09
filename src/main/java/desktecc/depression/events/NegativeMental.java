package desktecc.depression.events;

import desktecc.depression.Depression;
import desktecc.depression.datas.PlayerMoodDATA;
import desktecc.depression.datas.UnhealthyFoodsDATA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;

import static desktecc.depression.Depression.*;
import static java.util.Map.entry;

public class NegativeMental implements Listener {


    //Eat unhealthy food
    @EventHandler
    public static void onEat(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();

        Pair<Boolean, Float> unhealthyFoodValidation = validUnhealthyFood(event.getItem().getType());

        if(unhealthyFoodValidation.getValue0()){
            getPlayerMental(player).subMentalPoints(unhealthyFoodValidation.getValue1());
        }
    }

    public static Pair<Boolean, Float> validUnhealthyFood(Material consumable){
        Map<Material, Float> unhealthyFoods = Map.ofEntries(
                entry(Material.POISONOUS_POTATO, UnhealthyFoodsDATA.POISONOUS_POTATO.getUnhealthyFoodPoints()),
                entry(Material.ROTTEN_FLESH, UnhealthyFoodsDATA.ROTTEN_FLESH.getUnhealthyFoodPoints()),
                entry(Material.SPIDER_EYE, UnhealthyFoodsDATA.SPIDER_EYE.getUnhealthyFoodPoints()),
                entry(Material.PUFFERFISH, UnhealthyFoodsDATA.PUFFERFISH.getUnhealthyFoodPoints()),
                entry(Material.DRIED_KELP, UnhealthyFoodsDATA.DRIED_KELP.getUnhealthyFoodPoints()),
                entry(Material.BEEF, UnhealthyFoodsDATA.BEEF.getUnhealthyFoodPoints()),
                entry(Material.CHICKEN, UnhealthyFoodsDATA.CHICKEN.getUnhealthyFoodPoints()),
                entry(Material.COD, UnhealthyFoodsDATA.COD.getUnhealthyFoodPoints()),
                entry(Material.MUTTON, UnhealthyFoodsDATA.MUTTON.getUnhealthyFoodPoints()),
                entry(Material.PORKCHOP, UnhealthyFoodsDATA.PORKCHOP.getUnhealthyFoodPoints()),
                entry(Material.RABBIT, UnhealthyFoodsDATA.RABBIT.getUnhealthyFoodPoints()),
                entry(Material.SALMON, UnhealthyFoodsDATA.SALMON.getUnhealthyFoodPoints()),
                entry(Material.TROPICAL_FISH, UnhealthyFoodsDATA.TROPICAL_FISH.getUnhealthyFoodPoints())
        );

        boolean ateUnhealthyFood = false;
        Float unhealthyPoints = 0.0F;

        for(Material food : unhealthyFoods.keySet()){
            if (consumable == food) {
                ateUnhealthyFood = true;
                unhealthyPoints = unhealthyFoods.get(food);
                break;
            }
        }

        return new Pair<>(ateUnhealthyFood,unhealthyPoints);
    }

    //Take too much damage
    @EventHandler
    public static void onDamage(EntityDamageEvent event) {
        if (event.getDamage() >= 6.0){
            if (event.getEntity() instanceof Player player) {
                getPlayerMental(player).subMentalPoints((float) event.getDamage()/2F);
            }
        }
    }

    //Stay in the dark for too much time and too much time without sleep
    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerMoodDATA playerMood = getPlayerMental(player);
        BukkitScheduler scheduler = Bukkit.getScheduler();

        //Check the dark
        //Check if player not on the dark previously
        if(!playerMood.getOnDark()) {
            if (player.getLocation().getBlock().getLightLevel() < 5) {
                playerMood.setOnDark(true);

                scheduler.runTaskTimer(Depression.getPlugin(), task -> {
                    if (player.getLocation().getBlock().getLightLevel() < 5) {
                        playerMood.subMentalPoints(2.0F);
                    } else {
                        playerMood.setOnDark(false);
                        task.cancel();
                    }
                }, 20L * 60L, 20L * 60L);
            }
        }

        //Check Enderman
        if(!player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 8, 8, 8).isEmpty()){
            //if exists any entity so this add time
            playerMood.setTimeAlone(player.getPlayerTime()+9000);

            List<Entity> entities = (List<Entity>) player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 8, 8, 8);

            for(Entity entity: entities) {
                if (entity.getType() == EntityType.ENDERMAN) {
                    playerMood.setNearEnderman(true);
                }
            }
            if(playerMood.getNearEnderman()) {
                scheduler.runTaskTimer(Depression.getPlugin(), task -> {
                    List<Entity> checkNewEntities = (List<Entity>) player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 8, 8, 8);

                    boolean checkEnderman = false;

                    for(Entity entity: checkNewEntities) {
                        if (entity.getType() == EntityType.ENDERMAN) {
                            checkEnderman = true;
                        }
                    }
                    if (checkEnderman) {
                        playerMood.subMentalPoints(1.5F);
                    } else {
                        playerMood.setNearEnderman(false);
                        task.cancel();
                    }
                }, 20L * 30L, 20L * 30L);
            }
        }else{
            if(playerMood.getTimeAlone()==0L){
                playerMood.setTimeAlone(player.getPlayerTime());
            }else{
                long playerTime = playerMood.getTimeAlone();

                if((playerTime+18000)<=player.getPlayerTime()){
                    playerMood.subMentalPoints(2.0F);
                    playerMood.setTimeAlone(player.getPlayerTime()+18000);
                }
            }
        }
    }
}
