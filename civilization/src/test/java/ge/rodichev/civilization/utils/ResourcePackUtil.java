package ge.rodichev.civilization.utils;

import java.util.*;

import ge.rodichev.civilization.resource.*;

public class ResourcePackUtil {
    public static ResourcePack createResourcePack(double wood, double stone) {
        ResourcePack resourcePack = ResourcePack.createEmptyResourcePack();
        resourcePack.put(Resource.WOOD, wood);
        resourcePack.put(Resource.STONE, stone);
        return resourcePack;
    }

    public static ResourcePack createEmptyBurningOrNotResourcePack(boolean burning, double maxGen) {
        ResourcePack resourcePack = new ResourcePack();
        Arrays.stream(Resource.values()).filter(resource -> resource.isBurnOnEndOfTick() == burning)
                .forEach(resource -> resourcePack.put(resource, maxGen));
        return resourcePack;
    }
}
