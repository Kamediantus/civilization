package ge.rodichev.civilization.entity.building.housing;

import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
@Setter
public class SimpleHut extends Housing {
    private static final int CAPACITY = 10;

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public ResourcePack getRequiredResourcesForBuild() {
        return RequiredResources.RESOURCE_MAP.get(this.getClass());
    }
}
