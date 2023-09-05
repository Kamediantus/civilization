package ge.rodichev.civilization.resource;

import java.util.*;

public class ResourcePack extends HashMap<Resource, Double> {

    public static ResourcePack createEmptyResourcePack() {
        ResourcePack resourcePack = new ResourcePack();
        Arrays.stream(Resource.values()).forEach(resource -> resourcePack.put(resource, 0d));
        return resourcePack;
    }

    public static ResourcePack createEmptyBurnedResourcePack() {
        ResourcePack resourcePack = new ResourcePack();
        Arrays.stream(Resource.values()).filter(Resource::isBurnOnEndOfTick).forEach(resource -> resourcePack.put(resource, 0d));
        return resourcePack;
    }

    public ResourcePack add(Resource resource, Double count) {
        this.put(resource, count);
        return this;
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

    public static ResourcePack multiplyResources(ResourcePack source, double ratio) {
        ResourcePack resourcePackWithRatio = ResourcePack.createEmptyResourcePack();
        resourcePackWithRatio.concatResources(source);
        resourcePackWithRatio.keySet().forEach(k -> resourcePackWithRatio.replace(k, resourcePackWithRatio.get(k) * ratio));
        return resourcePackWithRatio;
    }

    public static ResourcePack multiplyResourcePacks(ResourcePack sourceA, ResourcePack sourceB) {
        ResourcePack resourcePackWithRatio = ResourcePack.createEmptyResourcePack();
        resourcePackWithRatio.concatResources(sourceA);
        resourcePackWithRatio.keySet().forEach(k -> resourcePackWithRatio.replace(k, resourcePackWithRatio.get(k) * sourceB.get(k)));
        return resourcePackWithRatio;
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
