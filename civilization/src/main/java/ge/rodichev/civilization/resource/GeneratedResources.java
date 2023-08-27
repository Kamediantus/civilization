package ge.rodichev.civilization.resource;

import java.util.*;

import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.entity.building.factory.*;

public class GeneratedResources {
    public static final Map<Class, ResourcePack> RESOURCE_MAP = getGeneratedResourcesMap();

    public static Map<Class, ResourcePack> getGeneratedResourcesMap() {
        Map<Class, ResourcePack> resourceMap = new HashMap<>();
        resourceMap.put(Sawmill.class, getSawmillGeneratedResources());
        resourceMap.put(StonePit.class, getStonePitGeneratedResources());

        return resourceMap;
    }

    private static ResourcePack getSawmillGeneratedResources() {
        ResourcePack generatedResources = new ResourcePack();
        generatedResources.put(Resource.WOOD, 5d);
        return generatedResources;
    }

    private static ResourcePack getStonePitGeneratedResources() {
        ResourcePack generatedResources = new ResourcePack();
        generatedResources.put(Resource.STONE, 5d);
        return generatedResources;
    }
}
