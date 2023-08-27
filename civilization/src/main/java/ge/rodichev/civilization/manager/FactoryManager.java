package ge.rodichev.civilization.manager;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import org.springframework.beans.factory.annotation.*;

public class FactoryManager {
    public List<Factory> factories;

    @Autowired
    Citizens citizens;

    @Autowired
    ResourceManager resourceManager;

    public void tick() {

    }

//    private ResourcePack getProducedResourcesPerTick() {
//        ResourcePack producedResources = ResourcePack.createEmptyResourcePack();
//        factories.stream().map(Factory::getMaxGeneratedResourcesPerTick)
//                .forEach(k -> {
//                    int value = resourceManager.getResourcePack().get(k);
//                    resourceManager.getResourcePack().replace(k, value)
//                });
//    }
}
