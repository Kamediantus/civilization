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

    private RequiredResources(){}

    public static Map<Class<? extends Factory>, Integer> getRequiredCitizensMap() {
        Map<Class<? extends Factory>, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Sawmill.class, 2);
        resourceMap.put(StonePit.class, 4);
        resourceMap.put(ConstructionWorkshop.class, 4);

        return resourceMap;
    }

    public static Map<Class<? extends Building>, ResourcePack> getRequiredResourcesMap() {
        Map<Class<? extends Building>, ResourcePack> resourceMap = new HashMap<>();
        resourceMap.put(SimpleHut.class, getSimpleHutRequiredResources());
        resourceMap.put(Sawmill.class, getSawmillRequiredResources());
        resourceMap.put(StonePit.class, getStonePitRequiredResources());
        resourceMap.put(ConstructionWorkshop.class, getConstructionWorkshopRequiredResources());

        return resourceMap;
    }

    public static ResourcePack getSawmillRequiredResources() {
        return new ResourcePack()
                .add(Resource.WOOD, 10d)
                .add(Resource.STONE, 5d)
                .add(Resource.WORKFORCE, 2d);
    }

    public static ResourcePack getSimpleHutRequiredResources() {
        return new ResourcePack()
                .add(Resource.WOOD, 8d)
                .add(Resource.STONE, 5d)
                .add(Resource.WORKFORCE, 2d);
    }

    public static ResourcePack getStonePitRequiredResources() {
        return new ResourcePack()
                .add(Resource.WOOD, 10d)
                .add(Resource.STONE, 15d)
                .add(Resource.WORKFORCE, 4d);
    }

    public static ResourcePack getConstructionWorkshopRequiredResources() {
        return new ResourcePack()
                .add(Resource.WOOD, 25d)
                .add(Resource.STONE, 20d)
                .add(Resource.WORKFORCE, 1d);
    }
}
