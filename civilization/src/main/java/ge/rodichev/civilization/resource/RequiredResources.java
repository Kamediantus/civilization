package ge.rodichev.civilization.resource;

import java.util.*;

import ge.rodichev.civilization.entity.building.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.entity.building.housing.*;
import lombok.*;

@Getter
@Setter
public class RequiredResources {
    public static final Map<Class<? extends Building>, ResourcePack> RESOURCE_MAP = getRequiredResourcesMap();
    public static final Map<Class<? extends Factory>, Integer> CITIZENS_MAP = getRequiredCitizensMap();

    public static Map<Class<? extends Factory>, Integer> getRequiredCitizensMap() {
        Map<Class<? extends Factory>, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Sawmill.class, 2);
        resourceMap.put(StonePit.class, 4);

        return resourceMap;
    }

    public static Map<Class<? extends Building>, ResourcePack> getRequiredResourcesMap() {
        Map<Class<? extends Building>, ResourcePack> resourceMap = new HashMap<>();
        resourceMap.put(SimpleHut.class, getSimpleHutRequiredResources());
        resourceMap.put(Sawmill.class, getSawmillRequiredResources());
        resourceMap.put(StonePit.class, getStonePitRequiredResources());

        return resourceMap;
    }

    public static ResourcePack getSawmillRequiredResources() {
        ResourcePack resourcePack = new ResourcePack();
        resourcePack.put(Resource.WOOD, 10d);
        resourcePack.put(Resource.STONE, 5d);
        return resourcePack;
    }

    public static ResourcePack getSimpleHutRequiredResources() {
        ResourcePack resourcePack = new ResourcePack();
        resourcePack.put(Resource.WOOD, 8d);
        resourcePack.put(Resource.STONE, 5d);
        return resourcePack;
    }

    public static ResourcePack getStonePitRequiredResources() {
        ResourcePack resourcePack = new ResourcePack();
        resourcePack.put(Resource.WOOD, 10d);
        resourcePack.put(Resource.STONE, 15d);
        return resourcePack;
    }
}
