package ge.rodichev.civilization.manager;

import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class ResourceManager extends Manager implements PostTicker {
    private ResourcePack resourcePack;
    private ResourcePack residueOfBurningResourcesFromPreviousTick;
    private ResourcePack commonResourcesCurrentTick;

    @Autowired
    Factories factories;

    public ResourceManager() {
        resourcePack = ResourcePack.createEmptyResourcePack();
        residueOfBurningResourcesFromPreviousTick = ResourcePack.createEmptyResourcePack();
    }

    public ResourceManager(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
    }

    @Override
    public void tick() { // TODO put all resource generation here instead of separate calling
        // clear currentResources
        produceCommonResources();
        produceBurningResources();
    }

    protected void produceCommonResources() {
        commonResourcesCurrentTick = ResourcePack.createEmptyResourcePack();
        factories.stream().filter(factory -> !factory.isProduceBurnedResource())
                .forEach(factory -> commonResourcesCurrentTick.concatResources(factory.getActualGeneratedResourcesPerTick()));

        resourcePack.concatResources(commonResourcesCurrentTick);
    }

    protected void produceBurningResources() {
        resourcePack.keySet().stream().filter(Resource::isBurnOnEndOfTick)
                .forEach(k -> {
                    residueOfBurningResourcesFromPreviousTick.replace(k, resourcePack.get(k));
                    resourcePack.replace(k, 0d);
                });
        factories.stream().filter(Factory::isProduceBurnedResource)
                .forEach(factory -> resourcePack.concatResources(factory.getActualGeneratedResourcesPerTick()));
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

    // burn out burning resources.
    @Override
    public void postTick() {
        resourcePack.keySet().stream().filter(Resource::isBurnOnEndOfTick).forEach(k -> {
            residueOfBurningResourcesFromPreviousTick.replace(k, resourcePack.get(k));
            resourcePack.replace(k, 0d);
        });
    }
}
