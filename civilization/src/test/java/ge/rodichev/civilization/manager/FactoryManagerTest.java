package ge.rodichev.civilization.manager;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

import ge.rodichev.civilization.entity.*;
import ge.rodichev.civilization.entity.building.factory.*;
import ge.rodichev.civilization.resource.*;
import ge.rodichev.civilization.utils.*;
import org.junit.jupiter.api.*;

class FactoryManagerTest {

    @Test
    void testRemoveElderlyCitizens() {
        FactoryManager factoryManager = spy(new FactoryManager());
        ResourceManager resourceManager = new ResourceManager(ResourcePack.createFilledResourcePack(1000));
        when(factoryManager.getResourceManager()).thenReturn(resourceManager);
        when(factoryManager.getFactories()).thenReturn(new Factories());
        Citizens citizens = new Citizens();
        when(factoryManager.getCitizens()).thenReturn(citizens);

        Sawmill sawmill = new Sawmill();
        int elderlyCitizens = sawmill.getMaxCitizenCount() - 3;
        Citizens employees = CitizensUtils.createCitizens(sawmill.getMaxCitizenCount() - elderlyCitizens, elderlyCitizens);
        citizens.addAll(employees);
        factoryManager.build(sawmill, employees);

        StonePit stonePit = new StonePit();
        elderlyCitizens = stonePit.getMaxCitizenCount() - 3;
        employees = CitizensUtils.createCitizens(stonePit.getMaxCitizenCount() - elderlyCitizens, elderlyCitizens);
        citizens.addAll(employees);
        factoryManager.build(stonePit, employees);

        factoryManager.removeDeadCitizens();
        factoryManager.getFactories().forEach(factory -> {
            assertEquals("Only 3 citizens should left", 3, factory.getEmployee().size());
        });
        assertEquals("Only 6 citizens should have job", 6, citizens.stream().filter(citizen -> citizen.getFactory() != null).toList().size());
    }

    @Test
    void testAddCitizensToFactories() {
        FactoryManager factoryManager = spy(new FactoryManager());
        ResourceManager resourceManager = spy(new ResourceManager(ResourcePackUtil.createResourcePack(100, 100, 100)));
        when(factoryManager.getResourceManager()).thenReturn(resourceManager);
        when(factoryManager.getFactories()).thenReturn(new Factories());

        Sawmill sawmill = new Sawmill();
        factoryManager.build(sawmill, CitizensUtils.createNormCitizens(sawmill.getMaxCitizenCount() - 3));
        StonePit stonePit = new StonePit();
        factoryManager.build(stonePit, CitizensUtils.createNormCitizens(stonePit.getMaxCitizenCount() - 3));
        ConstructionWorkshop constructionWorkshop = new ConstructionWorkshop();
        factoryManager.build(constructionWorkshop, CitizensUtils.createNormCitizens(constructionWorkshop.getMaxCitizenCount() - 3));

        factoryManager.setReadyToWorkCitizensIterator(CitizensUtils.createNormCitizens(5).iterator());
        boolean citizensAdded =  factoryManager.addCitizensToFactories(Resource.WOOD);
        assertTrue("Citizens was added", citizensAdded);
        assertEquals("Max employees count",  sawmill.getMaxCitizenCount(), sawmill.getEmployee().size());
        assertTrue("One citizen left", factoryManager.getReadyToWorkCitizensIterator().hasNext());

        citizensAdded =  factoryManager.addCitizensToFactories(Resource.STONE);
        assertTrue("Citizens was added", citizensAdded);
        assertEquals("Max employees count", stonePit.getMaxCitizenCount() - 1, stonePit.getEmployee().size());
        assertFalse("None citizen left", factoryManager.getReadyToWorkCitizensIterator().hasNext());

        citizensAdded =  factoryManager.addCitizensToFactories(Resource.WORKFORCE);
        assertFalse("Citizens was added", citizensAdded);
        assertEquals("Max employees count", constructionWorkshop.getMaxCitizenCount() - 3, constructionWorkshop.getEmployee().size());
    }

