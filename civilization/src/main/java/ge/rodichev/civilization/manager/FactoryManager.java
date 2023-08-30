package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

@Getter
@Setter
public class FactoryManager {
    private List<Factory> factories;

    @Autowired
    Citizens citizens;

    @Autowired
    ResourceManager resourceManager;

    public FactoryManager() {
        this.factories = new ArrayList<>();
    }

    public void tick() {
        ResourcePack producedResources = getProducedResourcesPerTick();
        getResourceManager().getResourcePack();
        // calculate less produced resource / existed resources
        // 100 WOOD / 10 wood/tick  1000/1/1  500/0.1/5
        // 25 STONE / 30 stone/tick 750/1/1   375/0.1/5
        //produce resources
        getResourceManager().getResourcePack().concatResources(producedResources);
    }


    protected ResourcePack getResourceValue(ResourcePack actualGenPerTick) {
        ResourcePack resourcePackWithRatio = ResourcePack.multiplyResources(getResourceManager().getResourcePack(), 0.1d);
        ResourcePack genResourcesWithRatio = ResourcePack.multiplyResources(actualGenPerTick, 5);

        return ResourcePack.multiplyResourcePacks(resourcePackWithRatio, genResourcesWithRatio);
    }

    protected ResourcePack getProducedResourcesPerTick() {
        ResourcePack producedResources = ResourcePack.createEmptyResourcePack();
        factories.stream().forEach(factory -> producedResources.concatResources(factory.getActualGeneratedResourcesPerTick()));
        return producedResources;
    }
}
