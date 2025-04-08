package desktecc.depression;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Depression extends JavaPlugin {

    private static HashMap<UUID, Float> playersMental;

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<UUID, Float> getPlayersMental(){
     return playersMental;
    }

    public static Float getPlayerMental(Player player){
        return playersMental.get(player.getUniqueId());
    }

    public static void setPlayerMental(Player player, Float points){
        if(playersMental.containsKey(player.getUniqueId())){
            playersMental.replace(player.getUniqueId(), points);
        }else{
            playersMental.put(player.getUniqueId(), points);
        }
    }

}
