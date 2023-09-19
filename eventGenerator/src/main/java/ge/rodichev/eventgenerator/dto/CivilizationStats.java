package ge.rodichev.eventgenerator.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
@Setter
public class CivilizationStats {
    private Factories factories;
    private int simpleHutCount;
    private ResourcePack resourcePack;
    private long citizensCount;
    private long tickCount;

    public CivilizationStats(@JsonProperty("factories") Factories factories,
                             @JsonProperty("simpleHutCount") int simpleHutCount,
                             @JsonProperty("resourcePack") ResourcePack resourcePack,
                             @JsonProperty("citizensCount") long citizensCount,
                             @JsonProperty("tickCount") long tickCount) {
        this.factories = factories;
        this.simpleHutCount = simpleHutCount;
        this.resourcePack = resourcePack;
        this.citizensCount = citizensCount;
        this.tickCount = tickCount;
    }

    public CivilizationStats() {}
}