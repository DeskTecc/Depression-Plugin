package desktecc.depression.events;

import desktecc.depression.datas.PlayerMoodDATA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static desktecc.depression.Depression.setPlayerMental;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        PlayerMoodDATA moodDATA = new PlayerMoodDATA(false, false,false,100.0F);
        setPlayerMental(event.getPlayer(), moodDATA);
    }
}
