package desktecc.depression.datas;

import org.bukkit.scheduler.BukkitTask;

public class PlayerSleepDATA {
    private BukkitTask task;
    private boolean asleep;

    public PlayerSleepDATA(BukkitTask task, boolean asleep){
        this.task = task;
        this.asleep = asleep;
    }

    public BukkitTask getTask(){
        return this.task;
    }

    public void setTask(BukkitTask task){
        this.task = task;
    }

    public boolean getAsleep(){
        return this.asleep;
    }

    public void setAsleep(boolean asleep){
        this.asleep = asleep;
    }
}
