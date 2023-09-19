package ge.rodichev.eventgenerator.dto;

import lombok.*;

@Getter
public enum Resource {
    WOOD(false), STONE(false), WORKFORCE(true);

    private boolean burnOnEndOfTick;
    Resource(boolean burnOnEndOfTick) {
        this.burnOnEndOfTick = burnOnEndOfTick;
    }
}
