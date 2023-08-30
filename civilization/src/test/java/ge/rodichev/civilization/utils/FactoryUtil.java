package ge.rodichev.civilization.utils;

import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;

public class FactoryUtil {
    public static Factory createBaseFactory(int maxResGen, int maxCitCount, int reqCitCount) {
        Factory factory = new Factory() {
            @Override
            public int getMaxCitizenCount() {
                return maxCitCount;
            }

            @Override
            public int getRequiredCitizenCount() {
                return reqCitCount;
            }
            @Override
            public ResourcePack getMaxGeneratedResourcesPerTick() {
                return ResourcePack.createFilledResourcePack(maxResGen);
            }
            @Override
            public ResourcePack getRequiredResourcesForBuild() {
                return null;
            }
        };
        return factory;
    }

    public static Factory createBaseFactory(ResourcePack maxResGen, int maxCitCount, int reqCitCount) {
        Factory factory = new Factory() {
            @Override
            public int getMaxCitizenCount() {
                return maxCitCount;
            }

            @Override
            public int getRequiredCitizenCount() {
                return reqCitCount;
            }
            @Override
            public ResourcePack getMaxGeneratedResourcesPerTick() {
                return maxResGen;
            }
            @Override
            public ResourcePack getRequiredResourcesForBuild() {
                return null;
            }
        };
        return factory;
    }
}
