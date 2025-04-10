package desktecc.depression.events;

import desktecc.depression.datas.HealthyFoodsDATA;
import desktecc.depression.datas.PlayerMoodDATA;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.javatuples.Pair;


import java.util.List;
import java.util.Map;

import static desktecc.depression.Depression.getPlayerMental;
import static java.util.Map.entry;

public class PositiveMental implements Listener {

    //Eat healthy food
    @EventHandler
    public static void onEat(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();

        Pair<Boolean, Float> findHealthyFood = validHealthyFood(event.getItem().getType());

        if(!findHealthyFood.getValue0()){
            Float mentalPoints = getPlayerMental(player).getMentalPoints();
            getPlayerMental(player).setMentalPoints(mentalPoints+ findHealthyFood.getValue1());
        }
    }

    public static Pair<Boolean, Float> validHealthyFood(Material consumable){
        Map<Material, Float> healthyFoods = Map.ofEntries(
                entry(Material.GOLDEN_APPLE, HealthyFoodsDATA.GOLDEN_APPLE.getHealthyFoodPoints()),
                entry(Material.GOLDEN_CARROT, HealthyFoodsDATA.GOLDEN_CARROT.getHealthyFoodPoints()),
                entry(Material.APPLE, HealthyFoodsDATA.APPLE.getHealthyFoodPoints()),
                entry(Material.CARROT, HealthyFoodsDATA.CARROT.getHealthyFoodPoints()),
                entry(Material.CAKE, HealthyFoodsDATA.CAKE.getHealthyFoodPoints()),
                entry(Material.PUMPKIN_PIE, HealthyFoodsDATA.PUMPKIN_PIE.getHealthyFoodPoints()),
                entry(Material.BAKED_POTATO, HealthyFoodsDATA.BAKED_POTATO.getHealthyFoodPoints()),
                entry(Material.BEETROOT_SOUP, HealthyFoodsDATA.BEETROOT_SOUP.getHealthyFoodPoints()),
                entry(Material.MUSHROOM_STEW, HealthyFoodsDATA.MUSHROOM_STEW.getHealthyFoodPoints()),
                entry(Material.HONEY_BOTTLE, HealthyFoodsDATA.HONEY_BOTTLE.getHealthyFoodPoints()),
                entry(Material.MELON_SLICE, HealthyFoodsDATA.MELON_SLICE.getHealthyFoodPoints()),
                entry(Material.COOKIE, HealthyFoodsDATA.COOKIE.getHealthyFoodPoints()),
                entry(Material.BREAD, HealthyFoodsDATA.BREAD.getHealthyFoodPoints()),
                entry(Material.SWEET_BERRIES, HealthyFoodsDATA.SWEET_BERRIES.getHealthyFoodPoints()),
                entry(Material.GLOW_BERRIES, HealthyFoodsDATA.GLOW_BERRIES.getHealthyFoodPoints()),
                entry(Material.BEETROOT, HealthyFoodsDATA.BEETROOT.getHealthyFoodPoints()),
                entry(Material.POTATO, HealthyFoodsDATA.POTATO.getHealthyFoodPoints()),
                entry(Material.RABBIT_STEW, HealthyFoodsDATA.RABBIT_STEW.getHealthyFoodPoints())
        );

        boolean atehealthyFood = false;
        Float healthyPoints = 0.0F;

        for(Material food : healthyFoods.keySet()){
            if (consumable == food) {
                atehealthyFood = true;
                healthyPoints = healthyFoods.get(food);
                break;
            }
        }

        return new Pair<>(atehealthyFood, healthyPoints);
    }

    //Trade villager
    @EventHandler
    public static void onTrade(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();

        if(event.getRightClicked().getType().equals(EntityType.VILLAGER)){
            float points = 2.0F;
            if(getPlayerMental(player).getVillagerClickCounter()<4) {
                points = points-((float) getPlayerMental(player).getVillagerClickCounter()+1F)/2F;
                getPlayerMental(player).addMentalPoints(points);
            }
        }
    }

    //Breed animals
    @EventHandler
    public static void onBreedAnimals(EntityBreedEvent event){
        if(event.getBreeder() instanceof Player player){
            getPlayerMental(player).addMentalPoints(2.0F);
        }
    }

    //Stay with pets
    @EventHandler
    public static void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerMoodDATA playerMood = getPlayerMental(player);

        if(!player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 8, 8, 8).isEmpty()) {
            List<Entity> entities = (List<Entity>) player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 8, 8, 8);

            for (Entity entity : entities) {
                Tameable animal = (Tameable) entity;

                //if animal is tamed and the owner is the near player
                if (animal.isTamed()) {
                    if(animal.getOwner().getUniqueId().equals(player.getUniqueId())){
                        playerMood.setTimeAlone(player.getPlayerTime() + 9000);
                        playerMood.addMentalPoints(4.0F);
                    }
                }
            }
        }
    }

    //Events below for harvest crops
    @EventHandler
    public static void onHarvest(PlayerHarvestBlockEvent event){
        Player player = event.getPlayer();
        PlayerMoodDATA playerMood = getPlayerMental(player);
        playerMood.addMentalPoints(0.15F);
    }

    @EventHandler
    public static void onCrop(BlockBreakEvent event){
        Player player = event.getPlayer();
        PlayerMoodDATA playerMood = getPlayerMental(player);
        if(event.getBlock().getType().isCompostable()){
            playerMood.addMentalPoints(0.15F);
        }
    }
}
