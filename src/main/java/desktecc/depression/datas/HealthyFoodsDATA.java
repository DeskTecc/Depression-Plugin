package desktecc.depression.datas;

public enum HealthyFoodsDATA {
    GOLDEN_APPLE(4.0F),
    GOLDEN_CARROT(4.0F),
    APPLE(2.5F),
    CARROT(2.5F),
    CAKE(2.5F),
    PUMPKIN_PIE(2.5F),
    BAKED_POTATO(2.5F),
    BEETROOT_SOUP(2.5F),
    MUSHROOM_STEW(2.5F),
    HONEY_BOTTLE(2.0F),
    MELON_SLICE(2.0F),
    COOKIE(2.0F),
    BREAD(1.5F),
    SWEET_BERRIES(1.5F),
    GLOW_BERRIES(1.0F),
    BEETROOT(1.0F),
    POTATO(1.0F),
    RABBIT_STEW(1.0F);

    private final Float points;

    HealthyFoodsDATA(Float points){
        this.points = points;
    }

    public Float getHealthyFoodPoints(){
        return this.points;
    }
}
