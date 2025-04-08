package desktecc.depression.events;

import desktecc.depression.datas.UnhealthyFoodsDATA;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Map;

import static desktecc.depression.Depression.getPlayerMental;
import static java.util.Map.entry;

public class NegativeMental implements Listener {

    @EventHandler
    public static void onEat(PlayerItemConsumeEvent event){
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
        //TODO: create a stream() to get food and subtract value from player mentalPoints
        for(Material food : unhealthyFoods.keySet()){
            if(event.getItem().getType()==food){
                ateUnhealthyFood=true;
            }
        }

        if(ateUnhealthyFood){
            Float mentalPoints = getPlayerMental(event.getPlayer());
            //TODO: every food will have your own value to decrease mental points
        }
    }
}
