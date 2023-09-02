package ge.rodichev.civilization.resource;

import static org.springframework.test.util.AssertionErrors.*;

import org.junit.jupiter.api.*;

class ResourcePackTest {

    @Test
    void testConcatResources() {
        ResourcePack resourcePack = ResourcePack.createEmptyResourcePack();

        resourcePack.concatResources(ResourcePack.createFilledResourcePack(10));
        resourcePack.keySet().forEach(k -> {
            assertEquals("0 + 10 = 10", 10d, resourcePack.get(k));
        });

        resourcePack.concatResources(ResourcePack.createFilledResourcePack(12.4d));
        resourcePack.keySet().forEach(k -> {
            assertEquals("10 + 12.4 = 22.4", 22.4d, resourcePack.get(k));
        });
    }

    @Test
    void testHasEnoughResources() {
        ResourcePack resourcePack = ResourcePack.createFilledResourcePack(10);
        assertTrue("Should be more then needed", resourcePack.hasEnoughResources(ResourcePack.createFilledResourcePack(5)));
        assertTrue("Should be equals", resourcePack.hasEnoughResources(ResourcePack.createFilledResourcePack(10)));
        assertFalse("Should be less them needed", resourcePack.hasEnoughResources(ResourcePack.createFilledResourcePack(11)));
    }

    @Test
    void testBuilder() {
        ResourcePack resourcePack = new ResourcePack().add(Resource.WOOD, 5d);
        assertEquals("Should contain only 5 wood.", 1, resourcePack.size());
        assertEquals("Should contain only 5 wood.", 5d, resourcePack.get(Resource.WOOD));

        resourcePack = resourcePack.add(Resource.STONE, 7d);
        assertEquals("Should contain 5 wood and 7 stones.", 2, resourcePack.size());
        assertEquals("Should contain 5 wood and 7 stones.", 5d, resourcePack.get(Resource.WOOD));
        assertEquals("Should contain 5 wood and 7 stones.", 7d, resourcePack.get(Resource.STONE));
    }
}
