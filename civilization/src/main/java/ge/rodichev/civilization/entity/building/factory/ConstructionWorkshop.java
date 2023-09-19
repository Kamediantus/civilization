package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.resource.*;


public class ConstructionWorkshop extends Factory {
    private static final int MAX_CITIZENS = 20;
    @Override
    public boolean isProduceBurnedResource() {
        return true;
    }

    @Override
    public ResourcePack getRequiredResourcesForBuild() {
        return RequiredResources.RESOURCE_MAP.get(ConstructionWorkshop.class);
    }

    @Override
    public Resource getProducedResourceType() {
        return Resource.WORKFORCE;
    }

    @Override
    public int getMaxCitizenCount() {
        return MAX_CITIZENS;
    }

    @Override
    public int getRequiredCitizenCount() {
        return RequiredResources.CITIZENS_MAP.get(ConstructionWorkshop.class);
    }

    @Override
    public ResourcePack getMaxGeneratedResourcesPerTick() {
        return GeneratedResources.RESOURCE_MAP.get(ConstructionWorkshop.class);
    }
}
