package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.resource.*;
import lombok.*;

@Getter
public class ResourceManager extends Manager {
    private ResourcePack resourcePack;

    public ResourceManager() {
        resourcePack = ResourcePack.createEmptyResourcePack();
    }

    @Override
    public void tick() {

    }

    public void increaseResource(Resource resource, int resourceCount) {
        resourcePack.replace(resource, resourcePack.get(resource) + resourceCount);
    }

    public void degreaseResources(ResourcePack resourcePack) { //TODO refactor, add check possibility to decrease
        for (Map.Entry<Resource, Double> entry: resourcePack.entrySet()) {
            degreaseResource(entry.getKey(), entry.getValue());
        }
    }

    public void degreaseResource(Resource resource, double resourceCount) {
        resourcePack.replace(resource, resourcePack.get(resource) - resourceCount);
    }

    public boolean hasEnoughResource(Resource resource, Double necessaryCount) {
        return resourcePack.get(resource) >= necessaryCount;
    }

    // TODO write test
    public boolean hasEnoughResources(ResourcePack resourcePack) {
        for (Map.Entry<Resource, Double> entry: resourcePack.entrySet()) {
            if (!hasEnoughResource(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
