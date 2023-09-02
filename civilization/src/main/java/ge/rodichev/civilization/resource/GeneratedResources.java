package ge.rodichev.civilization.resource;

import java.util.*;

import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.entity.building.factory.*;

public class GeneratedResources {
    public static final Map<Class<? extends Building>, ResourcePack> RESOURCE_MAP = getGeneratedResourcesMap();

    private GeneratedResources(){}

    public static Map<Class<? extends Building>, ResourcePack> getGeneratedResourcesMap() {
        Map<Class<? extends Building>, ResourcePack> resourceMap = new HashMap<>();
        resourceMap.put(Sawmill.class, getSawmillGeneratedResources());
        resourceMap.put(StonePit.class, getStonePitGeneratedResources());
        resourceMap.put(ConstructionWorkshop.class, getConstructionWorkshopGeneratedResources());

        return resourceMap;
    }

    private static ResourcePack getSawmillGeneratedResources() {
        return new ResourcePack().add(Resource.WOOD, 5d);
    }

    private static ResourcePack getStonePitGeneratedResources() {
        return new ResourcePack().add(Resource.STONE, 5d);
    }

    private static ResourcePack getConstructionWorkshopGeneratedResources() {
        return new ResourcePack().add(Resource.WORKFORCE, 10d);
    }
}
