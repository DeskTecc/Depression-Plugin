package desktecc.depression.datas;

public class PlayerMoodDATA {
    private boolean onDark;
    private boolean onAsleep;
    private boolean nearEnderman;
    private long timeAlone = 0L;
    private Float mentalPoints;


    public PlayerMoodDATA(boolean onDark, boolean onAsleep, boolean nearEnderman,Float mentalPoints){
        this.onDark = onDark;
        this.onAsleep = onAsleep;
        this.nearEnderman = nearEnderman;
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

    public boolean getOnAsleep(){
        return this.onAsleep;
    }

    public void setOnAsleep(boolean onAsleep){
        this.onAsleep = onAsleep;
    }

    public boolean getNearEnderman(){
        return this.nearEnderman;
    }

    public void setNearEnderman(boolean nearEnderman){
        this.nearEnderman = nearEnderman;
    }

    public long getTimeAlone(){
        return this.timeAlone;
    }

    public void setTimeAlone(long timeAlone){
        this.timeAlone = timeAlone;
    }

}
