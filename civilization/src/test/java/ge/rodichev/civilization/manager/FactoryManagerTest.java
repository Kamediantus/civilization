package ge.rodichev.civilization.manager;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

class FactoryManagerTest {

    @Test
    void testGetValueOfExistingResources() {
        FactoryManager factoryManager = spy(new FactoryManager());
        ResourceManager resourceManager = spy(new ResourceManager(ResourcePackUtil.createResourcePack(100, 25)));
        Factories factories = new Factories();
        when(factoryManager.getFactories()).thenReturn(factories);
        when(resourceManager.getFactories()).thenReturn(factories);
        when(factoryManager.getResourceManager()).thenReturn(resourceManager);

        Factory stoneFactory = FactoryUtil.createBaseFactory(ResourcePackUtil.createResourcePack(0, 30), 10, 2);
        stoneFactory.setEmployee(CitizensUtils.createNormCitizens(stoneFactory.getMaxCitizenCount()));
        Factory woodFactory = FactoryUtil.createBaseFactory(ResourcePackUtil.createResourcePack(10, 0), 10, 2);
        woodFactory.setEmployee(CitizensUtils.createNormCitizens(woodFactory.getMaxCitizenCount()));

        factoryManager.getFactories().add(stoneFactory);
        factoryManager.getFactories().add(woodFactory);

        resourceManager.tick();
        ResourcePack resourceValue = factoryManager.getResourcesValue(factoryManager.getResourceManager().getCommonResourcesCurrentTick());
        assertEquals("Wood has high priority",550d, resourceValue.get(Resource.WOOD));
        assertEquals("Wood has high priority",825d, resourceValue.get(Resource.STONE));
    }
}
