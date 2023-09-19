package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.resource.*;

public class Sawmill extends Factory {
    private static final int MAX_CITIZENS = 10;
    @Override
    public int getRequiredCitizenCount() {
        return RequiredResources.CITIZENS_MAP.get(Sawmill.class);
    }

    @Override
    public ResourcePack getRequiredResourcesForBuild() {
        return RequiredResources.RESOURCE_MAP.get(this.getClass());
    }

    @Override
    public ResourcePack getMaxGeneratedResourcesPerTick() {
        return GeneratedResources.RESOURCE_MAP.get(this.getClass());
    }

    @Override
    public Resource getProducedResourceType() {
        return Resource.WOOD;
    }

    @Override
    public int getMaxCitizenCount() {
        return MAX_CITIZENS;
    }
}
