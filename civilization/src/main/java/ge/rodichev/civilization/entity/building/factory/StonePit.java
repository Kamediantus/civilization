package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.resource.*;

public class StonePit extends Factory {
    @Override
    public ResourcePack getRequiredResourcesForBuild() {
        return RequiredResources.RESOURCE_MAP.get(StonePit.class);
    }

    @Override
    public ResourcePack getMaxGeneratedResourcesPerTick() {
        return GeneratedResources.RESOURCE_MAP.get(StonePit.class);
    }

    @Override
    public int getMaxCitizenCount() {
        return 15;
    }

    @Override
    public int getRequiredCitizenCount() {
        return RequiredResources.CITIZENS_MAP.get(StonePit.class);
    }
}
