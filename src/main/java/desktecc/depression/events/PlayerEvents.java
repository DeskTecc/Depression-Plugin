package desktecc.depression.events;

import desktecc.depression.Depression;
import desktecc.depression.datas.PlayerMoodDATA;
import desktecc.depression.datas.PlayerSleepDATA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static desktecc.depression.Depression.*;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(Depression.getPlugin(), () -> {
            if(getPlayerMental(player).getMentalPoints()<=70){
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15,1));
            }
            if(getPlayerMental(player).getMentalPoints()<=50){
                player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 15,1));
            }
            if(getPlayerMental(player).getMentalPoints()<=40){
                Random rng = new Random();
                int random_number = rng.nextInt(0,100);
                if(random_number<=10){
                    List<Sound> monster_sounds = Arrays.asList(
                            Sound.ENTITY_CREEPER_HURT,
                            Sound.ENTITY_CREEPER_DEATH,
                            Sound.ENTITY_CREEPER_PRIMED,
                            Sound.ENTITY_ENDERMAN_AMBIENT,
                            Sound.ENTITY_ENDERMAN_SCREAM,
                            Sound.ENTITY_ENDERMAN_STARE,
                            Sound.ENTITY_ENDERMAN_DEATH,
                            Sound.ENTITY_GHAST_SCREAM,
                            Sound.ENTITY_GHAST_AMBIENT,
                            Sound.ENTITY_GHAST_DEATH,
                            Sound.ENTITY_SKELETON_AMBIENT,
                            Sound.ENTITY_SKELETON_DEATH,
                            Sound.ENTITY_SKELETON_STEP,
                            Sound.ENTITY_SKELETON_HURT,
                            Sound.ENTITY_WITHER_SKELETON_AMBIENT,
                            Sound.ENTITY_WITHER_SKELETON_STEP,
                            Sound.ENTITY_WITHER_SKELETON_HURT,
                            Sound.ENTITY_SLIME_SQUISH,
                            Sound.ENTITY_SPIDER_AMBIENT,
                            Sound.ENTITY_SPIDER_HURT,
                            Sound.ENTITY_SPIDER_STEP,
                            Sound.ENTITY_SPIDER_DEATH,
                            Sound.ENTITY_WARDEN_AMBIENT,
                            Sound.ENTITY_WARDEN_DIG,
                            Sound.ENTITY_WARDEN_DEATH,
                            Sound.ENTITY_WITCH_AMBIENT,
                            Sound.ENTITY_WITCH_DRINK,
                            Sound.ENTITY_WITCH_HURT,
                            Sound.ENTITY_WITCH_DEATH,
                            Sound.ENTITY_ZOMBIE_AMBIENT,
                            Sound.ENTITY_ZOMBIE_HURT,
                            Sound.ENTITY_ZOMBIE_DEATH,
                            Sound.ENTITY_ZOMBIE_STEP
                            );
                    Sound random_sound = monster_sounds.get(rng.nextInt(0, monster_sounds.size()));

                    float random_volume = rng.nextFloat(1F,3F);

                    player.playSound(player.getLocation(), random_sound, random_volume, 1F);
                }
            }
            if(getPlayerMental(player).getMentalPoints()<=30){
                if(getPlayerMental(player).getMentalPoints()<=10){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 1));
                }else {
                    if (player.getLocation().getBlock().getLightLevel() > 7) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 1));
                    }
                }
            }
        }, 20L * 10L, 20L * 10L);


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

    //mentalPoints below 90
    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event){

        Player player = event.getPlayer();
        if(getPlayerMental(player).getMentalPoints()<=90) {
            List<Material> meat = Arrays.asList(
                    Material.BEEF,
                    Material.CHICKEN,
                    Material.MUTTON,
                    Material.PORKCHOP,
                    Material.RABBIT,
                    Material.TROPICAL_FISH,
                    Material.COOKED_BEEF,
                    Material.COOKED_CHICKEN,
                    Material.COOKED_MUTTON,
                    Material.COOKED_PORKCHOP,
                    Material.COOKED_RABBIT
            );

            for (Material food : meat) {
                if (food.equals(event.getItem().getType())) {
                    if (player.getFoodLevel() == 20) {
                        player.setFoodLevel(19);
                    }
                }
            }
        }
    }

    //mentalPoints below 80
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player player){
            if(getPlayerMental(player).getMentalPoints()<=80) {
                if (event.getEntity() instanceof Creature) {
                    if (!(event.getEntity() instanceof Monster)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    //mentalPoints 60
    @EventHandler
    public void onPlayerFoodLevelRaise(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player player){
            if(getPlayerMental(player).getMentalPoints()<=60) {
                event.setFoodLevel(1);
            }
        }
    }

    //mentalPoints 20
    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() instanceof Player player){

            if(getPlayerMental(player).getMentalPoints()<=20) {
                List<Material> pickaxes = Arrays.asList(
                        Material.WOODEN_PICKAXE,
                        Material.STONE_PICKAXE,
                        Material.GOLDEN_PICKAXE,
                        Material.IRON_PICKAXE,
                        Material.DIAMOND_PICKAXE,
                        Material.NETHERITE_PICKAXE
                );
                for(Material pickaxe : pickaxes){
                    if(player.getItemInUse().getType()==pickaxe){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    //mentalPoints 10
    @EventHandler
    public void onPlayerTargeted(EntityTargetEvent event){
        if(event.getTarget() instanceof Player){
            event.setCancelled(true);
        }
    }
}
