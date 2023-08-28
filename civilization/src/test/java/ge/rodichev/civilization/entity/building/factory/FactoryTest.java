package ge.rodichev.civilization.entity.building.factory;

import static org.springframework.test.util.AssertionErrors.*;

import ge.rodichev.civilization.resource.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

public class FactoryTest {

    @Test
    public void testGetActualGeneratedResourcesPerTick() {
        Factory factory = createBaseFactory(100, 10, 2);
        factory.setEmployee(CitizensUtils.createNormCitizens(2));
        factory.getActualGeneratedResourcesPerTick().forEach((resource, actualCount) -> {
            assertEquals("ResGen should be 20% of max", 20d, actualCount);
        });

        factory.setEmployee(CitizensUtils.createNormCitizens(factory.getRequiredCitizenCount() -1));
        factory.getActualGeneratedResourcesPerTick().forEach((resource, actualCount) -> {
            assertEquals("ResGen should be zero in case then citizens not enough", 0d, actualCount);
        });

        factory.setEmployee(CitizensUtils.createNormCitizens(factory.getMaxCitizenCount()));

        factory.getActualGeneratedResourcesPerTick().forEach((resource, actualCount) -> {
            assertEquals("ResGen should be max in case then citizens count is max", factory.getMaxGeneratedResourcesPerTick().get(resource), actualCount);
        });
    }

    private static Factory createBaseFactory(int maxResGen, int maxCitCount, int reqCitCount) {
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
}