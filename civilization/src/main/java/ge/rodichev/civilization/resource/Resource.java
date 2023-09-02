package ge.rodichev.civilization.resource;

public enum Resource {
    WOOD(false), STONE(false), WORKFORCE(true);

    private boolean burnOnEndOfTick;
    Resource(boolean burnOnEndOfTick) {
        this.burnOnEndOfTick = burnOnEndOfTick;
    }
}
