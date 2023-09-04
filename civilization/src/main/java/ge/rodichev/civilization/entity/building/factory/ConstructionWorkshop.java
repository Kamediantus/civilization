package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.resource.*;


public class ConstructionWorkshop extends Factory {
    @Override
    public boolean isProduceBurnedResource() {
        return true;
    }

    @Override
    public ResourcePack getRequiredResourcesForBuild() {
        return RequiredResources.RESOURCE_MAP.get(ConstructionWorkshop.class);
    }

    @Override
    public int getMaxCitizenCount() {
        return 20;
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
