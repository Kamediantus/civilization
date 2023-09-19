package ge.rodichev.civilization.entity.building;

import com.fasterxml.jackson.annotation.*;
import ge.rodichev.civilization.resource.*;

public abstract class Building {
    @JsonIgnore
    public abstract ResourcePack getRequiredResourcesForBuild();
}
