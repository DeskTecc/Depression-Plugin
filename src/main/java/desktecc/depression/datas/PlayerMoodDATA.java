package desktecc.depression.datas;

public class PlayerMoodDATA {
    private boolean onDark;
    private PlayerSleepDATA checkSleep;
    private boolean nearEnderman;
    private Integer villagerClickCounter;
    private Long timeAlone = 0L;
    private Float mentalPoints;

    public PlayerMoodDATA(boolean onDark, PlayerSleepDATA checkSleep, boolean nearEnderman, Integer villagerClickCounter,Float mentalPoints){
        this.onDark = onDark;
        this.checkSleep = checkSleep;
        this.nearEnderman = nearEnderman;
        this.villagerClickCounter = villagerClickCounter;
        this.mentalPoints = mentalPoints;
    }

    public boolean getOnDark(){
        return this.onDark;
    }

    public void setOnDark(boolean onDark){
        this.onDark = onDark;
    }

    public Float getMentalPoints(){
        return this.mentalPoints;
    }

    public void setMentalPoints(Float points){
        this.mentalPoints = points;
        if(this.mentalPoints<0.0F){
            this.mentalPoints=0.0F;
        }
        if(this.mentalPoints>100.0F){
            this.mentalPoints=100.0F;
        }
    }

    public void addMentalPoints(Float points){
        this.mentalPoints = this.mentalPoints+points;
        if(this.mentalPoints>100.0F){
            this.mentalPoints=100.0F;
        }
    }

    public void subMentalPoints(Float points){
        this.mentalPoints = this.mentalPoints-points;
        if(this.mentalPoints<0.0F){
            this.mentalPoints=0.0F;
        }
    }

    public PlayerSleepDATA getCheckSleep(){
        return this.checkSleep;
    }

    public void setOnAsleep(PlayerSleepDATA checkSleep){
        this.checkSleep = checkSleep;
    }

    public boolean getNearEnderman(){
        return this.nearEnderman;
    }

    public void setNearEnderman(boolean nearEnderman){
        this.nearEnderman = nearEnderman;
    }

    public Integer getVillagerClickCounter(){
        return this.villagerClickCounter;
    }

    public void setVillagerClickCounter(Integer villagerClickCounter){
        this.villagerClickCounter = villagerClickCounter;
    }

    public long getTimeAlone(){
        return this.timeAlone;
    }

    public void setTimeAlone(long timeAlone){
        this.timeAlone = timeAlone;
    }

}
