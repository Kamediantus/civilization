package ge.rodichev.civilization.entity.building.factory;

import static org.springframework.test.util.AssertionErrors.*;

import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

class FactoryTest {

    @Test
    void testGetActualGeneratedResourcesPerTick() {
        Factory factory = FactoryUtil.createBaseFactory(100, 10, 2);
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
}