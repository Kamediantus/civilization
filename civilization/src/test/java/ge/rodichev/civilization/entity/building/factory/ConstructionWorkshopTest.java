package ge.rodichev.civilization.entity.building.factory;

import static org.springframework.test.util.AssertionErrors.*;

import ge.rodichev.civilization.resource.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

class ConstructionWorkshopTest {

    @Test
    void testActualResourceGenerationGen() {
        ConstructionWorkshop workshop = new ConstructionWorkshop();
        workshop.getEmployee().addAll(CitizensUtils.createNormCitizens(RequiredResources.CITIZENS_MAP.get(ConstructionWorkshop.class) - 1));
        assertEquals("Gen should be zero. Not enough citizens", 0d, workshop.getActualGeneratedResourcesPerTick().get(Resource.WORKFORCE));

        workshop.getEmployee().addAll(CitizensUtils.createNormCitizens(1));
        assertEquals("Gen should be 2. Minimal generation", 2d, workshop.getActualGeneratedResourcesPerTick().get(Resource.WORKFORCE));

        workshop.getEmployee().addAll(CitizensUtils.createNormCitizens(workshop.getMaxCitizenCount() - workshop.getEmployee().size()));
        assertEquals("Gen should be 10. Max generation", workshop.getMaxGeneratedResourcesPerTick().get(Resource.WORKFORCE), workshop.getActualGeneratedResourcesPerTick().get(Resource.WORKFORCE));
    }
}
