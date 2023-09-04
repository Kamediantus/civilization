package ge.rodichev.civilization.resource;

import lombok.*;

@Getter
public enum Resource {
    WOOD(false), STONE(false), WORKFORCE(true);

    private boolean burnOnEndOfTick;
    Resource(boolean burnOnEndOfTick) {
        this.burnOnEndOfTick = burnOnEndOfTick;
    }
}
