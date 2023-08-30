package ge.rodichev.civilization.manager;

import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
public class ResourceManager extends Manager {
    private ResourcePack resourcePack;

    public ResourceManager() {
        resourcePack = ResourcePack.createEmptyResourcePack();
    }

    public ResourceManager(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
    }

    @Override
    public void tick() {

    }

    public void increaseResources(ResourcePack resourcePackToIncrease) {
        resourcePack.concatResources(resourcePackToIncrease);
    }

    public void increaseResource(Resource resource, double resourceCount) {
        resourcePack.replace(resource, resourcePack.get(resource) + resourceCount);
    }

    public void degreaseResources(ResourcePack resourcePackToSubtract) {
        if (resourcePack.hasEnoughResources(resourcePack)) {
            resourcePack.subtractResources(resourcePackToSubtract);
        }
    }

    public void degreaseResource(Resource resource, double resourceCount) {
        resourcePack.replace(resource, resourcePack.get(resource) - resourceCount);
    }
}
