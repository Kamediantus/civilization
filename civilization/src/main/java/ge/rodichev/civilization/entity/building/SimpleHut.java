package ge.rodichev.civilization.entity.building;

import java.util.*;

import ge.rodichev.civilization.entity.building.factory.housing.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
@Setter
public class SimpleHut extends Housing {

    @Override
    public int getCapacity() {
        return 10;
    }

    @Override
    public ResourcePack getRequiredResourcesForBuild() {
        return RequiredResources.RESOURCE_MAP.get(this.getClass());
    }
}
