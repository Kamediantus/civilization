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

    public void concatResources(ResourcePack resourcePack) {
        resourcePack.keySet().forEach(k -> this.replace(k, this.get(k) + resourcePack.get(k)));
    }

    public void subtractResources(ResourcePack resourcePack) {
        resourcePack.keySet().forEach(k -> this.replace(k, this.get(k) - resourcePack.get(k)));
    }

    public boolean hasEnoughResources(ResourcePack resourcePack) {
        for (Map.Entry<Resource, Double> entry: resourcePack.entrySet()) {
            if (!hasEnoughResource(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean hasEnoughResource(Resource resource, Double necessaryCount) {
        return this.get(resource) >= necessaryCount;
    }

}
