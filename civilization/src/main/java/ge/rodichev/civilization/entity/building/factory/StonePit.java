package ge.rodichev.civilization.entity.building.factory;

import ge.rodichev.civilization.resource.*;

public class StonePit extends Factory {
    private static final int MAX_CITIZENS = 15;
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
        return MAX_CITIZENS;
    }

    @Override
    public int getRequiredCitizenCount() {
        return RequiredResources.CITIZENS_MAP.get(StonePit.class);
    }
}
