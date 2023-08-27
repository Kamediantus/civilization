package ge.rodichev.civilization.resource;

import java.util.*;

public class ResourcePack extends HashMap<Resource, Double> {

    public static ResourcePack createEmptyResourcePack() {
        ResourcePack resourcePack = new ResourcePack();
        Arrays.stream(Resource.values()).forEach(resource -> resourcePack.put(resource, 0d));
        return resourcePack;
    }

    public static ResourcePack createFilledResourcePack(double initResourceCount) {
        ResourcePack resourcePack = new ResourcePack();
        Arrays.stream(Resource.values()).forEach(resource -> resourcePack.put(resource, initResourceCount));
        return resourcePack;
    }
}