    @Test
    void testBuild() {
        FactoryManager factoryManager = spy(new FactoryManager());
        ResourceManager resourceManager = spy(new ResourceManager(ResourcePack.multiplyResources(RequiredResources.RESOURCE_MAP.get(Sawmill.class), 1.5d)));
        when(factoryManager.getResourceManager()).thenReturn(resourceManager);
        when(factoryManager.getFactories()).thenReturn(new Factories());
        when(factoryManager.getCitizens()).thenReturn(new Citizens());
        Sawmill sawmill = new Sawmill();
        factoryManager.getCitizens().addAll(CitizensUtils.createNormCitizens(sawmill.getMaxCitizenCount()));

        factoryManager.build(sawmill, factoryManager.getCitizens());
        assertEquals("Factory added", 1, factoryManager.getFactories().size());
        assertEquals("Max citizens linked to factory", sawmill.getMaxCitizenCount(), factoryManager.getCitizens().stream()
                .filter(citizen -> citizen.getFactory() != null).toList().size());
        assertEquals("Only 0,5 from required resources left, because init resources = 1.5",
                ResourcePack.multiplyResources(RequiredResources.RESOURCE_MAP.get(Sawmill.class), 0.5), factoryManager.getResourceManager().getResourcePack());
    }

    @Test
    void testAbleToBuild() {
        FactoryManager factoryManager = spy(new FactoryManager());
        ResourceManager resourceManager = spy(new ResourceManager());
        when(factoryManager.getResourceManager()).thenReturn(resourceManager);

        factoryManager.setReadyToWorkCitizensIterator(CitizensUtils.createNormCitizens(RequiredResources.CITIZENS_MAP.get(Sawmill.class)).iterator());
        when(resourceManager.getResourcePack()).thenReturn(ResourcePack.multiplyResources(RequiredResources.RESOURCE_MAP.get(Sawmill.class), 1.1));
        assertTrue("Enough citizens and resources", factoryManager.ableToBuild(Sawmill.class).size() == RequiredResources.CITIZENS_MAP.get(Sawmill.class));

        int requiredCitizens = new Sawmill().getRequiredCitizenCount();
        factoryManager.setReadyToWorkCitizensIterator(CitizensUtils.createNormCitizens(requiredCitizens).iterator());
        when(resourceManager.getResourcePack()).thenReturn(RequiredResources.RESOURCE_MAP.get(Sawmill.class));
        assertTrue("Enough citizens and resources", factoryManager.ableToBuild(Sawmill.class).size() == requiredCitizens);

        factoryManager.setReadyToWorkCitizensIterator(CitizensUtils.createNormCitizens(requiredCitizens - 1).iterator());
        when(resourceManager.getResourcePack()).thenReturn(RequiredResources.RESOURCE_MAP.get(Sawmill.class));
        assertTrue("Not Enough citizens", factoryManager.ableToBuild(Sawmill.class).isEmpty());

        factoryManager.setReadyToWorkCitizensIterator(CitizensUtils.createNormCitizens(requiredCitizens).iterator());
        when(resourceManager.getResourcePack()).thenReturn(ResourcePack.multiplyResources(RequiredResources.RESOURCE_MAP.get(Sawmill.class), 0.9));
        assertTrue("Not Enough resources", factoryManager.ableToBuild(Sawmill.class).isEmpty());
    }

    @Test
    void testGetMostValuableResourceToImprove() {
        FactoryManager factoryManager = new FactoryManager();
        assertEquals("Workforce lower then bottom line", Resource.WORKFORCE, factoryManager
                .getMostValuableResourceToImprove(ResourcePackUtil.createResourcePack(100d, 200d, FactoryManager.BOTTOM_LINE_OF_BURNING_RESOURCES - 1)));
        assertEquals("Wood has lowest value", Resource.WOOD, factoryManager
                .getMostValuableResourceToImprove(ResourcePackUtil.createResourcePack(100d, 200d, FactoryManager.BOTTOM_LINE_OF_BURNING_RESOURCES)));
        assertEquals("Stone has lowest value", Resource.STONE, factoryManager
                .getMostValuableResourceToImprove(ResourcePackUtil.createResourcePack(300d, 200d, FactoryManager.BOTTOM_LINE_OF_BURNING_RESOURCES)));
    }

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
