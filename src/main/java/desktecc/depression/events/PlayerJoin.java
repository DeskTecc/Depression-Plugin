package desktecc.depression.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static desktecc.depression.Depression.setPlayerMental;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        setPlayerMental(event.getPlayer(), 100F);
    }
}
