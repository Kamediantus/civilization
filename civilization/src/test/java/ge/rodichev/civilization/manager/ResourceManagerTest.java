package ge.rodichev.civilization.manager;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

import java.util.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

class ResourceManagerTest {
    @Test
    void testProduceCommonResources() {
        int resGen1 = 10;
        int resGen2 = 12;
        int resGen3 = 110;
        int burnResGen3 = 112;
        double resGenSum = Double.valueOf(resGen1 + resGen2 + resGen3);

        ResourceManager resourceManager = spy(new ResourceManager());
        assertTrue("Contain all resources", Arrays.asList(Resource.values()).containsAll(resourceManager.getResourcePack().keySet()));

        Factories factories = new Factories();
        factories.add(FactoryUtil.createBaseFactory(resGen1, 1, 1, false));
        factories.add(FactoryUtil.createBaseFactory(resGen2, 1, 1, false));
        factories.add(FactoryUtil.createBaseFactory(resGen3, 1, 1, false));
        factories.add(FactoryUtil.createBaseFactory(burnResGen3, 1, 1, true));
        factories.forEach(factory -> factory.getEmployee().add(new Citizen()));

        when(resourceManager.getFactories()).thenReturn(factories);

        resourceManager.tick(); // tick 1
        resourceManager.getResidueOfBurningResourcesFromPreviousTick().forEach((k, v) ->
                assertEquals("Residue should be zero after first tick",
                        0d, resourceManager.getResidueOfBurningResourcesFromPreviousTick().get(k)));

        Arrays.stream(Resource.values()).filter(resource -> !resource.isBurnOnEndOfTick())
                .forEach(resource -> assertEquals("Common resGen equal sum of factories gen", resGenSum, resourceManager.getResourcePack().get(resource)));

        Arrays.stream(Resource.values()).filter(Resource::isBurnOnEndOfTick)
                .forEach(resource -> assertEquals("Burning resGen equal single factory gen", Double.valueOf(burnResGen3), resourceManager.getResourcePack().get(resource)));

        resourceManager.tick(); // tick 2
        resourceManager.getResidueOfBurningResourcesFromPreviousTick().forEach((k, v) ->
                assertEquals("Residue should equal previous tick gen",
                        Double.valueOf(burnResGen3), resourceManager.getResidueOfBurningResourcesFromPreviousTick().get(k)));

        Arrays.stream(Resource.values()).filter(resource -> !resource.isBurnOnEndOfTick())
                .forEach(resource -> assertEquals("Common resGen equal sum of factories gen", resGenSum + resGenSum, resourceManager.getResourcePack().get(resource)));

        Arrays.stream(Resource.values()).filter(Resource::isBurnOnEndOfTick)
                .forEach(resource -> assertEquals("Burning resGen equal single factory gen", Double.valueOf(burnResGen3), resourceManager.getResourcePack().get(resource)));

    }
}
