package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.resource.*;

public class Sawmill extends Factory {
    @Override
    public int getRequiredCitizenCount() {
        return 2;
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
    public int getMaxCitizenCount() {
        return 10;
    }
}
