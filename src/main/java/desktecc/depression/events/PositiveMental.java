package desktecc.depression.events;

import desktecc.depression.datas.HealthyFoodsDATA;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.javatuples.Pair;


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
            getPlayerMental(player).setMentalPoints(getPlayerMental(player).getMentalPoints()+2.0F);
        }
    }

    //Breed animals
    @EventHandler
    public static void onBreedAnimals(EntityBreedEvent event){
        if(event.getBreeder() instanceof Player player){
            getPlayerMental(player).setMentalPoints(getPlayerMental(player).getMentalPoints()+2.0F);
        }
    }
}
