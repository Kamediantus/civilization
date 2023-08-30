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
        FactoryManager manager = spy(new FactoryManager());
        Factory stoneFactory = FactoryUtil.createBaseFactory(ResourcePackUtil.createResourcePack(0, 30), 10, 2);
        stoneFactory.setEmployee(CitizensUtils.createNormCitizens(stoneFactory.getMaxCitizenCount()));

        Factory woodFactory = FactoryUtil.createBaseFactory(ResourcePackUtil.createResourcePack(10, 0), 10, 2);
        woodFactory.setEmployee(CitizensUtils.createNormCitizens(woodFactory.getMaxCitizenCount()));

        manager.getFactories().add(stoneFactory);
        manager.getFactories().add(woodFactory);

        when(manager.getResourceManager()).thenReturn(new ResourceManager(ResourcePackUtil.createResourcePack(100, 25)));

        ResourcePack resourceValue = manager.getResourcesValue(manager.getProducedResourcesPerTick());
        assertEquals("Wood in high priority",500d, resourceValue.get(Resource.WOOD));
        assertEquals("Wood in high priority",375d, resourceValue.get(Resource.STONE));
    }
}
