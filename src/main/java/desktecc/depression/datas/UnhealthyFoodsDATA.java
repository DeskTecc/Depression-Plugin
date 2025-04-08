package desktecc.depression.datas;

public enum UnhealthyFoodsDATA {
    POISONOUS_POTATO(2.5F),
    ROTTEN_FLESH(2.5F),
    SPIDER_EYE(2.5F),
    PUFFERFISH(2.5F),
    DRIED_KELP(1.5F),
    BEEF(1.0F),
    CHICKEN(1.0F),
    COD(1.0F),
    MUTTON(1.0F),
    PORKCHOP(1.0F),
    RABBIT(1.0F),
    SALMON(1.0F),
    TROPICAL_FISH(1.0F);

    private final Float points;

    UnhealthyFoodsDATA(Float points){
        this.points = points;
    }

    public Float getUnhealthyFoodPoints(){
        return this.points;
    }
}
