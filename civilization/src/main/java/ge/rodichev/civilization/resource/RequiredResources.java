package ge.rodichev.civilization.resource;

import java.util.*;

import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.entity.building.factory.*;
import lombok.*;

@Getter
@Setter
public class RequiredResources {
    public static final Map<Class, ResourcePack> RESOURCE_MAP = getRequiredResourcesMap();

    public static Map<Class, ResourcePack> getRequiredResourcesMap() {
        Map<Class, ResourcePack> resourceMap = new HashMap<>();
        resourceMap.put(SimpleHut.class, getSimpleHutRequiredResources());
        resourceMap.put(Sawmill.class, getSawmillRequiredResources());

        return resourceMap;
    }

    public static ResourcePack getSawmillRequiredResources() {
        ResourcePack resourcePack = new ResourcePack();
        resourcePack.put(Resource.WOOD, 10);
        resourcePack.put(Resource.STONE, 5);
        return resourcePack;
    }

    public static ResourcePack getSimpleHutRequiredResources() {
        ResourcePack resourcePack = new ResourcePack();
        resourcePack.put(Resource.WOOD, 10);
        resourcePack.put(Resource.STONE, 5);
        return resourcePack;
    }
}
