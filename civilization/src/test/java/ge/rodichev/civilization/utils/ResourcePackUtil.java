package ge.rodichev.civilization.utils;

import ge.rodichev.civilization.resource.*;

public class ResourcePackUtil {
    public static ResourcePack createResourcePack(double wood, double stone) {
        ResourcePack resourcePack = ResourcePack.createEmptyResourcePack();
        resourcePack.put(Resource.WOOD, wood);
        resourcePack.put(Resource.STONE, stone);
        return resourcePack;
    }
}
